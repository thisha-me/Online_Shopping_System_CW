package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {
    private final Scanner scanner=new Scanner(System.in);

    public double get_double(String input_txt){
        while (true){
            try {
                System.out.print(input_txt);
                return scanner.nextDouble();
            }catch (InputMismatchException e){
                System.out.println("Invalid input \n Input must be double value");
                scanner.next();
            }
        }
    }

    public int get_int(String input_txt){
        while (true){
            try {
                System.out.print(input_txt);
                return scanner.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Invalid input \n Input must be integer value");
                scanner.next();
            }
        }
    }
}
