package exercices;

public class NumberPalindrome {
    public static void main(String[] args) {

    }

    public static boolean isPalindrome(int number) {
        int palindrome = number;
        int reverse = 0;

        while (palindrome != 0) {
            // extract the last digit
            int remainder = palindrome % 10;
            reverse = reverse * 10 + remainder;
            // drop the last digit
            palindrome = palindrome / 10;
        }

        return number == reverse;
    }
}