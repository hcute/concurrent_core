package threadcoreknowledge.jmm;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用volatile
 */
public class NoVolatile2 implements Runnable{

    volatile boolean done = false;
    AtomicInteger readA = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        NoVolatile2 useVolatile1 = new NoVolatile2();
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
            flipDown();
            readA.incrementAndGet();
        }

    }

    private void flipDown() {
        done = !done; // 这个操作就不是原子操作
    }

}
