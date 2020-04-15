package threadcoreknowledge.threadobjectcommonmethods;

/**
 * 证明wait只释放当前的那把锁
 */
public class WaitNotifyReleaseOwnMonitor {


    private static volatile Object resouceA = new Object();
    private static volatile Object resouceB = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (resouceA) {
                System.out.println(Thread.currentThread().getName() + " got resourceA lock ");
                synchronized (resouceB) {
                    System.out.println(Thread.currentThread().getName() + " got resourceB lock ");
                    try {
                        System.out.println(Thread.currentThread().getName() + " releases resourceA lock ");
                        resouceA.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (resouceA) {
                System.out.println(Thread.currentThread().getName() + " got resourceA lock. ");
                System.out.println(Thread.currentThread().getName() + " try got resourceA lock. ");
                synchronized (resouceB) {
                    System.out.println(Thread.currentThread().getName() + " got resourceB lock. ");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
