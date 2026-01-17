package section3InputOutput.byteStreams;

import java.io.*;

public class Main {
    static String inFileStr = "files/picture.jpg";
    static String outFileStrNoBuffer = "files/picture-out-no-buffer.jpg";
    static String outFileStrWithBuffer = "files/picture-out-with-buffer.jpg";

    public static void main(String[] args) {
        fileCopyNoBuffer(inFileStr, outFileStrNoBuffer);
        fileCopyWithBufferAndArray(inFileStr, outFileStrWithBuffer);
    }

    public static void fileCopyNoBuffer(String inFileStr, String outputFileStr) {
        System.out.println("\nInside fileCopyNoBuffer ...");

        // used for benchmarking
        long startTime, elapsedTime;

        // print file length
        File fileIn = new File(inFileStr);
        System.out.println("File size is " + fileIn.length() + " bytes");

        try (
                FileInputStream fis = new FileInputStream(inFileStr);
                FileOutputStream fos = new FileOutputStream(outputFileStr)
        ) {
            startTime = System.nanoTime();

            int byteRead;
            // read a raw byte, returns an int of 0 to 256
            while ((byteRead = fis.read()) != -1) {
                fos.write(byteRead);
            }

            elapsedTime = System.nanoTime() - startTime;
            System.out.println("Elapsed Time is " + (elapsedTime / 1000000.0) + " msec");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fileCopyWithBufferAndArray(String inFileStr, String outputFileStr) {
        System.out.println("\nInside fileCopyWithBufferAndArray ...");

        // used for benchmarking
        long startTime, elapsedTime;
        startTime = System.nanoTime();

        // print file length
        File fileIn = new File(inFileStr);
        System.out.println("File size is " + fileIn.length() + " bytes");

        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inFileStr));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFileStr));
        ) {

            byte[] byteBuf = new byte[4000];
            int numBytesRead;
            while ((numBytesRead = bis.read(byteBuf)) != -1) {
                bos.write(byteBuf, 0, numBytesRead);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        elapsedTime = System.nanoTime() - startTime;
        System.out.println("fileCopyWithBufferAndArray: " + (elapsedTime / 1000000.0) + " msec");

    }
}
