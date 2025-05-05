package section15JavaCollections.hashing;

public class PlayingCard {

    private final String suit;
    private final String face;
    private final int internalHash;

    public PlayingCard(String suit, String face) {
        this.suit = suit;
        this.face = face;
        this.internalHash = (suit.equals("Hearts")) ? 11 : 12;
    }

    @Override
    public String toString() {
        return face + " of " + suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayingCard that = (PlayingCard) o;

        if (!suit.equals(that.suit)) return false;
        return face.equals(that.face);
    }

    @Override
    public int hashCode() {
        int result = suit.hashCode();
        result = 31 * result + face.hashCode();
        return result;
    }
}
