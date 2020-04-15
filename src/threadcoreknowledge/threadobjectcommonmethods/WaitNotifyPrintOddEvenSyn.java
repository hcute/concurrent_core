package threadcoreknowledge.threadobjectcommonmethods;

/**
 * 使用synchronized 实现交替打印0-100的奇偶数，效率比较低下
 */
public class WaitNotifyPrintOddEvenSyn {

    // 新建两个线程
    // 一个处理偶数，一个处理奇数(用位运算)
    private static int count;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread enveThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (count < 100) {
                    synchronized (lock) {
                        if ((count&1) == 0) { // 速度更快
                            System.out.println(Thread.currentThread().getName() + "：" + count);
                            count++;
                        }

                    }
                }
            }
        },"偶数");

        Thread oddThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (count < 100) {
                    synchronized (lock) {
                        if ((count&1) != 0) { // 速度更快
                            System.out.println(Thread.currentThread().getName() + "：" + count);
                            count++;
                        }
                    }
                }
            }
        },"奇数");

        enveThread.start();
        oddThread.start();
    }


}
