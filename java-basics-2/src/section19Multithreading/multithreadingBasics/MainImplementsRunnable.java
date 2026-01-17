package section19Multithreading.multithreadingBasics;

class MyRunnable implements Runnable {

    @Override
    public void run() {
        int i = 1;
        while (i < Integer.MAX_VALUE) {
            System.out.println(i + " Hello");
            i++;
        }
    }
}

public class MainImplementsRunnable {

    public static void main(String[] args) {

        MyRunnable myR = new MyRunnable();
        Thread t = new Thread(myR);
        t.start();

        int i = 1;
        while (i < Integer.MAX_VALUE) {
            System.out.println(i + " World");
            i++;
        }
    }
}