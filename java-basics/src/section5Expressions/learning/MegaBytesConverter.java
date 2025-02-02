package section5Expressions.learning;

public class MegaBytesConverter {
    public static void main(String[] args) {
        printMegaBytesAndKiloBytes(2500);
    }

    public static void printMegaBytesAndKiloBytes(int kiloBytes) {
        if (kiloBytes < 0) {
            System.out.println("Invalid Value");
            return;
        }
        int megaBiteUnit = 1024;
        int megaBytes = kiloBytes / megaBiteUnit;
        int remainingKilobytes = kiloBytes % megaBiteUnit;
        System.out.println(kiloBytes + " KB = " + megaBytes + " MB and " + remainingKilobytes + " KB");
    }
}
