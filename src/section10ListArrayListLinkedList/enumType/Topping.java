package section10ListArrayListLinkedList.enumType;

public enum Topping {

    MUSTARD,
    PICKLES,
    BACON,
    CHEDDAR,
    TOMATO;

    public double getPrice() {

        return switch (this) {
            case BACON -> 1.5;
            case CHEDDAR -> 1.0;
            case MUSTARD -> 1.99;
            default -> 0.0;
        };
    }
}
