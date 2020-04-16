package threadcoreknowledge.threadobjectcommonmethods;

import java.util.concurrent.TimeUnit;

/**
 * 等待的是主线程，主线程interrupt
 */
public class JoinInterrupt {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Runnable r = () -> {
            try {
                mainThread.interrupt();
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "finish!");
            } catch (InterruptedException e) {
                System.out.println("子线程被中断了");
            }
        };

        Thread thread = new Thread(r);
        thread.start();
        System.out.println("等待子线程运行完毕");

        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "中断了");
            thread.interrupt();
        }
        System.out.println("子线程已运行完成");
    }
}
