package section9Array.challenge;

import java.util.Scanner;

public class MinimumElementArray {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int[] newArray = getIntegers(4);
        System.out.println("Minimum number is " + findMin(newArray));

    }

    private static int[] getIntegers(int count) {
        System.out.println("Enter " + count + " of integers:\r");
        int[] values = new int[count];
        for (int i = 0; i < count; i++) {
            if (i > 0) {
                System.out.println("enter number");
            }
            ;
            values[i] = scanner.nextInt();
        }
        return values;
    }

    private static int findMin(int[] array) {
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            int value = array[i];

            if (min > value) {
                min = value;
            }
        }
        return min;
    }
}
