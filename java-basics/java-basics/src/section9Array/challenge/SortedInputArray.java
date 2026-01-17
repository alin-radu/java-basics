package section9Array.challenge;

import java.util.Arrays;
import java.util.Scanner;

public class SortedInputArray {
    private static Scanner scanner = new Scanner(System.in);

    private static int[] getIntegers() {
        int[] values = new int[5];

        System.out.println("Enter " + 5 + " integers: \r");
        for (int i = 0; i < 5; i++) {
            values[i] = scanner.nextInt();
        }
        scanner.close();

        return values;
    }

    private static int[] sortIntegers(int[] array) {
        int[] sortedArray = Arrays.copyOf(array, array.length);

        boolean flag = true;
        int temp;
        while (flag) {
            flag = false;
            for (int i = 0; i < array.length - 1; i++) {
                if (sortedArray[i] < sortedArray[i + 1]) {
                    temp = sortedArray[i];
                    sortedArray[i] = sortedArray[i + 1];
                    sortedArray[i + 1] = temp;
                    flag = true;
                }
            }
        }

        return sortedArray;

    }

    private static void printValues(String message, int[] values) {
        System.out.println(message + Arrays.toString(values));
    }

    public static void main(String[] args) {
        int[] myIntegers = getIntegers();
        printValues("Your input numbers: ", myIntegers);

        int[] mySortedArray = sortIntegers(myIntegers);
        printValues("Your sorted array: ", mySortedArray);
    }
}
