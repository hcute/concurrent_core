package threadcoreknowledge.uncaughtexception;

/**
 * 单线程抛出，处理，有异常堆栈
 *  多线程 ，子线程发生异常，会有什么不同
 */
public class ExceptionInChildThread implements Runnable{
    public static void main(String[] args) {

        Thread thread = new Thread(new ExceptionInChildThread());
        thread.start();
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
    }
    @Override
    public void run() {
        throw new RuntimeException();
    }
}
