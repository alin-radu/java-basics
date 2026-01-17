package section5Expressions.learning;

public class IntEqualityPrinter {
    public static void main(String[] args) {
        printEqual(1, 1, 1);
        printEqual(1, 1, 2);
        printEqual(-1, -1, -1);
        printEqual(1, 2, 3);

    }

    public static void printEqual(int param1, int param2, int param3) {
        if (param1 < 0 || param2 < 0 || param3 < 0) {
            System.out.println("Invalid Value");
        } else if (param1 == param2 && param2 == param3) {
            System.out.println("All numbers are equal");
        } else if (param1 == param2 && param2 != param3 || param1 == param3 && param3 != param2 || param2 == param3 && param3 != param1) {
            System.out.println("Neither all are equal or different");
        } else {
            System.out.println("All numbers are different");
        }
    }

}
