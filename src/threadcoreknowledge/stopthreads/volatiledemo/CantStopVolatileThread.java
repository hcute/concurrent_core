package threadcoreknowledge.stopthreads.volatiledemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * volatile 修饰的boolean标记位无法停止线程，阻塞的时候，
 * 生产者生成速度很快但是消费者消费者很慢，就会出现阻塞队列满的时候，生产者就会等待
 */
public class CantStopVolatileThread {

    public static void main(String[] args) {
        ArrayBlockingQueue storage= new ArrayBlockingQueue(10);

        Producer producer = new Producer(storage);
        Thread producerThread = new Thread(producer);
        producerThread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Consumer consumer = new Consumer(storage);

        while (consumer.needMoreNums()){
            try {
                System.out.println(consumer.storage.take() + "被消费了");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("消费者不需要更多数据了");
        producer.canceled =true; // 让生产者停止生产
        System.out.println(producer.canceled);

    }

}

class Producer implements Runnable{
    public volatile boolean canceled =false;
    BlockingQueue storage;

    public Producer(BlockingQueue storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        int num = 0;
        try {

            while (num <= 100000 && !canceled){
                if (num % 100 == 0){
                    storage.put(num);
                    System.out.println(num +"是100的倍数，被放到仓库中");
                }
                num++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("生产者停止运行");
        }
    }
}

class Consumer {
    private volatile boolean canceled =false;
    BlockingQueue storage;

    public Consumer(BlockingQueue storage) {
        this.storage = storage;
    }

    public boolean needMoreNums() {
        double random = Math.random();
        if (random > 0.95) {
            return false;
        }
        return true;
    }
}
