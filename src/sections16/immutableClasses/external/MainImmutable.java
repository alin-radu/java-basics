package sections16.immutableClasses.external;

import sections16.immutableClasses.PersonImmutable;
import sections16.immutableClasses.hacker.PersonOfInterest;

public class MainImmutable {

    public static void main(String[] args) {

        PersonImmutable jane = new PersonImmutable("Jane", "01/01/1930");
        PersonImmutable jim = new PersonImmutable("Jim", "02/02/1932");
        PersonImmutable joe = new PersonImmutable("Joe", "03/03/1934");

        PersonImmutable[] johnsKids = {jane, jim, joe};
        PersonImmutable john = new PersonImmutable("John", "05/05/1900", johnsKids);

        System.out.println("original john -> " + john);

        PersonImmutable[] kids = john.getKids();
        kids[0] = jim;
        kids[1] = new PersonImmutable("Ann", "04/04/1936");
        System.out.println("first attempt to hack john's kids -> " + john);

        johnsKids[0] = new PersonImmutable("Ann", "04/04/1936");
        System.out.println("second attempt to hack john's kids -> " + john);

        LivingPerson johnLiving = new LivingPerson(john.getName(), john.getKids());
        System.out.println("johnLiving -> " + johnLiving);

        LivingPerson hackKid = new LivingPerson("HackKid", null);
        johnLiving.addKid(hackKid);
        System.out.println("first attempt to hack johnLiving's kids -> " + johnLiving);

        PersonOfInterest johnCopy = new PersonOfInterest(john);

        System.out.println(johnCopy);

        kids = johnCopy.getKids();
        kids[1] = hackKid;

        System.out.println(johnCopy);
        System.out.println("third attempt to hack john's kids -> " + john);
    }
}
