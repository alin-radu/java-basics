package section23InputOutput.fileStream;

import java.io.FileInputStream;
import java.io.IOException;

public class MainInputStream {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("files/test.txt")) {

            // 1
            byte[] b = new byte[fis.available()];
            fis.read(b);
            String str = new String(b);
            System.out.println(str);
            System.out.println("Task completed.");

            // 2

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
