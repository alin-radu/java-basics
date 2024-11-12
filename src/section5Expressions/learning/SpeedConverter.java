package section5Expressions.learning;

public class SpeedConverter {

    public static void main(String[] args) {
        printConversion(15);
        printConversion(-1);

    }

    public static long toMilesPerHour(double kilometersPerHour){
        if(kilometersPerHour < 0) {
            return -1;
        }
        return Math.round(kilometersPerHour / 1.609);
    }

    public static void printConversion(double kilometersPerHour){
        if(kilometersPerHour < 0) {
            System.out.println("Invalid Value");
            return;
        }
        long getMilesPerHour = toMilesPerHour(kilometersPerHour);
        System.out.println(kilometersPerHour + " km/h = " +getMilesPerHour + " mi/h");
    }

}
