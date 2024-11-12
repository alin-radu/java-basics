package section5Expressions.learning;

public class SwitchPractice {
    public static void main(String[] args) {
        int switchValue = 3;

        switch (switchValue) {
            case 1:
                System.out.println("Value was 1");
                break;
            case 2:
                System.out.println("Value was 2");
                break;
            case 3:
            case 4:
            case 5:
                System.out.println("Value was 3, 4 or 5.");
                break;
            default:
                System.out.println("Was not 1 or 2.");
                break;
        }

        String month = "JaNuarys";

        switch (month.toLowerCase()) {
            case "january":
                System.out.println("This is the month.");
                break;
            default:
                System.out.println("Not the mounth.");
                break;
        }
        System.out.println("Swich exited.");

    }
}
