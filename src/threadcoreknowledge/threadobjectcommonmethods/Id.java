package threadcoreknowledge.threadobjectcommonmethods;

/**
 * 线程id从0开始是自增的，自己创建的线程的id早已不是0
 */
public class Id {

    public static void main(String[] args) {
        Thread t1 = new Thread();
        System.out.println("主线程的id：" + Thread.currentThread().getId());
        System.out.println("子线程的id：" + t1.getId());
    }
}
