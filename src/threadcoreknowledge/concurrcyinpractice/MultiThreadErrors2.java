package threadcoreknowledge.concurrcyinpractice;

/**
 * 演示死锁
 */
public class MultiThreadErrors2 implements Runnable{

    int flag = 1;

    static Object object1 = new Object();
    static Object object2 = new Object();

    public static void main(String[] args) {
        MultiThreadErrors2 r1 = new MultiThreadErrors2();
        MultiThreadErrors2 r2 = new MultiThreadErrors2();
        r1.flag = 1;
        r2.flag = 0;
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();


    }
    @Override
    public void run() {
        System.out.println("flag = " + flag) ;
        if (flag == 1) {
            synchronized (object1){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object2){
                    System.out.println("1");
                }
            }
        }

        if (flag == 0) {
            synchronized (object2){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object1){
                    System.out.println("0");
                }
            }
        }
    }
}
