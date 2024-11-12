package exercices;

public class SumOddRange {
    public static void main(String[] args) {
        sumOdd(1, 100);
    }

    public static boolean isOdd(int number) {
        if (number < 0) {
            return false;
        } else if (number % 2 != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static int sumOdd(int start, int end) {
        if (start < 0 && end < 0 || start > end) {
            return -1;
        }

        int sum = 0;
        for (int i = start; i <= end; i++) {
            if (isOdd(i)) {
                sum = sum + i;
            }
        }
        System.out.println(sum);
        return sum;
    }
}
