import utils.InputValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager{

    private ArrayList<Product> products;
    private final Scanner scanner=new Scanner(System.in);
    private final InputValidator inputValidator=new InputValidator();
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
        do{
            System.out.println("\nMenu:");
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print list of products");
            System.out.println("4. Save products to file");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            choice= scanner.next();

            switch (choice) {
                case "1" -> addProduct();
                case "2" -> deleteProduct();
                case "3" -> printProducts();
                case "4" -> saveProductsToFile();
                case "5" -> System.out.println("Exit the program...");
                default -> System.out.println("Invalid choice.");
            }
        }while (!choice.equals("5"));

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

        System.out.println("Are you sure to delete this product(Y/n)?");
        String yN=scanner.next().toUpperCase();
        if(yN.equals("Y")){
            products.remove(deletedProduct);
            System.out.println("Product delete successfully");
        }
    }

    @Override
    public void printProducts() {
        Collections.sort(products);
        for (Product product : products) {
            System.out.println(product+"\n");
        }
    }

    @Override
    public void saveProductsToFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("products.txt"));
            outputStream.writeObject(products);
            System.out.println("Products saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving products to file.");
            System.out.println(e);
        }
    }

    @Override
    public void loadProductsFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("products.txt"))) {
            products = (ArrayList<Product>) inputStream.readObject();
            System.out.println("Products loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved products found.");
            System.out.println(e);
        }
    }

}
