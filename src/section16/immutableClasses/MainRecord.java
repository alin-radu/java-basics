package section16.immutableClasses;

public class MainRecord {
    public static void main(String[] args) {
        PersonRecord jane = new PersonRecord("Jane", "01/01/1930");
        PersonRecord jim = new PersonRecord("Jim", "02/02/1932");
        PersonRecord joe = new PersonRecord("Joe", "03/03/1934");

        PersonRecord[] johnsKids = {jane, jim, joe};
        PersonRecord john = new PersonRecord("John", "05/05/1900", johnsKids);

        System.out.println(john);

        PersonRecord johnCopy = new PersonRecord("John", "05/05/1900");
        System.out.println(johnCopy);

        PersonRecord[] kids = johnCopy.kids();
        kids[0] = jim;
        kids[1] = new PersonRecord("Ann", "04/04/1936");
        System.out.println(johnCopy);

        System.out.println(john);
        johnsKids[0] = new PersonRecord("Test", "04/04/2024");
        System.out.println(john);

//        johnsKids[0] = new PersonRecord("Ann", "04/04/1936");
//        System.out.println(john);
    }
}
