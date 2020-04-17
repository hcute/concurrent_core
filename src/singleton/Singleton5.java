package singleton;

/**
 * 懒汉式（线程安全）【不推荐】
 */
public class Singleton5 {

    private static Singleton5 instance;

    private Singleton5() {

    }

    // 使用到的时候才创建，但是没有解决线程安全问题
    public static Singleton5 getInstance(){
        if (instance == null) { // 同时进入到这里的线程，还是会创建多个实例
            synchronized (Singleton5.class) {
                instance = new Singleton5();
            }
        }
        return instance;
    }
}
