package section5Expressions.learning;

public class MinutesToYearsAndDaysCalculator {
    public static void main(String[] args) {
        printYearsAndDays(561600);
    }

    public static void printYearsAndDays(long minutes) {
        if (minutes < 0) {
            System.out.println("Invalid Values");
            return;
        }
        int day = 60 * 24;
        long days = minutes / day % 365;
        long years = minutes / (365 * day);

        System.out.println(minutes + " min = " + years + " y " + days + " d");
    }

}
