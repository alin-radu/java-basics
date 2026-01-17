package section18ExceptionHandling;

import java.io.FileInputStream;
import java.util.Scanner;

public class ResourcesBasics {

    static void Divide() throws Exception {

        String filePath = "/Users/alin-radu/alinSpinjitzu/personal/courses/Java-general/java-basics/2-Learn-JAVA-Programming-Beginner-to-Master-Abdul/java-basics-2/src/test.txt";
        try (FileInputStream fi = new FileInputStream(filePath);
             Scanner sc = new Scanner(fi)) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();
            System.out.println(a / c);
        }

    }

    public static void main(String[] args) throws Exception {
        try {
            Divide();
        } catch (Exception e) {
            System.out.println("exception output" + e);
            e.printStackTrace();
        }

        //int x=sc.nextInt();

        // System.out.println(x);

    }

}