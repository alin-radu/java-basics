package section5Expressions.learning;

public class PlayingCat {
    public static void main(String[] args) {
        System.out.println(isCatPlaying(true, 10));
    }

    public static boolean isCatPlaying(boolean summer, int temperature) {
        if (summer == true) {
            if (temperature >= 25 && temperature <= 45) {
                return true;
            }
            return false;
        }
        if (temperature >= 25 && temperature <= 35) {
            return true;
        }
        return false;
    }
}

