package cli;

import gui.LoginGUI;
import main.Clothing;
import main.Electronics;
import main.Product;
import main.ShoppingManager;
import utils.DBConnection;
import utils.InputValidator; //custom defined input validators

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {

    private final ArrayList<Product> products;
    private ArrayList<Product> deletedProducts;
    private final Scanner scanner = new Scanner(System.in);
    private final InputValidator inputValidator = new InputValidator();

    public WestminsterShoppingManager() {
        products = new ArrayList<>();
        deletedProducts = new ArrayList<>();
        loadProducts();
    }

    @Override
    public ArrayList<Product> getProducts() {
        return products;
    }

    public synchronized void displayMenu() {
        String choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print list of products");
            System.out.println("4. Save products to file");
            System.out.println("5. Open customer login(GUI)");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            choice = scanner.next();

            switch (choice) {
                case "1" -> addProduct();
                case "2" -> deleteProduct();
                case "3" -> printProducts();
                case "4" -> saveProducts();
                case "5" ->startGUI();
                case "6" -> System.out.println("Exit the program...");
                default -> System.out.println("Invalid choice.");
            }
        } while (!choice.equals("6"));

    }

    @Override
    public void addProduct() {

        System.out.println("Add Product");

        if (products.size() > 50) {
            System.out.println("Maximum product limit reached (50 products). Cannot add more.");
            return;  // for exit method
        }

        System.out.print("Product ID: ");
        String productId = scanner.next();
        if (isExistProductID(productId)) {
            System.out.println("Already Existing product id");
            return;
        }

        System.out.print("Product Name: ");
        String productName = scanner.next();

        int availableItems = inputValidator.get_int("Available Items: ");

        double price = inputValidator.get_double("Price: ");

        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.print("Select product type to add:");
        String choice = scanner.next();

        switch (choice) {
            case "1":
                System.out.print("Brand: ");
                String brand = scanner.next();

                int warrantyPeriod = inputValidator.get_int("Warranty Period (Months): ");

                Electronics newElectronics = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
                products.add(newElectronics);
                System.out.println("Electronics added successfully");
                break;
            case "2":
                System.out.print("Size: ");
                String size = scanner.next();

                System.out.print("Color: ");
                String color = scanner.next();

                Clothing newClothing = new Clothing(productId, productName, availableItems, price, size, color);
                products.add(newClothing);
                System.out.println("Clothing added successfully");
                break;
            default:
                System.out.println("invalid Choice");
        }
    }

    public boolean isExistProductID(String productID) {
        for (Product product : products) {
            return product.getProductID().equals(productID);
        }
        return false;
    }

    @Override
    public void deleteProduct() {
        if (products.isEmpty()) {
            System.out.println("No products available for delete.");
            return;
        }

        System.out.println("Delete Product");

        System.out.print("Product Id:");
        String deleteProductId = scanner.next().strip();

        Product deletedProduct = null;
        for (Product product : products) {
            if (product.getProductID().equals(deleteProductId)) {
                deletedProduct = product;
                System.out.println("Product Delete ");
                break;
            }
        }

        if (deletedProduct == null) {
            System.out.println("Product with ID " + deleteProductId + " not found.");
            return;
        }

        System.out.println("Deleted Product");
        System.out.println(deletedProduct);

        System.out.print("Are you sure to delete this product(Y/n)?");
        String yN = scanner.next().toLowerCase();
        if (yN.equals("y")) {
            products.remove(deletedProduct);
            deletedProducts.add(deletedProduct);
            System.out.println("Product delete successfully");
            System.out.println(deletedProduct.getAvailableItems() + " left the system");
        }
    }

    @Override
    public void printProducts() {
        Collections.sort(products);
        for (Product product : products) {
            System.out.println(product + "\n");
        }
    }

    public void saveProducts() {
        try (Connection connection = DBConnection.getConnection()) {
            String sql;
            PreparedStatement statement = null;
            for (Product product : products) {

                if (product instanceof Electronics) {
                    sql = "INSERT IGNORE INTO Electronics (productID, productName, availableItems, price, brand, warrantyPeriod) VALUES (?, ?, ?, ?,?, ?)";
                    statement = connection.prepareStatement(sql);
                    statement.setString(5, ((Electronics) product).getBrand());
                    statement.setInt(6, ((Electronics) product).getWarrantyPeriod());
                } else if (product instanceof Clothing) {
                    sql = "INSERT IGNORE INTO Clothing (productID, productName, availableItems, price, size, color) VALUES (?, ?, ?, ?, ?, ?)";
                    statement = connection.prepareStatement(sql);
                    statement.setString(5, ((Clothing) product).getSize());
                    statement.setString(6, ((Clothing) product).getColor());
                }
                assert statement != null;
                statement.setString(1, product.getProductID());
                statement.setString(2, product.getProductName());
                statement.setInt(3, product.getAvailableItems());
                statement.setDouble(4, product.getPrice());
                statement.executeUpdate();
            }

            // if delete products delete form the db
            deleteProductsDB(connection);

            System.out.println("Data save to database");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadProducts() {
        try (Connection connection = DBConnection.getConnection()) {
            loadElectronics(connection);
            loadClothing(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadElectronics(Connection connection) throws SQLException {
        String electronicSQL = "SELECT productID, productName, availableItems, price, brand, warrantyPeriod FROM Electronics";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(electronicSQL);

        while (resultSet.next()) {
            String productId = resultSet.getString("productID");
            String productName = resultSet.getString("productName");
            int availableItems = resultSet.getInt("availableItems");
            double price = resultSet.getDouble("price");
            String brand = resultSet.getString("brand");
            int warrantyPeriod = resultSet.getInt("warrantyPeriod");

            products.add(new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod));
        }
    }

    private void loadClothing(Connection connection) throws SQLException {
        String clothingSQL = "SELECT productID, productName, availableItems, price, size, color FROM Clothing";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(clothingSQL);

        while (resultSet.next()) {
            String productId = resultSet.getString("productID");
            String productName = resultSet.getString("productName");
            int availableItems = resultSet.getInt("availableItems");
            double price = resultSet.getDouble("price");
            String size = resultSet.getString("size");
            String color = resultSet.getString("color");
            products.add(new Clothing(productId, productName, availableItems, price, size, color));
        }
    }

    private void deleteProductsDB(Connection connection) throws SQLException {
        String sql;
        for (Product product : deletedProducts) {
            sql = "DELETE FROM ";
            sql += product instanceof Electronics ? "Electronics WHERE productId=?" : "Clothing WHERE productId=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getProductID());
            statement.executeUpdate();
        }
        deletedProducts.clear();
    }

    public void startGUI(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI().setVisible(true);
            }
        });
    }


    public static void main(String[] args) {
        new WestminsterShoppingManager().displayMenu();
    }
}
