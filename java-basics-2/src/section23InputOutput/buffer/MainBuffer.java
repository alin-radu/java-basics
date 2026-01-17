package section23InputOutput.buffer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class MainBuffer {
    public static void main(String[] args) {
        // Capture start time
        long startTime = System.nanoTime();

        try (FileInputStream fis = new FileInputStream("files/test.txt")) {
            String str1 = new String(fis.readAllBytes());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Capture end time
        long endTime = System.nanoTime();

        // Calculate the duration in nanoseconds
        long duration = endTime - startTime;

        // Convert to milliseconds for easier reading
        double durationInMilliseconds = duration / 1_000_000.0;

        System.out.println("durationInMilliseconds = " + durationInMilliseconds);

        // Capture start time
        long startTime2 = System.nanoTime();

        try (FileInputStream fis = new FileInputStream("files/test.txt");
             BufferedInputStream bis = new BufferedInputStream(fis);) {

            String str1 = new String(bis.readAllBytes());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Capture end time
        long endTime2 = System.nanoTime();

        // Calculate the duration in nanoseconds
        long duration2 = endTime2 - startTime2;

        // Convert to milliseconds for easier reading
        double durationInMilliseconds2 = duration2 / 1_000_000.0;

        System.out.println("durationInMilliseconds2 = " + durationInMilliseconds2);
    }
}
