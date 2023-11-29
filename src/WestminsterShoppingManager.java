import java.util.ArrayList;
import java.util.Scanner;

public class WestminsterShoppingManager {
    private static ArrayList<Product> products=new ArrayList<>();
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String choice;

        do {
            displayMenu();
            System.out.print("Enter choice: ");
            choice= scanner.next();
        }while (!choice.equals("5"));
    }

    public static void displayMenu(){
        System.out.println("\nMenu:");
        System.out.println("1. Add a new product");
        System.out.println("2. Delete a product");
        System.out.println("3. Print list of products");
        System.out.println("4. Save products to file");
        System.out.println("5. Exit");
    }

    public static void addProduct(){
        Scanner scanner=new Scanner(System.in);

        if(products.size()>50){
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

        switch (choice){
            case "1":
                System.out.print("Brand: ");
                String brand= scanner.next();

                System.out.print("Warranty Period: ");
                int warrantyPeriod=scanner.nextInt();

                Electronics newElectronics=new Electronics(productId,productName,availableItems,price,brand,warrantyPeriod);
                products.add(newElectronics);
                System.out.println("Electronics added successfully");
                break;
            case "2":
                System.out.print("Size: ");
                String size= scanner.next();

                System.out.print("Color: ");
                String color=scanner.next();

                Clothing newClothing=new Clothing(productId,productName,availableItems,price,size,color);
                products.add(newClothing);
                System.out.println("Clothing added successfully");
                break;
            default:
                System.out.println("invalid Choice");

        }


    }
}
