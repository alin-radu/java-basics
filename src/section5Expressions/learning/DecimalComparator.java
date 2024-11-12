package section5Expressions.learning;

public class DecimalComparator {
    public static void main(String[] args) {
       System.out.println(areEqualByThreeDecimalPlaces(-3.175632, -3.175345));
    }

    public static boolean areEqualByThreeDecimalPlaces(double param1, double param2) {
        double firstNum = (int) (param1 * 1000) / 1000.0;
        double secondNum = (int) (param2 * 1000) / 1000.0;

        return firstNum == secondNum;
    }
}
