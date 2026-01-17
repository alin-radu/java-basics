package section19Multithreading.multithreadingBasics;

class MyThread extends Thread {

    @Override
    public void run() {
        int i = 1;
        while (i < Integer.MAX_VALUE) {
            System.out.println(i + " Hello");
            i++;
        }
    }
}

public class MainExtendsThread {

    public static void main(String[] args) {

        MyThread myTt = new MyThread();
        myTt.start();

        int i = 1;
        while (i < Integer.MAX_VALUE) {
            System.out.println(i + " World");
            i++;
        }
    }
}