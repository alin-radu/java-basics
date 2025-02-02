package exercices;

public class FirstLastDigitSum {
    public static void main(String[] args) {
      int a =  sumFirstAndLastDigit(257);
    }

    public static int sumFirstAndLastDigit(int number) {
        if (number < 0) {
            return -1;
        }
        if (number < 10) {
            return number * 2;
        }

        int lastDigit = 0;
        int firstDigit = 0;
        boolean isLastDigit = false;

        while (number > 0) {
            int digit = number % 10;

            if (!isLastDigit) {
                lastDigit = digit;
                isLastDigit = true;
            } else {
                firstDigit = digit;
            }

            number = number / 10;
        }

        int sum = lastDigit + firstDigit;

        return sum;

    }
}
