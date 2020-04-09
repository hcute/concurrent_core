package threadcoreknowledge.startthread;

/**
 * 不能两次启动线程
 */
public class CantStartTwice {

    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
        thread.start();
    }
}
