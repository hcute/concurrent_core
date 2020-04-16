package threadcoreknowledge.threadobjectcommonmethods;

import java.util.concurrent.TimeUnit;

/**
 * 分析join源码 知道原理，写出等价代码
 */
public class JoinPrinciple {

    public static void main(String[] args) throws InterruptedException {

        Runnable r = () ->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
        };

        Thread t1 = new Thread(r);
        t1.start();
        System.out.println("开始等待子线程运行完毕");
        t1.join();
//        synchronized (t1) {
//            t1.wait();
//        }
        System.out.println("所有线程执行完毕");
    }
}
