package section19Multithreading.monitorBasics;

class MyData {

    // v1 for implementing monitor
//    void display(String str) {
//        synchronized (this) {
//            for (int i = 0; i < str.length(); i++) {
//                System.out.print(str.charAt(i));
//            }
//            System.out.println();
//        }
//    }

    // v2 for implementing monitor
    synchronized void display(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.print(str.charAt(i));
        }
        System.out.println();
    }
}

class MyThread1 extends Thread {
    MyData myData;

    MyThread1(MyData d) {
        myData = d;
    }

    @Override
    public void run() {
        myData.display("Hello World!!!");
    }
}

class MyThread2 extends Thread {
    MyData myData;

    MyThread2(MyData d) {
        myData = d;
    }

    @Override
    public void run() {
        myData.display("Welcome...");
    }
}

public class Main {
    public static void main(String[] args) {
        MyData myData = new MyData();
        MyThread1 myThread1 = new MyThread1(myData);
        MyThread2 myThread2 = new MyThread2(myData);

        myThread1.start();
        myThread2.start();
    }
}
