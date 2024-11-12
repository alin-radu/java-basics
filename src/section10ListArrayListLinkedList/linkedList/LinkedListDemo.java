package section10ListArrayListLinkedList.linkedList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class LinkedListDemo {
    private static void printList(List<String> linkedList) {
        Iterator<String> i = linkedList.iterator();

        System.out.println("----------------------------");
        while (i.hasNext()) {
            System.out.println("Now visiting " + i.next());
        }
        System.out.println("----------------------------");
    }

    private static boolean addInOrder(List<String> list, String newCity) {
        ListIterator<String> stringListIterator = list.listIterator();

        while (stringListIterator.hasNext()) {
            int comparison = stringListIterator.next().compareTo(newCity);
            if (comparison == 0) {
                // equal, not add
                System.out.println("!!! "+newCity + " is already included as a destination.");
                return false;
            } else if (comparison > 0) {
                // newCity should appear before this one
                stringListIterator.previous();
                stringListIterator.add(newCity);
                return true;
            } else if (comparison < 0) {
                // move on the next city
            }
        }
        stringListIterator.add(newCity);
        return true;
    }

    public static void main(String[] args) {
        List<String> placesToVisit = new LinkedList<>();
        addInOrder(placesToVisit, "Sydney");
        addInOrder(placesToVisit, "Melbourne");
        addInOrder(placesToVisit, "Brisbane");
        addInOrder(placesToVisit, "Melbourne");
        addInOrder(placesToVisit, "Perth");
        addInOrder(placesToVisit, "Canberra");
        addInOrder(placesToVisit, "Adelaide");
        addInOrder(placesToVisit, "Darwin");

        printList(placesToVisit);
    }
}
