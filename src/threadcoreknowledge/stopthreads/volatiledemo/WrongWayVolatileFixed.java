package threadcoreknowledge.stopthreads.volatiledemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class WrongWayVolatileFixed {

    public static void main(String[] args) {
        WrongWayVolatileFixed wrongWayVolatileFixed = new WrongWayVolatileFixed();
        ArrayBlockingQueue storage= new ArrayBlockingQueue(10);
        Producer producer = wrongWayVolatileFixed.new Producer(storage);
        Thread thread = new Thread(producer);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threadcoreknowledge.stopthreads.volatiledemo.Consumer consumer = new threadcoreknowledge.stopthreads.volatiledemo.Consumer(storage);

        while (consumer.needMoreNums()){
            try {
                System.out.println(consumer.storage.take() + "被消费了");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("消费者不需要更多数据了");
        thread.interrupt();
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

                while (num <= 100000 && !Thread.currentThread().isInterrupted()){
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
}


