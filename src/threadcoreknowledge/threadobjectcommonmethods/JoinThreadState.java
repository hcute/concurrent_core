package threadcoreknowledge.threadobjectcommonmethods;



/**
 * 先join 再main线程的状态
 */
public class JoinThreadState {

    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    System.out.println(mainThread.getState());
                    System.out.println(Thread.currentThread().getName() + "子线程执行完毕了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        System.out.println("等待子线程执行完毕");
        thread.join();
        System.out.println("子线程运行完毕");


    }
}
