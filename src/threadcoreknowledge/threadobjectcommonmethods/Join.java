package threadcoreknowledge.threadobjectcommonmethods;

import java.util.concurrent.TimeUnit;

/**
 * 演示join ，注意语句的输出顺序
 */
public class Join {
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
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        System.out.println("开始等待子线程运行完毕");
        t1.join();
        t2.join();
        System.out.println("所有线程执行完毕");
    }
}
