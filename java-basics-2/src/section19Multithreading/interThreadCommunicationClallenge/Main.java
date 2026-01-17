package section19Multithreading.interThreadCommunicationClallenge;

import java.util.ArrayList;
import java.util.List;

class WhiteBoard {
    String text;

    List<Integer> studentsList = new ArrayList<>();
    List<Integer> shouldReadList = new ArrayList<>();

    public void addStudentToClassroom(Integer studentID) {
        studentsList.add(studentID);
    }

    synchronized public void write(String t) {
        while (!shouldReadList.isEmpty())
            try {
                wait();
            } catch (Exception e) {
                System.out.println();
            }

        System.out.println("Teacher is Writing " + t);
        System.out.flush();

        text = t;
        shouldReadList = new ArrayList<>(studentsList);
        notifyAll();
    }

    synchronized public String read(Integer studentID) {
        while (shouldReadList.isEmpty() || !shouldReadList.contains(studentID))
            try {
                wait();
            } catch (Exception e) {
                System.out.println();
            }

        String t = text;
        shouldReadList.remove(studentID);

        if (shouldReadList.isEmpty())
            notify();

        return t;
    }

}

class Teacher extends Thread {
    WhiteBoard wb;

    String[] notes = {"Java is language", "It is OOPs", "It is Platform Independent", "It supports Thread", "end"};

    public Teacher(WhiteBoard w) {
        wb = w;
    }

    public void run() {
        for (String note : notes) wb.write(note);
    }

}

class Student extends Thread {

    Integer studentID;
    String name;
    List<String> info = new ArrayList<>();

    WhiteBoard wb;

    private static Integer studentCount = 0;

    public Student(String name, WhiteBoard wb) {
        studentID = studentCount++;
        this.name = name;
        this.wb = wb;
    }

    public void addStudentToClassroom() {
        wb.addStudentToClassroom(studentID);
    }

    public void run() {
        String text;

        do {
            text = wb.read(studentID);
            System.out.println(name + " Reading " + text);
            System.out.flush();
            info.add(text);
        } while (!text.equals("end"));

        System.out.println(name + " was read " + info);
    }

}

public class Main {
    public static void main(String[] args) {
        WhiteBoard wb = new WhiteBoard();
        Teacher t = new Teacher(wb);

        Student s1 = new Student("1. Adam", wb);
        Student s2 = new Student("2. Bobby", wb);
        Student s3 = new Student("3. Carmen", wb);
        Student s4 = new Student("4. Danny", wb);

        s1.addStudentToClassroom();
        s2.addStudentToClassroom();
        s3.addStudentToClassroom();
        s4.addStudentToClassroom();

        t.start();

        s1.start();
        s2.start();
        s3.start();
        s4.start();

    }
}