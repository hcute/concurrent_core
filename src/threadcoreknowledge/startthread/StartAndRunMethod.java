package threadcoreknowledge.startthread;

public class StartAndRunMethod {

    public static void main(String[] args) {
        Runnable runnable = ()->{
            System.out.println(Thread.currentThread().getName());
        };

        // 和执行普通方法没有区别
        runnable.run();

        new Thread(runnable).start();
    }
}
