package threadcoreknowledge.stopthreads;

public class RightWayInterrupted {

    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {

                }
            }
        });

        threadOne.start();
        threadOne.interrupt();// 发送中断信号

        System.out.println("IsInterrupted:" + threadOne.isInterrupted());
        System.out.println("IsInterrupted:" + threadOne.interrupted());
        System.out.println("IsInterrupted:" + Thread.interrupted());
        System.out.println("IsInterrupted:" + threadOne.isInterrupted());
        threadOne.join();
        System.out.println("main thread is over ");

    }
}
