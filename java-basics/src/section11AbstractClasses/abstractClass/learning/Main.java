package section11AbstractClasses.abstractClass.learning;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

//        Animal animal = new Animal("animal", "big", 100);

        Dog dog = new Dog("Wolf", "big", 100);
        dog.makeNoise();
        doAnimalStuff(dog);

        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(dog);
        animals.add(new Dog("German Shepard", "big", 150));
        animals.add(new Fish("Goldfish", "small", 1));
        animals.add(new Fish("Barracuda", "big", 75));
        animals.add(new Dog("Pug", "small", 20));

        animals.add(new Horse("Clydesdale", "large", 1000));

        for (Animal animal : animals) {
            doAnimalStuff(animal);
            if (animal instanceof Mammal currentMammal) {
                currentMammal.shedHair();
            }
//            With Pattern Matching for instanceof: In Java 16 and later, you can do this more concisely using pattern matching.
//            The syntax if (animal instanceof Mammal currentMammal) combines the type check and cast in a single step.
//            If animal is indeed a Mammal, then currentMammal will be assigned as a reference of type Mammal without needing a separate cast.

//            if (animal instanceof Mammal) {
//                Mammal currentMammal = (Mammal) animal;  // Cast needed
//                currentMammal.shedHair();
//            }
        }

    }

    private static void doAnimalStuff(Animal animal) {

        animal.makeNoise();
        animal.move("slow");
    }

}