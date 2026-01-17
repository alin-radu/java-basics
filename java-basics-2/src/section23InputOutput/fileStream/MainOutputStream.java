package section23InputOutput.fileStream;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainOutputStream {
    public static void main(String[] args) {
        try (FileOutputStream fos = new FileOutputStream("files/test.txt")) {
            String str = "Learn Java programming.";

            // 1
//            fos.write(str.getBytes());

            // 2
            byte[] b = str.getBytes();
            for (byte element : b) {
                fos.write(element);
            }

            System.out.println("Task completed.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
