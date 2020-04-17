package threadcoreknowledge.jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用volatile
 */
public class UseVolatile1 implements Runnable{

    volatile boolean done = false;
    AtomicInteger readA = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        UseVolatile1 useVolatile1 = new UseVolatile1();
        Thread t1 = new Thread(useVolatile1);
        Thread t2 = new Thread(useVolatile1);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(useVolatile1.done);
        System.out.println(useVolatile1.readA.get());
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            setDone();
            readA.incrementAndGet();
        }

    }

    private void setDone() {
        done = true; // 这个操作是原子操作，但是a++不具备原子操作
    }
}
