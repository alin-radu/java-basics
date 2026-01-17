package section16.unmodifiableCollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        StringBuilder bobsNotes = new StringBuilder();
        StringBuilder billsNotes = new StringBuilder("Bill struggles with generics");

        Student bob = new Student("Bob", bobsNotes);
        Student bill = new Student("Bill", billsNotes);

        List<Student> students = new ArrayList<>(List.of(bob, bill));

        List<Student> studentsFirstCopy = new ArrayList<>(students);
        List<Student> studentsSecondCopy = List.copyOf(students);
        List<Student> studentsThirdCopy = Collections.unmodifiableList(students);

        bobsNotes.append("Bob was one of my first students.");
        studentsFirstCopy.get(0).getStudentNotes().append("Notes Copied");

        studentsFirstCopy.add(new Student("Bonnie", new StringBuilder()));

        studentsSecondCopy.get(0).getStudentNotes().append("Notes Copied, Second");

//        studentsThirdCopy.set(0, new Student("Bonnie", new StringBuilder()));
        studentsFirstCopy.sort(Comparator.comparing(Student::getName));
//        students.add(new Student("Bonnie", new StringBuilder()));


        StringBuilder bonniesNotes = studentsFirstCopy.get(2).getStudentNotes();
        bonniesNotes.append("Bonnie is taking 3 of my courses");

        System.out.println("students -----------------------");
        students.forEach(System.out::println);

        System.out.println("studentsFirstCopy -----------------------");
        studentsFirstCopy.forEach(System.out::println);

        System.out.println("studentsSecondCopy -----------------------");
        studentsSecondCopy.forEach(System.out::println);

        System.out.println("studentsThirdCopy -----------------------");
        studentsThirdCopy.forEach(System.out::println);
        System.out.println("-----------------------");
    }
}