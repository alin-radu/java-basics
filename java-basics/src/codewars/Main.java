package codewars;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(arrayOfLists));
    }

    static final List<?>[] arrayOfLists = new List[]{
            List.of(1, 2, 3),
            List.of(4, 5, 6),
            List.of(7, 8, 9)
    };


}
