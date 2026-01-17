package section23InputOutput.byteArrayStream;

import java.io.ByteArrayInputStream;

public class Main {
    public static void main(String[] args) {
        byte[] b = {'a', 'b', 'c'};

        // 1
        ByteArrayInputStream bis1 = new ByteArrayInputStream(b);
        int tempB;
        while ((tempB = bis1.read()) != -1) {
            System.out.println((char)tempB);
        }

        // 2
        ByteArrayInputStream bis2 = new ByteArrayInputStream(b);
        String str2 = new String(bis2.readAllBytes());
        System.out.println(str2);
    }
}
