package duke.choice;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ShopApp {
    public static void main(String[] args) {
        double total = 0.0;

        System.out.println("-------------------------");
        System.out.println("Welcome to Duke Choice Shop!");
        System.out.println("-------------------------");

        Customer c1 = new Customer("Pinky", 2);

        Clothing item1 = new Clothing("Blue Jacket", 20.9, "M");
        Clothing item2 = new Clothing("Orange Jacket", 10.5, "S");
        Clothing[] items = {item1, item2, new Clothing("Green Scarf", 5.0, "M"), new Clothing("Blue T-Shirt", 10.5, "S")};

        c1.addItems(items);

        System.out.println("Customer name: " + c1);
        System.out.println("-------------------------");

        System.out.println(c1.getName() + " clothing items: ");

        Arrays.sort(c1.getItems());

        for (Clothing item : c1.getItems()) {
            System.out.println(item);
        }

        total = c1.getTotalClothingCost();

        System.out.println("Total: " + total);
        System.out.println("-------------");

        // practice 7.1
        double average = 0;
        int count = 0;

        for (Clothing item : c1.getItems()) {
            if (item.getSize().equals("L")) {
                count++;
                average += item.getPrice();
            }
        }

        if (count == 0) {
            System.out.println("No clothing found");
        } else {
            average /= count;
            System.out.println("Average price: " + average + ", Count: " + count);
        }
        System.out.println("-------------");

        // web server
        try {
            WebServer wb = getWebServer("/items", items);
            wb.start();

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    private static WebServer getWebServer(String endpoint, Clothing[] items) throws UnknownHostException {
        ItemList list = new ItemList(items);

        Routing routing = Routing.builder()
                .get(endpoint, list)
                .build();

        ServerConfiguration configuration = ServerConfiguration.builder()
                .bindAddress(InetAddress.getLocalHost())
                .port(8888)
                .build();

        return WebServer.create(configuration, routing);
    }
}