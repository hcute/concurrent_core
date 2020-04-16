package threadcoreknowledge.uncaughtexception;

/**
 * 不加 try catch 抛出4个异常，都带线程名字
 *  加了try catch ，期望捕获到第一个线程的异常，线程234不应该运行，希望打印出 Caught Exception
 *
 *  执行发现，根本没有 caught Exception ，线程234 依然运行并且抛出异常
 *
 *  说明 线程的异常不能用传统方法捕获
 */
public class CantCatchDirectly implements Runnable{

    public static void main(String[] args) throws InterruptedException {
        // 此处try catch 是针对的主线程
        try {
            new Thread(new CantCatchDirectly(),"MyThread-1").start();
            Thread.sleep(300);
            new Thread(new CantCatchDirectly(),"MyThread-2").start();
            Thread.sleep(300);
            new Thread(new CantCatchDirectly(),"MyThread-3").start();
            Thread.sleep(300);
            new Thread(new CantCatchDirectly(),"MyThread-4").start();
        }catch (RuntimeException e) {
            System.out.println("Caught Exception");
        }

    }
    @Override
    public void run() {
        // 必须要线程内部的run方法处理掉异常，但是不推荐这种方式
        try {
            throw new RuntimeException();
        }catch (RuntimeException e){
            System.out.println("Caught Exception");
        }

    }
}
