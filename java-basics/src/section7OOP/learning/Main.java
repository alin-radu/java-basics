package section7OOP.learning;

public class Main {
    public static void main(String[] args) {
        // introduction
        for (int i = 1; i <= 5; i++) {
            String name = switch (i) {
                case 1 -> "Mary";
                case 2 -> "John";
                case 3 -> "Bobby";
                case 4 -> "Jimmy";
                default -> "Anonymous";
            };

            Student s = new Student("S92300" + i, name, "05/11/1985", "Java MasterClass");
            LPAStudent lpaS = new LPAStudent("S92300" + i, name, "05/11/1985", "Java MasterClass");
//            System.out.println("Student- " + s);
//            System.out.println("LPAStudent- " + lpaS);
        }

    }
}
