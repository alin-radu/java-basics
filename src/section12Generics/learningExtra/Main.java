package section12Generics.learningExtra;

import section12Generics.learningExtra.model.LPAStudent;
import section12Generics.learningExtra.model.Student;
import section12Generics.learningExtra.util.QueryList;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        int studentCount = 10;

        // Student
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < studentCount; i++) {
            students.add(new Student());
        }
        students.add(new LPAStudent());
        printList(students);

        // LPAStudent, subclass of Student
        List<LPAStudent> lpaStudents = new ArrayList<>();
        for (int i = 0; i < studentCount; i++) {
            lpaStudents.add(new LPAStudent());
        }
        printList(lpaStudents);

//        List<String> stringListTest = Arrays.asList("Ann", "Bill", "Cathy", "John", "Tim");
//        printList(stringListTest);

        var queryList = new QueryList<>(lpaStudents);
        var matches = queryList.getMatches(
                "Course", "Python");
        printList(matches);

        var students2021 =
                QueryList.getMatches(students, "YearStarted", "2021");
        printList(students2021);

//        QueryList<Employee> employeeList = new QueryList<>();
    }

    // type argument
    public static void printList(List<? extends Student> students) {

//        Student last = students.get(students.size()-1);
//        students.set(0, last);

        for (var student : students) {
            System.out.println(student.getYearStarted());
        }
        System.out.println();
    }

    // generic method
//    public static <T extends Student> void printList(List<T> students) {
//        for (var student : students) {
//            System.out.println(student.getYearStarted());
//        }
//        System.out.println();
//    }

//    public static void printList(List students) {
//
//        for (var student : students) {
//            System.out.println(student);
//        }
//        System.out.println();
//    }
}