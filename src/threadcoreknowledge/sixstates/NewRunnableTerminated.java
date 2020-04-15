package threadcoreknowledge.sixstates;

/**
 * 展示线程的New Runnable Terminated，即时是正在运行也是Runnable状态
 */
public class NewRunnableTerminated implements Runnable{


    public static void main(String[] args) {
        NewRunnableTerminated r = new NewRunnableTerminated();
        Thread thread = new Thread(r);
        System.out.println(thread.getState()); // New状态
        thread.start();
        System.out.println(thread.getState()); // Runnable
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(thread.getState()); // Runnable

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(thread.getState());
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
    }
}
