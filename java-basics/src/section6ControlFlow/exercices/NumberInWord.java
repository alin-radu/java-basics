package exercices;

public class NumberInWord {
    public static void main(String[] args) {
    }

    public static void getStringFromNumber(int param) {
        switch (param) {
            case 0, 1 -> System.out.println("ZERO");
            case 2 -> System.out.println("ONE");
            case 3 -> System.out.println("TWO");
            default -> System.out.println("OTHER");
        }
    }

}