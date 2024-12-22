package section21Concurrency.executorServiceBasics;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceChallenge {

    private static final Random random = new Random();

    public static void main(String[] args) {

        ShoeWarehouse warehouse = new ShoeWarehouse();

        ExecutorService orderingService = Executors.newCachedThreadPool();

        Callable<Order> orderingTask = () -> {
            {
                Order newOrder = generateOrder();

                try {
                    Thread.sleep(random.nextInt(500, 5000));
                    warehouse.receiveOrder(newOrder);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                return newOrder;
            }
        };

        // v1
//        List<Callable<Order>> tasks = Collections.nCopies(15, orderingTask);
//        try {
//            orderingService.invokeAll(tasks);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        // v2
        try {
            for (int j = 0; j < 15; j++) {
                Thread.sleep(random.nextInt(5, 20));
                orderingService.submit(() -> warehouse.receiveOrder(generateOrder()));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            orderingService.shutdown();
        }

        try {
            boolean isOrderingServiceCompleted = orderingService.awaitTermination(6, TimeUnit.SECONDS);
            System.out.println("isOrderingServiceCompleted: " + isOrderingServiceCompleted);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            warehouse.shutDown();
        }
    }

    private static Order generateOrder() {
        return new Order(
                random.nextLong(1000000, 9999999),
                ShoeWarehouse.PRODUCT_LIST[random.nextInt(0, 5)],
                random.nextInt(1, 4));
    }
}
