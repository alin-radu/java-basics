package section5Expressions.learning;

public class TeenNumberChecker {
    public static void main(String[] args) {

    }

    public static boolean hasTeen (int param1,int param2, int param3 ){
        return isTeen(param1) || isTeen(param2) || isTeen(param3);
    }

    public static boolean isTeen (int param1){
        return param1 >= 13 && param1 <=19;
    }
}
