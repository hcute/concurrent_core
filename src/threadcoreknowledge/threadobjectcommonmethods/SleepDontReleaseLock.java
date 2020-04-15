package threadcoreknowledge.threadobjectcommonmethods;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SleepDontReleaseLock implements Runnable {

    private static final Lock lock = new ReentrantLock();
    @Override
    public void run() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "获取到了锁");
        try {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + "已苏醒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        SleepDontReleaseLock sleepDontReleaseLock = new SleepDontReleaseLock();
        Thread t1 = new Thread(sleepDontReleaseLock);
        Thread t2 = new Thread(sleepDontReleaseLock);

        t1.start();
        t2.start();
    }
}
