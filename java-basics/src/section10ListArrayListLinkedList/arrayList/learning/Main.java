package section10ListArrayListLinkedList.arrayList.learning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // 1
        GroceryItem[] groceryArray = new GroceryItem[3];
        groceryArray[0] = new GroceryItem("milk");
        groceryArray[1] = new GroceryItem("apples", "PRODUCE", 6);
        groceryArray[2] = new GroceryItem("oranges", "PRODUCE", 5);
        System.out.println("groceryArray: " + Arrays.toString(groceryArray));

        GroceryItem[] groceryItems2 = {new GroceryItem("milk")};
        System.out.println("groceryArray2: " + Arrays.toString(groceryItems2));

        // raw use of a type
        ArrayList groceryArrayList = new ArrayList();
        groceryArrayList.add(new GroceryItem("Butter"));
        groceryArrayList.add("Yogurt");

        // arrayList methods
        ArrayList<GroceryItem> groceryList = new ArrayList<>();
        groceryList.add(new GroceryItem("Butter"));
        groceryList.add(new GroceryItem("Milk"));
        groceryList.add(new GroceryItem("Oranges", "PRODUCE", 5));
        groceryList.set(0, new GroceryItem("Apples", "PRODUCE", 6));
        groceryList.remove(1);
        System.out.println("groceryList: " + groceryList);

        // creating an ArrayList using an immutable collection
        String[] items = {"apples", "bananas"};
        List<String> itemsList = List.of(items);
        ArrayList<String> itemsArrayList = new ArrayList<>(itemsList);
        itemsArrayList.add("cherry");
        System.out.println("itemsArrayList: " + itemsArrayList);

        // Arrays vs ArrayList
        String[] originalArray = new String[]{"First", "Second", "Third"};
        var originalList = Arrays.asList(originalArray);

        originalList.set(0, "one");
        System.out.println("list: " + originalList);
        System.out.println("array: " + Arrays.toString(originalArray));

        originalList.sort(Comparator.naturalOrder());
        System.out.println("array: " + Arrays.toString(originalArray));

        List<String> newList = Arrays.asList("Sunday", "Monday", "Tuesday");
        System.out.println(newList);

        ArrayList<String> stringList = new ArrayList<>(List.of("Jan", "Feb", "Mart"));
        String[] stringArray = stringList.toArray(new String[10]);

        System.out.println("string array -" + Arrays.toString(stringArray));
    }
}

record GroceryItem(String name, String type, int count) {

    public GroceryItem(String name) {
        this(name, "DAIRY", 1);
    }

    @Override
    public String toString() {
        return String.format("%d %s in %s", count, name.toUpperCase(), type);
    }
}
