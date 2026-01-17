package section19Multithreading.threadClassBasics;

class MyRunnable implements Runnable {

    @Override
    public void run() {

    }
}

class MyThread extends Thread {
    MyThread(String name) {
        super(name);
        setPriority(Thread.MAX_PRIORITY - 2);
    }

    @Override
    public void run() {
        int count = 1;

        while (count < Integer.MAX_VALUE) {
            System.out.println("count: " + count + ", entered");

            try {
                System.out.println("count: " + count + ", sleep");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("e:" + e);
            }

            System.out.println("count: " + count + ", completed");

            count++;

        }

    }
}

public class Main {
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread1 = new Thread(myRunnable);

        MyThread myThread = new MyThread("MyThread_1");

        System.out.println("myThread.getId: " + myThread.getId());
        System.out.println("myThread.getName: " + myThread.getName());
        System.out.println("myThread.getPriority: " + myThread.getPriority());
        System.out.println("myThread.getState: " + myThread.getState());

        myThread.start();

        System.out.println("myThread.isAlive: " + myThread.isAlive());
        System.out.println("The Thread: " + myThread.getName() + ", started ...  ");

        System.out.println("myThread.getState: " + myThread.getState());
        System.out.println("myThread.isAlive: " + myThread.isAlive());

        myThread.interrupt();

    }
}
