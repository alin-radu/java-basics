package section9Array.challenge;

import java.util.Arrays;
import java.util.Scanner;

public class SortedInputArray {
    private static Scanner scanner = new Scanner(System.in);

    private static int[] getIntegers(int number) {
        int[] values = new int[number];
        int valuesLength = number;


        System.out.println("Enter " + number + " integers: \r");
        for (int i = 0; i < valuesLength; i++) {
            values[i] = scanner.nextInt();
        }
        scanner.close();

        return values;
    }

    private static int[] sortIntegers(int[] array) {
        int sortedLength = array.length;
        int[] sortedArray = new int[sortedLength];
        for (int i = 0; i < sortedLength; i++) {
            sortedArray[i] = array[i];
        }

        boolean flag = true;
        int temp;
        while (flag) {
            flag = false;
            for (int i = 0; i < sortedLength - 1; i++) {
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
        int[] myIntegers = getIntegers(5);
        printValues("Your input numbers: ", myIntegers);

        int[] mySortedArray = sortIntegers(myIntegers);
        printValues("Your sorted array: ", mySortedArray);
    }
}
