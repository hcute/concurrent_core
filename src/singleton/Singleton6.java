package singleton;

/**
 * 双重检查【推荐面试使用】
 */
public class Singleton6 {

    private volatile static Singleton6 instance;

    private Singleton6() {

    }

    // 使用到的时候才创建，但是没有解决线程安全问题
    public static Singleton6 getInstance(){
        if (instance == null) { // 同时进入到这里的线程，还是会创建多个实例
            synchronized (Singleton6.class) {
                if (instance == null) { // 双重检查
                    instance = new Singleton6();
                }
            }
        }
        return instance;
    }
}
