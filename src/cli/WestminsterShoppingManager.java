package cli;

import utils.DBConnection;
import utils.InputValidator; //custom defined input validators

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {

    private final ArrayList<Product> products;
    private final Scanner scanner = new Scanner(System.in);
    private final InputValidator inputValidator = new InputValidator();

    public WestminsterShoppingManager() {
        products = new ArrayList<>();
        loadProductsFromFile();
    }

    @Override
    public ArrayList<Product> getProducts() {
        return products;
    }

    public void displayMenu() {
        String choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print list of products");
            System.out.println("4. Save products to file");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            choice = scanner.next();

            switch (choice) {
                case "1" -> addProduct();
                case "2" -> deleteProduct();
                case "3" -> printProducts();
                case "4" -> saveProductsToFile();
                case "5" -> System.out.println("Exit the program...");
                default -> System.out.println("Invalid choice.");
            }
        } while (!choice.equals("5"));

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
        if(isExistProductID(productId)){
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

    private boolean isExistProductID(String productID){
        for(Product product:products){
            return product.equals(productID);
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
            if (product.equals(deleteProductId)) {
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

    @Override
    public void saveProductsToFile() {
        try (FileWriter writer=new FileWriter("products.csv")){
            for (Product product:products){
                writer.write(product.toCSV());
            }
            System.out.println("Product Save to File Successfully");
        } catch (IOException e) {
            System.out.println("Error saving products to file.");
        }
    }

    @Override
    public void loadProductsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("products.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0) {
                    String productType = data[0]; // get product type
                    if (productType.equals("Electronics")) {
                        products.add(Electronics.fromCSV(line));
                    } else if (productType.equals("Clothing")) {
                        products.add(Clothing.fromCSV(line));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No saved products found.");
        }
    }

//    public void saveProducts() {
//        try (Connection connection = DBConnection.getConnection()) {
//            for (Product product : products) {
//                String sql = "INSERT IGNORE INTO Product (productID, productName, availableItems, price) VALUES (?, ?, ?, ?)";
//                PreparedStatement statement = connection.prepareStatement(sql);
//                statement.setString(1, product.getProductID());
//                statement.setString(2, product.getProductName());
//                statement.setInt(3, product.getAvailableItems());
//                statement.setDouble(4, product.getPrice());
//                statement.executeUpdate();
//
//                if (product instanceof Clothing) {
//                    saveClothing((Clothing) product, connection);
//                } else if (product instanceof Electronics) {
//                    saveElectronics((Electronics) product, connection);
//                }
//            }
//            System.out.println("Data save to database");
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void saveClothing(Clothing clothing, Connection connection) throws SQLException {
//        String sql = "INSERT IGNORE INTO Clothing (productID, size, color) VALUES (?, ?, ?)";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setString(1, clothing.getProductID());
//        statement.setString(2, clothing.getSize());
//        statement.setString(3, clothing.getColor());
//        statement.executeUpdate();
//    }
//
//    private void saveElectronics(Electronics electronics, Connection connection) throws SQLException {
//        String sql = "INSERT IGNORE INTO Electronics (productID, brand, warrantyPeriod) VALUES (?, ?, ?)";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setString(1, electronics.getProductID());
//        statement.setString(2, electronics.getBrand());
//        statement.setInt(3, electronics.getWarrantyPeriod());
//        statement.executeUpdate();
//    }
//
//    public void loadProducts() {
//        try {
//            Connection connection = DBConnection.getConnection();
//            loadElectronics(connection);
//            loadClothing(connection);
//            System.out.println("Data loading successfully");
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void loadElectronics(Connection connection) throws SQLException {
//        String electronicSQL = "SELECT p.productID, p.productName, p.availableItems, p.price, e.brand, e.warrantyPeriod " +
//                "FROM Product p INNER JOIN Electronics e " +
//                "ON p.productID = e.productID;";
//
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(electronicSQL);
//
//        while (resultSet.next()) {
//            String productId = resultSet.getString("productID");
//            String productName = resultSet.getString("productName");
//            int availableItems = resultSet.getInt("availableItems");
//            double price = resultSet.getDouble("price");
//            String brand = resultSet.getString("brand");
//            int warrantyPeriod = resultSet.getInt("warrantyPeriod");
//
//            products.add(new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod));
//        }
//    }
//
//    private void loadClothing(Connection connection) throws SQLException {
//        String clothingSQL = "SELECT p.productID, p.productName, p.availableItems, p.price, c.size, c.color " +
//                "FROM Product p INNER JOIN Clothing c ON p.productID = c.productID";
//
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(clothingSQL);
//
//        while (resultSet.next()) {
//            String productId = resultSet.getString("productID");
//            String productName = resultSet.getString("productName");
//            int availableItems = resultSet.getInt("availableItems");
//            double price = resultSet.getDouble("price");
//            String size = resultSet.getString("size");
//            String color = resultSet.getString("color");
//            products.add(new Clothing(productId, productName, availableItems, price, size, color));
//        }
//    }
}
