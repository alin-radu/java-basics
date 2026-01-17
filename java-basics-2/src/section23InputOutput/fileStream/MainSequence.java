package section23InputOutput.fileStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;

public class MainSequence {

    public static void main(String[] args) {
        try (FileInputStream fis1 = new FileInputStream("files/source1.txt");
             FileInputStream fis2 = new FileInputStream("files/source2.txt");
             FileOutputStream fos2 = new FileOutputStream("files/destination.txt");
             SequenceInputStream sis = new SequenceInputStream(fis1, fis2);
        ) {
            // First, read and write from fis1
            int b;
            while ((b = fis1.read()) != -1) {
                fos2.write(b); // Write fis1 content
            }

            // Write a newline after the first stream content
            fos2.write(System.lineSeparator().getBytes()); // Adds a newline between streams

            // Now, read and write from fis2
            while ((b = fis2.read()) != -1) {
                fos2.write(b); // Write fis2 content
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
