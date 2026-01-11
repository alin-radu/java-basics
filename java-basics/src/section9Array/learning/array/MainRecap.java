package section9Array.learning.array;

import java.util.Arrays;
import java.util.Random;

public class MainRecap {

    public static void main(String[] args) {

        // instantiate an Array
        int[] integerArray = new int[10];
        integerArray[0] = 1;
        integerArray[1] = 2;
        integerArray[2] = 3;
        integerArray[3] = 4;
        integerArray[4] = 5;

        for (int item : integerArray) {
            System.out.println(item);
        }

        int[] arrayInitializer = new int[]{1, 2, 3, 4, 5};
        int[] anonymousArrayInitializer = {1, 2, 3, 4, 5};

        System.out.println(Arrays.toString(integerArray));
        System.out.println(Arrays.toString(arrayInitializer));
        System.out.println(Arrays.toString(anonymousArrayInitializer));
    }

    private static int[] getRandomArray(int customLength) {

        Random random = new Random();
        int[] newInt = new int[customLength];
        for (int i = 0; i < customLength; i++) {
            newInt[i] = random.nextInt(100);
        }

        return newInt;
    }
}
