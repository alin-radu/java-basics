package section3InputOutput.characterStreams;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    static String inFileStr = "files/text.txt";

    public static void main(String[] args) {
        fileReadWithBufferAndArray(inFileStr);
        fileReadWithNI2(inFileStr);
    }

    public static void fileReadWithBufferAndArray(String inFileStr) {
        System.out.println("\nInside fileReadWithBufferAndArray ...");

        // used for benchmarking
        long startTime, elapsedTime;

        // print file length
        File fileIn = new File(inFileStr);
        System.out.println("File size is " + fileIn.length() + " bytes");

        try (
                FileInputStream fis = new FileInputStream(inFileStr);
                InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr)
        ) {
            startTime = System.nanoTime();

            char[] charBuf = new char[4000];
            int numCharsRead;
            // read characters into the buffer
            while ((numCharsRead = br.read(charBuf)) != -1) {
                System.out.print(new String(charBuf, 0, numCharsRead));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        elapsedTime = System.nanoTime() - startTime;
        System.out.println();
        System.out.println("---");
        System.out.println("Elapsed Time is " + (elapsedTime / 1000000.0) + " msec");
    }

    public static void fileReadWithNI2(String inFileStr) {
        System.out.println("\nInside fileReadWithNI2 ...");

        // used for benchmarking
        long startTime, elapsedTime;

        // print file length
        File fileIn = new File(inFileStr);
        System.out.println("File size is " + fileIn.length() + " bytes");

        Path path = Path.of(inFileStr);

        try {
            startTime = System.nanoTime();

            var filesReadAllBytes = Files.readAllBytes(path);

//            var stringFilesReadAllBytes = new String(filesReadAllBytes);
//            System.out.println(stringFilesReadAllBytes);

            var filesReadString = Files.readString(path);
            System.out.println(filesReadString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        elapsedTime = System.nanoTime() - startTime;
        System.out.println();
        System.out.println("---");
        System.out.println("Elapsed Time is " + (elapsedTime / 1000000.0) + " msec");
    }
}
