package threadcoreknowledge.threadobjectcommonmethods;

/**
 * wait和 notify 的基本用法
 *  代码执行顺序
 *  证明wait 释放了锁
 */
public class Wait {

    public static Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread1 t1 = new Thread1();
        Thread2 t2 = new Thread2();
        t1.start();
        Thread.sleep(200);
        t2.start();


    }

    static class Thread1 extends Thread {

        @Override
        public void run() {
            synchronized (object) {
                System.out.println("线程"+ Thread.currentThread().getName()+"开始执行");
                try {
                    object.wait(); //释放了锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("线程" + Thread.currentThread().getName()+"获取到了锁");
            }
        }

    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                object.notify(); // 虽然唤醒了但是还是没有释放锁，知道synchronized结束
                System.out.println("线程" + Thread.currentThread().getName()+ "调用了notify");
            }
        }
    }
}
