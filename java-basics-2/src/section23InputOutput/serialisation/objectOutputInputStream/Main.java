package section23InputOutput.serialisation.objectOutputInputStream;

import java.io.*;

public class Main {
    static class Student implements Serializable {
        private int rollno;
        private String name;
        private float avg;
        private String dept;
        public static int Data = 10;
        public transient int t;

        public Student() {
        }

        public Student(int rollno, String name, float avg, String dept) {
            this.rollno = rollno;
            this.name = name;
            this.avg = avg;
            this.dept = dept;
            Data = 500;
            t = 500;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "rollno=" + rollno +
                    ", name='" + name + '\'' +
                    ", avg=" + avg +
                    ", dept='" + dept + '\'' +
                    ", data='" + Data + '\'' +
                    ", t=" + t +
                    '}';
        }
    }

    public static void main(String[] args) {
        try (
                FileOutputStream fos = new FileOutputStream("files/Student3.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            Student s = new Student(10, "John", 89.9f, "CSE");

            oos.writeObject(s);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (
                FileInputStream fis = new FileInputStream("files/Student3.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {

            Student s = (Student) ois.readObject();

            System.out.println(s);

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
