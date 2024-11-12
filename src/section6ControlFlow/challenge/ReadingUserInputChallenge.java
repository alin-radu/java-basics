package challenge;

import java.util.Scanner;

public class ReadingUserInputChallenge {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int count = 1;
        int sum = 0;

        while (count < 11) {
            System.out.println("Enter Number #" + count);
            boolean hasNextInt = scanner.hasNextInt();
            if (hasNextInt) {
                sum = sum + scanner.nextInt();
                count += 1;
            } else {
                System.out.println("Invalid Number");
            }
            scanner.nextLine();// handle next line character(enter key)
        }
        System.out.println("Sum = " + sum);

        scanner.close();
    }
}