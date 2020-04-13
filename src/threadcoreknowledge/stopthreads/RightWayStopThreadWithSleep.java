package threadcoreknowledge.stopthreads;

/**
 *  带有sleep的中断线程的方法
 */
public class RightWayStopThreadWithSleep{


    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () ->{
            int num = 0;
            while (num <=300 && !Thread.currentThread().isInterrupted()) {
                if (num % 100 ==0) {
                    System.out.println(num + "是100的倍数");
                }
                num++;
            }

            try {
                Thread.sleep(1000); // 睡眠过程中，如果收到停止信号，则会抛出异常java.lang.InterruptedException: sleep interrupted
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务执行结束了！");
        };

        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(500);
        thread.interrupt();

    }
}
