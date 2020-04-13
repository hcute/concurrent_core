package threadcoreknowledge.stopthreads;

/**
 * 每次循环都sleep或wait等待的情况下停止线程
 */
public class RightWayStopThreadWithSleepEveyLoop {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = ()->{
            int num = 0;
            try {
                while (num <=10000 /*&& !Thread.currentThread().isInterrupted()*/){ // 此处不需要每次都判断是否中断
                    if (num % 100 == 0) {
                        System.out.println(num + "是100倍数");
                    }
                    num++;
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
