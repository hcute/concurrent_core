package threadcoreknowledge.threadobjectcommonmethods;

/**
 * 3个线程，线程1 和线程2 被阻塞，线程3唤醒他们，notify 和 notifyAll 唤醒他们
 *
 */
public class WaitNotifyNotifyAll implements Runnable{

    private static final Object resourceA = new Object();

    public static void main(String[] args) {
        WaitNotifyNotifyAll r = new WaitNotifyNotifyAll();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        Thread t3 = new Thread(() -> {
            synchronized (resourceA) {
                resourceA.notifyAll(); // 此处如果是notify则只能唤醒一个线程
                System.out.println(Thread.currentThread().getName() + " notified");
            }
        });
        t1.start();
        t2.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t3.start();
    }

    @Override
    public void run() {

        synchronized (resourceA) {
            System.out.println(Thread.currentThread().getName() + " got resourceA lock");

            try {
                System.out.println(Thread.currentThread().getName() +" waits to start, release lock ");
                resourceA.wait();
                System.out.println(Thread.currentThread().getName() +" is waiting to end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
