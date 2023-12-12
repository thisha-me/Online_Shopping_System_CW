package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {
    private final Scanner scanner=new Scanner(System.in);

    public double get_double(){
        while (true){
            try {
                return scanner.nextDouble();
            }catch (InputMismatchException e){
                System.out.println("Invalid input \n Input must be double value");
                scanner.next();
            }
        }
    }

    public int get_int(){
        while (true){
            try {
                return scanner.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Invalid input \n Input must be integer value");
                scanner.next();
            }
        }
    }
}
