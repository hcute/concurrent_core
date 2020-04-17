package singleton;

/**
 * 懒汉式（线程安全）【不推荐】
 */
public class Singleton4 {

    private static Singleton4 instance;

    private Singleton4() {

    }

    // 使用到的时候才创建，加锁保证了线程安全，但是效率低
    public synchronized static Singleton4 getInstance(){
        if (instance == null) {
            instance = new Singleton4();
        }
        return instance;
    }
}
