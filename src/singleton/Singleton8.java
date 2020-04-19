package singleton;

/**
 * 枚举 单例最好的方式
 *  优点：
 *      1，写法简单
 *      2，线程安全有保证，会被编译成final类，而且里面的对象都是static修饰
 *      3，避免反序列化破坏单例
 *
 */
public enum Singleton8 {

    INSTANCE;

    public void whatever(){

    }

}
