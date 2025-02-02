package section5Expressions.learning;

public class BarkingDog {
    public static void main(String[] args) {
        boolean result =  shouldWakeUp(true,1);
        System.out.println(result);
    }

    public static boolean shouldWakeUp(boolean barking,  int hourOfDay){
        if(barking && (hourOfDay < 8 || hourOfDay > 23)){
            return true;
        }
        return false;
    }
}
