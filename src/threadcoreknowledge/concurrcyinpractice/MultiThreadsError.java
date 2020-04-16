package threadcoreknowledge.concurrcyinpractice;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * a++运行结果出错，并找出出错的位置
 */
public class MultiThreadsError implements Runnable{
    int index = 0;
    static AtomicInteger realIndex = new AtomicInteger();
    static AtomicInteger wrongCount = new AtomicInteger();
    final boolean[] marked = new boolean[10000000];
    static volatile MultiThreadsError multiThreadsError = new MultiThreadsError();
    static volatile CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);
    static volatile CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(multiThreadsError);
        Thread t2 = new Thread(multiThreadsError);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("表面上的错误" + multiThreadsError.index);
        System.out.println("真正运行的次数" + realIndex);
        System.out.println("发生错误的次数" + wrongCount);


    }

    @Override
    public void run() {
        marked[0] = true;
        for (int i = 0; i < 10000; i++) {
            try {
                cyclicBarrier2.reset();
                cyclicBarrier1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            index++;
            // 1 - 1 false
            // 2 - 2 true 这种没有问题
            try {
                cyclicBarrier1.reset();
                cyclicBarrier2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            realIndex.incrementAndGet();
            synchronized (multiThreadsError){
                if (marked[index] && marked[index-1]) {
                    System.out.println("发生错误：" + index);
                    wrongCount.incrementAndGet();
                }
                marked[index] =true;
            }

        }
    }
}

