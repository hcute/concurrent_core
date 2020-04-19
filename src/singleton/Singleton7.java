package singleton;

/**
 * 懒汉 静态内部类的模式【可用】
 */
public class Singleton7 {

    private Singleton7() {

    }

    // 外部类加载内部类没有加载
    private static class SingletonInstance{
        private static final Singleton7 INSTANCE = new Singleton7();
    }

    public static Singleton7 getInstance(){
        return SingletonInstance.INSTANCE;
    }
}
