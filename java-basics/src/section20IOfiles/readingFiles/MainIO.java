package section20IOfiles.readingFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainIO {
    public static void main(String[] args) {

        try (FileReader reader = new FileReader("files/file.txt")) {
            char[] block = new char[1000];
            int data;
            while ((data = reader.read(block)) != -1) {
                String content = new String(block, 0, data);
                System.out.printf("---> [%d chars] %s%n", data, content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("-----------------------------------");
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader("files/file.txt"))) {

            bufferedReader.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
