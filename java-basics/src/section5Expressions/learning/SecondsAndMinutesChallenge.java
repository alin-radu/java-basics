package section5Expressions.learning;

public class SecondsAndMinutesChallenge {
    public static void main(String[] args) {
        String result = getDurationString(61, 45);
        System.out.println(result);
    }

    public static String getDurationString(int minutes, int seconds) {
        if (minutes < 0 || seconds < 0 || seconds >= 59) {
            return "Invalid value";
        }
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;

        return hours + "h " + remainingMinutes + "m " + seconds + "s";
    }
}

