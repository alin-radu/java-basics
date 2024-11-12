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

        // inheritance
        Animal animal = new Animal("Generic Animal", "Huge", 400);
        doAnimalStuff(animal, "slow");

        Dog dog = new Dog();
        doAnimalStuff(dog, "fast");

        Dog yorkie = new Dog("Yorkie", 15);
        doAnimalStuff(yorkie, "fast");
        Dog retriever = new Dog("Labrador Retriever", 65,
                "Floppy", "Swimmer");
        doAnimalStuff(retriever, "slow");

        Dog wolf = new Dog("Wolf", 40);
        doAnimalStuff(wolf, "slow");

        Fish goldie = new Fish("Goldfish", 0.25, 2, 3);
        doAnimalStuff(goldie, "fast");

        // string tests
        String a = "abc";
        String b = new String("abc");

        String c = "abc";



        System.out.println("-------------- start --- string comparison tests");
        if(a == b){
            System.out.println("a == b");
        }

        if(a == c){
            System.out.println("a == c");
        }

        if(a.equals(b)){
            System.out.println("a.equals(b)");
        }

        System.out.println("-------------- end --- string comparison tests");


    }

    public static void doAnimalStuff(Animal animal, String speed) {
        System.out.println(" ");
        animal.makeNoise();
        animal.move(speed);
        System.out.println(animal);
        System.out.println("_ _ _ _");
    }
}
