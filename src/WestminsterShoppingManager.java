import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager{

    private ArrayList<Product> products;
    public WestminsterShoppingManager() {
        products = new ArrayList<>();
    }

    @Override
    public ArrayList<Product> getProducts() {
        return products;
    }
    private final Scanner scanner=new Scanner(System.in);
    public void displayMenu() {
        String choice;
        do{
            System.out.println("\nMenu:");
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print list of products");
            System.out.println("4. Save products to file");
            System.out.println("5. Exit");
            System.out.print("Enter choice");

            choice= scanner.next();

            switch (choice) {
                case "1" -> addProduct();
                case "2" -> deleteProduct();
                case "3" -> printProducts();
                case "4" -> saveProductsToFile();
                case "5" -> System.out.println("Exit the program...");
            }
        }while (!choice.equals("5"));

    }

    @Override
    public void addProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Add Product");

        if (products.size() > 50) {
            System.out.println("Maximum product limit reached (50 products). Cannot add more.");
            return;  // for exit method
        }

        System.out.print("Product ID: ");
        String productId = scanner.next();

        System.out.print("Product Name: ");
        String productName = scanner.next();

        System.out.print("Available Items: ");
        int availableItems = scanner.nextInt();

        System.out.print("Price: ");
        double price = scanner.nextDouble();

        System.out.println("Select product type to add:");
        System.out.println("1. Electronics");
        System.out.print("2. Clothing");
        String choice = scanner.next();

        switch (choice) {
            case "1":
                System.out.print("Brand: ");
                String brand = scanner.next();

                System.out.print("Warranty Period (Months): ");
                int warrantyPeriod = scanner.nextInt();

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
        Scanner scanner = new Scanner(System.in);

        if (products.isEmpty()) {
            System.out.println("No products available for delete.");
            return;
        }

        System.out.println("Delete Product");

        System.out.print("Product Id:");
        String deleteProductId = scanner.next();

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
        if (deletedProduct instanceof Electronics) {
            System.out.println(
                    "Type: Electronics" +
                            "\nID: " + deletedProduct.getProductID() +
                            "\nName: " + deletedProduct.getProductName() +
                            "\nBrand: " + ((Electronics) deletedProduct).getBrand() +
                            "\nWarranty: " + ((Electronics) deletedProduct).getWarrantyPeriod() + " months" +
                            "\nPrice: " + deletedProduct.getPrice() +
                            "\nProduct Left: " + deletedProduct.getAvailableItems()
            );
        } else if (deletedProduct instanceof Clothing) {
            System.out.println(
                    "Type: Clothing" +
                            "\nID: " + deletedProduct.getProductID() +
                            "\nName: " + deletedProduct.getProductName() +
                            "\nSize: " + ((Clothing) deletedProduct).getSize() +
                            "\nColor: " + ((Clothing) deletedProduct).getColor() +
                            "\nPrice: " + deletedProduct.getPrice() +
                            "\nProduct Left: " + deletedProduct.getAvailableItems()
            );
        }
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
            if (product instanceof Electronics)
                System.out.println((Electronics) product);
            else if (product instanceof Clothing)
                System.out.println((Clothing) product);
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
        }
    }

    @Override
    public void loadProductsFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("products.txt"))) {
            products = (ArrayList<Product>) inputStream.readObject();
            System.out.println("Products loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved products found.");
        }
    }
}
