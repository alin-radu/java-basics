package section15JavaCollections.basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Card> deck = Card.getStandardDeck();
        Card.printDeck(deck);

        System.out.println("------------------------------------------------------");
        System.out.println(">>> 1");
        Card[] cardArray = new Card[13];
        Card aceOfHearts = Card.getFaceCard(Card.Suit.HEART, 'A');
        Arrays.fill(cardArray, aceOfHearts);
        Card.printDeck(Arrays.asList(cardArray), "Aces of Hearts", 1);

        System.out.println("------------------------------------------------------");
        System.out.println(">>> 2");
        List<Card> cards = new ArrayList<>(52);
        Collections.fill(cards, aceOfHearts);
        System.out.println(cards);
        System.out.println("cards.size() =  " + cards.size());

        System.out.println("------------------------------------------------------");
        System.out.println(">>> 3");
        List<Card> acesOfHearts = Collections.nCopies(13, aceOfHearts);
        Card.printDeck(acesOfHearts, "Aces of Hearts", 1);

        System.out.println("------------------------------------------------------");
        System.out.println(">>> 4");
        Card kingOfClubs = Card.getFaceCard(Card.Suit.CLUB, 'K');
        List<Card> kingsOfClubs = Collections.nCopies(13, kingOfClubs);
        Card.printDeck(kingsOfClubs, "Kings of Clubs", 1);

        System.out.println("------------------------------------------------------");
        System.out.println(">>> 5");
        Card.printDeck(cards, "Card Collection before Aces added", 2);
        Collections.addAll(cards, cardArray);
        Collections.addAll(cards, cardArray);
        Card.printDeck(cards, "Card Collection with Aces added", 2);

        System.out.println("------------------------------------------------------");
        System.out.println(">>> 6");
        Collections.copy(cards, kingsOfClubs);
        Card.printDeck(cards, "Card Collection with Kings copied", 2);

        System.out.println("------------------------------------------------------");
        System.out.println(">>> 7");
        cards = List.copyOf(kingsOfClubs);
        Card.printDeck(cards, "List Copy of Kings", 1);
    }
}
