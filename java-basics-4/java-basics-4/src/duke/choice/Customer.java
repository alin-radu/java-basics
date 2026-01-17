package duke.choice;

import java.util.Arrays;

public class Customer {

    private String name;
    private String size;
    private Clothing[] items;

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }

    public Customer(String name, int measurement) {
        this.name = name;
        setSize(measurement);
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public void setSize(int measurement) {
        switch (measurement) {
            case 1, 2, 3 -> setSize("S");
            case 4, 5, 6 -> setSize("M");
            case 7, 8, 9 -> setSize("L");
            default -> setSize("XL");
        }
        ;
    }

    public Clothing[] getItems() {
        return items;
    }
    public void addItems(Clothing[] someItems) {
        items = someItems;
    }

    public double getTotalClothingCost() {
        double total = 0.0;

        if (items == null) {
            return total;
        }

        for (Clothing item : items) {
            total = total + item.getPrice();
        }

        return total;
    }

    @Override
    public String toString() {
        return "Customer-TEST{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", items=" + Arrays.toString(items) +
                '}';
    }
}

