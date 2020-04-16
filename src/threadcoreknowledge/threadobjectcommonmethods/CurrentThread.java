package threadcoreknowledge.threadobjectcommonmethods;

/**
 * 演示打印 main Thread-0 Thread-1
 */
public class CurrentThread implements Runnable {
    @Override
    public void run() {
        // 返回当前执行线程的引用
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        new CurrentThread().run();
        new Thread( new CurrentThread()).start();
        new Thread( new CurrentThread()).start();
    }
}
