package section17Streams.optionalBasics;

import section17Streams.studentEngagementChallenge.Course;
import section17Streams.studentEngagementChallenge.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Course pymc = new Course("PYMC", "Python Masterclass");
        Course jmc = new Course("JMC", "Java Masterclass");

        List<Student> students =
                Stream.generate(() -> Student.getRandomStudent(jmc, pymc))
                        .limit(1000)
                        .collect(Collectors.toList());

        // 1
//        Optional<Student> o1 = getStudent(null, "first");
//        System.out.println("o1 methods : Empty = " + o1.isEmpty() + ", Present = " + o1.isPresent());
//        System.out.println("o1 value : " + o1);

        // 2
        Optional<Student> o1 = getStudent(new ArrayList<>(), "first");
        System.out.println("o1 methods : Empty = " + o1.isEmpty() + ", Present = " + o1.isPresent());
        System.out.println("o1 value : " + o1);
//        if (o1.isPresent()) {
//            System.out.println("o1 value.get() : " + o1.get());
//        }
//        o1.ifPresent(student -> System.out.println("o1 value.get() : " + student));
        o1.ifPresentOrElse(student -> System.out.println("o1 value.get() : " + student), () -> System.out.println("o1 is Empty"));

        // 3
//        students.add(0, null);

        Optional<Student> o2 = getStudent(students, "first");
        System.out.println("o2 methods : Empty = " + o2.isEmpty() + ", Present = " + o2.isPresent());
        System.out.println("o2 value : " + o2);
        o2.ifPresent(student -> System.out.println("o2 value.get() : " + student));

//        Student firstStudent = (o2.isPresent() ? o2.get() : null);
//        Student firstStudent = (o2.orElse(null));
//        Student firstStudent = (o2.orElse(getDummyStudent(jmc)));
        Student firstStudent = (o2.orElseGet(() -> getDummyStudent(jmc)));
//        long id = (firstStudent == null) ? -1 : firstStudent.getStudentId();
        long id = firstStudent.getStudentId();
        System.out.println("firstStudent's id is : " + id);

        List<String> countries = students
                .stream()
                .map(Student::getCountryCode)
                .distinct()
                .toList();

        Optional.of(countries)
                .map(l -> String.join(", ", l))
                .filter(l -> l.contains("FR"))
                .ifPresentOrElse(System.out::println, () -> System.out.println("Missing FR"));

    }

    private static Optional<Student> getStudent(List<Student> list, String type) {

        if (list == null || list.isEmpty()) {
            return Optional.empty();
        } else if (type.equals("first")) {
//            return Optional.of(list.get(0));
            return Optional.ofNullable(list.get(0));
        } else if (type.equals("last")) {
//            return Optional.of(list.get(list.size() - 1));
            return Optional.ofNullable(list.get(list.size() - 1));
        }
//        return Optional.of(list.get(new Random().nextInt(list.size())));
        return Optional.ofNullable(list.get(new Random().nextInt(list.size())));
    }

    private static Student getDummyStudent(Course... courses) {

        System.out.println("Getting the dummy student");
        return new Student("NO", 1, 1, "U",
                false, courses);
    }

}
