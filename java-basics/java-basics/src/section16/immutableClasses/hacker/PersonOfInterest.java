package section16.immutableClasses.hacker;

import section16.immutableClasses.PersonImmutable;

public class PersonOfInterest extends PersonImmutable {

    public PersonOfInterest(PersonImmutable person) {
        super(person);
    }

// removed after making getKids in parent class final
//    @Override
//    public PersonImmutable[] getKids() {
//        return super.kids;
//    }
}
