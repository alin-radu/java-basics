package section9Array.challenge;

import java.util.Arrays;

public class ReverseArray {


    public static void main(String[] args) {

        int[] array = {1, 5, 6, 7};


        reverse(array);


    }

    private static void reverse(int[] array) {
        System.out.println("Array = " + Arrays.toString(array));
        int maxIndex = array.length - 1;
        int halfLength = array.length / 2;

        for (int i = 0; i < halfLength; i++) {
            int temp = array[i];
            array[i] = array[maxIndex - i];
            array[maxIndex - i] = temp;
        }
        System.out.println("Reversed array = " + Arrays.toString(array));
    }
}
