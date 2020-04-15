package threadcoreknowledge.threadobjectcommonmethods;

/**
 * 不会释放synchronized锁，等待sleep时间到了继续执行锁中的带
 */
public class SleepDontReleaseMonitor implements Runnable{


    public static void main(String[] args) {
        SleepDontReleaseMonitor sleepDontReleaseMonitor = new SleepDontReleaseMonitor();
        Thread t1 = new Thread(sleepDontReleaseMonitor);
        Thread t2 = new Thread(sleepDontReleaseMonitor);
        t1.start();
        t2.start();
    }

    @Override
    public void run() {
        sync();
    }

    private synchronized void sync(){
        System.out.println(Thread.currentThread().getName() + "获取到了锁");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "退出了同步代码块");
    }
}
