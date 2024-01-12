package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {
    private static final Scanner scanner=new Scanner(System.in);

    public static double getPositiveDouble(String input_txt){
        while (true){
            try {
                System.out.print(input_txt);
                double val=scanner.nextDouble();
                if(val>0){
                    return val;
                }
                System.out.println("Input must be positive");
            }catch (InputMismatchException e){
                System.out.println("Input must be double value!");
                scanner.next();
            }
        }
    }

    public static int getPositiveInt(String input_txt){
        while (true){
            try {
                System.out.print(input_txt);
                int val=scanner.nextInt();
                if(val>0){
                    return val;
                }
                System.out.println("Input must be positive");
            }catch (InputMismatchException e){
                System.out.println("Input must be integer value!");
                scanner.next();
            }
        }
    }
}
