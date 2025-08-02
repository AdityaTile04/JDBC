import java.util.Scanner;

public class Exceptionhandling {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner( System.in );

        int[] arr = new int[5];
        try {
            System.out.println("Hello from first try block");
            try {
                arr[6] = 10;
            } catch (ArrayIndexOutOfBoundsException  e) {
                System.out.println(e.getMessage());
            }
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("I always work");
        }
        System.out.println("Enter age:");
        int age = sc.nextInt();

        if(age < 18) {
            throw new RuntimeException("Your are not eligible to vote");
        } else {
            System.out.println("Eligible to vote");
        }

        ageChecker( 19 , 0);
    }
    public static void ageChecker(int a, int b) throws ArithmeticException {
        System.out.println(a/b);
    }
}
