package section21Concurrency.executorServiceBasics;

import section21Concurrency.ThreadColor;

import java.util.List;
import java.util.concurrent.*;

class ColorThreadFactory implements ThreadFactory {

    private String threadName;

    private int colorValue = 1;

    public ColorThreadFactory(ThreadColor color) {
        this.threadName = color.name();
    }

    public ColorThreadFactory() {
    }

    @Override
    public Thread newThread(Runnable r) {

        Thread thread = new Thread(r);
        String name = threadName;

        if (name == null) {
            name = ThreadColor.values()[colorValue].name();
        }

        if (++colorValue > (ThreadColor.values().length - 1)) {
            colorValue = 1;
        }
        thread.setName(name);
        return thread;
    }
}

@SuppressWarnings({"DuplicatedCode"})
public class MainBasics {
    public static void main(String[] args) {

        var multiExecutor = Executors.newCachedThreadPool();
        List<Callable<Integer>> taskList = List.of(
                () -> MainBasics.sum(1, 10, 1, "red"),
                () -> MainBasics.sum(10, 100, 10, "blue"),
                () -> MainBasics.sum(2, 20, 2, "green")
        );

        try {
            var results = multiExecutor.invokeAll(taskList);

            for (var result : results) {
                System.out.println(ThreadColor.ANSI_RESET.color() + result.get(500, TimeUnit.SECONDS));
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        } finally {
            multiExecutor.shutdown();
        }
    }

    public static void fifthMain(String[] args) {

        var multiExecutor = Executors.newCachedThreadPool();
        List<Callable<Integer>> taskList = List.of(
                () -> MainBasics.sum(1, 10, 1, "red"),
                () -> MainBasics.sum(10, 100, 10, "blue"),
                () -> MainBasics.sum(2, 20, 2, "green")
        );

        String[] colors = new String[]{"red", "green", "blue", "yellow", "cyan", "white", "purple"};

        try {
            var redValue = multiExecutor.submit(() -> MainBasics.sum(1, 10, 1, "red"));
            var blueValue = multiExecutor.submit(() -> MainBasics.sum(10, 100, 10, "blue"));
            var greenValue = multiExecutor.submit(() -> MainBasics.sum(2, 20, 2, "green"));

            try {
                System.out.println(ThreadColor.ANSI_RESET.color() + redValue.get(500, TimeUnit.SECONDS));
                System.out.println(ThreadColor.ANSI_RESET.color() + blueValue.get(500, TimeUnit.SECONDS));
                System.out.println(ThreadColor.ANSI_RESET.color() + greenValue.get(500, TimeUnit.SECONDS));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            multiExecutor.shutdown();
        }
    }

    public static void fourthMain(String[] args) {

        var multiExecutor = Executors.newCachedThreadPool();
        List<Callable<Integer>> taskList = List.of(
                () -> MainBasics.sum(1, 10, 1, "red"),
                () -> MainBasics.sum(10, 100, 10, "blue"),
                () -> MainBasics.sum(2, 20, 2, "green")
        );

        String[] colors = new String[]{"red", "green", "blue", "yellow", "cyan", "white", "purple"};

        try {
            multiExecutor.execute(() -> MainBasics.sum(1, 10, 1, "red"));
            multiExecutor.execute(() -> MainBasics.sum(10, 100, 10, "blue"));
            multiExecutor.execute(() -> MainBasics.sum(2, 20, 2, "green"));
            multiExecutor.execute(() -> MainBasics.sum(1, 10, 1, "yellow"));
            multiExecutor.execute(() -> MainBasics.sum(10, 100, 10, "cyan"));
            multiExecutor.execute(() -> MainBasics.sum(2, 20, 2, "purple"));

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(ThreadColor.ANSI_RESET.color() + "\nNext task will be executed.\n");

            for (var color : colors) {
                multiExecutor.execute(() -> MainBasics.sum(1, 10, 1, color));
            }

        } finally {
            multiExecutor.shutdown();
        }
    }

    public static void thirdMain(String[] args) {

        long startTime = System.currentTimeMillis();

        int noOfJobs = 10;
        int noOfThreads = 10;
        var multiExecutor = Executors.newFixedThreadPool(noOfThreads, new ColorThreadFactory());

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of available CPU cores: " + cores);

        for (int i = 0; i < noOfJobs; i++) {
            multiExecutor.execute(MainBasics::simulateWork);
        }
        multiExecutor.shutdown();

        // counterSize: 1_000_000_000;
        // threads: 1, time: 26_967 milliseconds;
        // threads: 10, time: 3_972 milliseconds;

        try {
            if (multiExecutor.awaitTermination(1, TimeUnit.MINUTES)) {
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                System.out.println("Total time elapsed: " + elapsedTime + " milliseconds");
            } else {
                System.out.println("Some jobs did not complete within the expected time.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void secondMain(String[] args) {

        var blueExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_BLUE));
        blueExecutor.execute(MainBasics::countDown);
        blueExecutor.shutdown();

        boolean isDone = false;
        try {
            isDone = blueExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (isDone) {
            System.out.println("Blue finished, starting Yellow");
            var yellowExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_YELLOW));
            yellowExecutor.execute(MainBasics::countDown);
            yellowExecutor.shutdown();

            try {
                isDone = yellowExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (isDone) {
                System.out.println("Yellow finished, starting Red");
                var redExecutor = Executors.newSingleThreadExecutor(new ColorThreadFactory(ThreadColor.ANSI_RED));
                redExecutor.execute(MainBasics::countDown);
                redExecutor.shutdown();

                try {
                    isDone = redExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (isDone) {
                    System.out.println("All processes completed");
                }
            }
        }
    }

    public static void firstMain(String[] args) {

        Thread blue = new Thread(
                MainBasics::countDown, ThreadColor.ANSI_BLUE.name());

        Thread yellow = new Thread(
                MainBasics::countDown, ThreadColor.ANSI_YELLOW.name());

        Thread red = new Thread(
                MainBasics::countDown, ThreadColor.ANSI_RED.name());

        blue.start();

        try {
            blue.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        yellow.start();

        try {
            yellow.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        red.start();

        try {
            red.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // count
    private static void countDown() {

        String threadName = Thread.currentThread().getName();
        var threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf(threadName.toUpperCase());
        } catch (IllegalArgumentException ignore) {
            // User may pass a bad color name, Will just ignore this error.
        }

        String color = threadColor.color();
        for (int i = 20; i >= 0; i--) {
            System.out.println(color + " " +
                    threadName.replace("ANSI_", "") + "  " + i);
        }
    }

    // simulateWork
    private static void simulateWork() {
        String threadName = Thread.currentThread().getName();
        long result = 0;

        for (int i = 0; i < 1_000_000_000; i++) {
            result += i;
        }

        System.out.println(threadName + " completed work, the result is " + result);
    }

    // sum
    private static int sum(int start, int end, int delta, String colorString) {

        var threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf("ANSI_" + colorString.toUpperCase());
        } catch (IllegalArgumentException ignore) {
            // User may pass a bad color name, Will just ignore this error.
        }

        String color = threadColor.color();
        int sum = 0;
        for (int i = start; i <= end; i += delta) {
            sum += i;
        }
        System.out.println(color + Thread.currentThread().getName() + ", " + colorString + "  " + sum);
        return sum;
    }
}

