package section23InputOutput.serialisation.printStream;

import java.io.*;

class Student {
    int rollno;
    String name;
    String dept;

    @Override
    public String toString() {
        return "Student{" +
                "rollno=" + rollno +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }
}

public class Main {
    public static void main(String[] args) throws Exception {

        Student sWrite = new Student();
        sWrite.rollno = 10;
        sWrite.name = "John";
        sWrite.dept = "CSE";

        myWrite(sWrite, "files/Student1.txt");

        Student sRead = myRead("files/Student1.txt");

        System.out.println(sRead);

    }

    private static void myWrite(Student s, String d) throws IOException {
        FileOutputStream fos = new FileOutputStream(d);
        PrintStream ps = new PrintStream(fos);
        ps.println(s.rollno);
        ps.println(s.name);
        ps.println(s.dept);

        ps.close();
        fos.close();
    }

    private static Student myRead(String d) throws IOException {
        FileInputStream fis = new FileInputStream(d);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        Student s = new Student();
        s.rollno = Integer.parseInt(br.readLine());
        s.name = br.readLine();
        s.dept = br.readLine();

        fis.close();
        isr.close();
        br.close();

        return s;
    }
}
