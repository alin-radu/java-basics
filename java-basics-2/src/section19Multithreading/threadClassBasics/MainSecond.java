package section19Multithreading.threadClassBasics;

class MyThread2 extends Thread {
    MyThread2(String threadName) {
        super(threadName);
    }

    @Override
    public void run() {
        int count = 1;

        while (count < Integer.MAX_VALUE) {
            System.out.println(getName() + " thread,count: " + count + ", entered");

            count++;

//            Thread.yield();

//            try {
//                Thread.sleep(100);
//            } catch (Exception e) {
//                System.out.println(e);
//            }

        }
    }

}

public class MainSecond {
    public static void main(String[] args) {
        MyThread2 myThread2 = new MyThread2("myThread2");
        myThread2.setDaemon(true);

        myThread2.start();

        Thread mainThread = Thread.currentThread();
        System.out.println("mainThread.getName(): " + mainThread.getName());
        System.out.println("mainThread.getPriority(): " + mainThread.getPriority());

//        try {
//            mainThread.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        int countMain = 1;
        while (countMain < Integer.MAX_VALUE) {
            System.out.println(mainThread.getName() + " thread,count: " + countMain + ", entered");

            countMain++;

//            try {
//                Thread.sleep(100);
//            } catch (Exception e) {
//                System.out.println(e);
//            }

        }
    }
}
