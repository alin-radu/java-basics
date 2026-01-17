package section19Multithreading.interThreadCommunication;

class MyData {
    int value;
    boolean flag = true;

    synchronized public void set(int v) {
        while (!flag) {
            System.out.println("---> p");
            try {
                wait();
            } catch (Exception e) {
                System.out.println();
            }
        }

        value = v;
        flag = false;
        notify();
    }

    synchronized public int get() {
        int x = 0;
        while (flag) {
            System.out.println("---> c");
            try {
                wait();
            } catch (Exception e) {
                System.out.println();
            }
        }

        x = value;
        flag = true;
        notify();

        return x;
    }
}

class Producer extends Thread {
    MyData data;

    public Producer(MyData d) {
        data = d;
    }

    @Override
    public void run() {
        int count = 1;

        while (true) {
            data.set(count);
            System.out.println("Producer " + count);
            count++;
        }
    }
}

class Consumer extends Thread {
    MyData data;

    public Consumer(MyData d) {
        data = d;
    }

    @Override
    public void run() {
        int value;

        while (true) {
            value = data.get();
            System.out.println("Consumer " + value);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        MyData data = new MyData();

        Producer p = new Producer(data);
        Consumer c = new Consumer(data);

        p.start();
        c.start();

    }
}