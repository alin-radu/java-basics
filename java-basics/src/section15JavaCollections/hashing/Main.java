package section15JavaCollections.hashing;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        String aText = "Hello";
        String bText = "Hello";
        String cText = String.join("l", "He", "lo");
        String dText = "He".concat("llo");
        String eText = "hello";

        List<String> hellos = Arrays.asList(aText, bText, cText, dText, eText);

        hellos.forEach((s) -> System.out.println(s + ": " + s.hashCode() + " " + System.identityHashCode(s)));

        Set<String> mySet = new HashSet<>(hellos);

        System.out.println("mySet = " + mySet);
        System.out.println("# of elements = " + mySet.size());

        for (String setValue : mySet) {
            int count = 0;
            System.out.println("--- " + setValue + " --- start --------------------------");
            for (int i = 0; i < hellos.size(); i++) {
                System.out.print("- idx: " + i);
                System.out.print(", setValue: " + setValue);
                System.out.println(", hellos[i]: " + hellos.get(i));
                if (setValue == hellos.get(i)) {
                    System.out.println(">>> setValue == hellos.get(i): " + i + ", ");
                    count++;
                }

            }
            System.out.println("--- " + setValue + " , count: " + count + " --- end ------");

            System.out.println(" ");
        }
        System.out.println("---");

        PlayingCard aceHearts = new PlayingCard("Hearts", "Ace");
        PlayingCard kingClubs = new PlayingCard("Clubs", "King");
        PlayingCard queenSpades = new PlayingCard("Spades", "Queen");

        List<PlayingCard> cards = Arrays.asList(aceHearts, kingClubs, queenSpades);
        cards.forEach((s) -> System.out.println(s + ": " + s.hashCode()));

        System.out.println();

        Set<PlayingCard> deck = new HashSet<>();
        System.out.println("starting to add cards to the deck Set ...");
        for (PlayingCard card : cards) {
            if (!deck.add(card)) {
                System.out.println("Found a duplicate for " + card);
            } else {
                System.out.println("Card added to the deck, " + card);
            }
        }
        System.out.println("completed to add cards to the deck Set");
        System.out.println();
        System.out.println("Set content: "+ deck);

    }
}
