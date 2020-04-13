package threadcoreknowledge.stopthreads;

/**
 * catch住InterruptedException后调用Thread.currentThread().interrupt()来
 */
public class RightWayStopThreadInProd2 implements Runnable{


    @Override
    public void run() {
        while (true){
            if (Thread.currentThread().isInterrupted()){
                System.out.println("Interrupt 线程被中断了");
                break;
            }
            System.out.println("go");
            reInterrupt();
        }
    }

    /**
     * 如果一定要catch 那么在catch后面再继续中断线程
     * @throws InterruptedException
     */
    private void reInterrupt() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 继续中断异常
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProd2());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
