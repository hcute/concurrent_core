package threadcoreknowledge.threadobjectcommonmethods;

/**
 * 使用wait 和notify
 * 1.拿到锁就打印
 * 2.打印完唤醒其他线程，自己wait
 */
public class WaitNotifyPrintOddEvenWait {

    private static int count = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) {

        Runnable runnable = () -> {
            while (count <=100 ) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + ":" + count++);
                    lock.notify();
                    try {
                        if (count <=100) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();

    }
}
