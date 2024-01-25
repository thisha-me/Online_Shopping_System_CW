import main.WestminsterShoppingManager;
import gui.LoginGUI;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String choice;
        do{
            System.out.println("-".repeat(40));
            System.out.printf("%32s%n", "Online Shopping Center");
            System.out.println("-".repeat(40));
            System.out.println("1. Login as Shopping Manager");
            System.out.println("2. Login as Customer");
            System.out.println("3. Exit");

            System.out.print("Enter your choice : ");
            choice=scanner.next();

            switch (choice) {
                case "1" -> new WestminsterShoppingManager().displayMenu();
                case "2" -> SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
                case "3" -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }while (!choice.equals("3"));
    }

}
