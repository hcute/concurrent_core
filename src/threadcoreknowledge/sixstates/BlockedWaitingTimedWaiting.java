package threadcoreknowledge.sixstates;

/**
 * 展示 Blocked Waiting Timed_Waiting
 */
public class BlockedWaitingTimedWaiting implements Runnable{


    public static void main(String[] args) {
        BlockedWaitingTimedWaiting r = new BlockedWaitingTimedWaiting();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        // 打印TIMED_WAITING 因为在sleep中
        System.out.println(t1.getState());
        // 打印BLOCKED 因为没有拿到monitor锁
        System.out.println(t2.getState());

        try {
            Thread.sleep(2300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t1.getState());


    }

    @Override
    public void run() {
        try {
            syn();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void syn() throws InterruptedException {
        Thread.sleep(2000);
        wait();
    }
}
