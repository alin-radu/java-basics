package section9Array.learning.array;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        // instantiate an Array
        // 1.
        int[] intArray = new int[10];
        intArray[0] = 45;
        intArray[1] = 1;
        System.out.println(Arrays.toString(intArray));

        double[] doubleArray = new double[10];
        doubleArray[2] = 3.5;
        System.out.println(Arrays.toString(doubleArray));

        // 2.
        String[] stringArray = new String[] {"first", "second"};
        System.out.println(Arrays.toString(stringArray));

        // 3.
        int[] anonymousArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(Arrays.toString(anonymousArray));


        int[] newArray;
//        newArray = new int[] {5, 4, 3, 2, 1};
        newArray = new int[5];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = newArray.length - i;
        }
        System.out.println(Arrays.toString(newArray));
        for (int element : newArray) {
            System.out.print(element + " ");
        }
//        Object objectVariable = newArray;
        System.out.println("objectVariable is really an int array");

        Object[] objectArray = new Object[3];
        objectArray[0] = "Hello";
        objectArray[1] = new StringBuilder("World");
        objectArray[2] = newArray;
        System.out.println(Arrays.toString(objectArray));
    }
}
