package section5Expressions.learning;

public class ParsingValuesFromAString {
    public static void main(String[] args) {
        String numberAsString = "2018";

        int number = Integer.parseInt(numberAsString);

        numberAsString += 1;
        number += 1;

        System.out.println("numberAsString = " + numberAsString);
        System.out.println("number = " + number);
    }
}
