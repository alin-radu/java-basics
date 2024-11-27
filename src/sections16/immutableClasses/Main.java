package sections16.immutableClasses;

public class Main {
    public static void main(String[] args) {

//        Person jane = new Person();
//        jane.setName("Jane");
//
//        Person jim = new Person();
//        jim.setName("Jim");
//
//        Person joe = new Person();
//        joe.setName("Joe");
//
//        Person john = new Person();
//        john.setName("John");
//        john.setDob("05/05/1900");
//        john.setKids(new Person[]{jane, jim, joe});
//        System.out.println(john);
//
//        john.setName("Jacob");
//        john.setKids(new Person[]{new Person(), new Person()});
//        System.out.println(john);

        Person jane = new Person("Jane", "01/01/1930");
        Person jim = new Person("Jim", "02/02/1932");
        Person joe = new Person("Joe", "03/03/1934");

        Person[] johnsKids = {jane, jim, joe};
        Person john = new Person("John", "05/05/1900", johnsKids);
//        Person john = new Person("John", "05/05/1900", Arrays.copyOf(johnsKids, johnsKids.length));

        System.out.println("1 -> " + john);

//        john.setKids(new Person[]{new Person("Ann", "04/04/1930")});
//        System.out.println("2 -> " + john);

        johnsKids[0] = new Person("HackKid", "04/04/1930");
        System.out.println("3 -> " + john);

//        Person[] kids = john.getKids();
//        kids[0] = jim;
//        System.out.println(john);
//
//        kids = null;
//        System.out.println(john);
//
//        john.setKids(kids);
//        System.out.println(john);
    }
}
