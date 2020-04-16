package threadcoreknowledge.concurrcyinpractice;

/**
 * 观察者模式
 */
public class RepairMultiThreadError5 {
    int count = 0;

    private EventListener listener;

    public static void main(String[] args) throws InterruptedException {
        MySource mySource = new MySource();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mySource.eventCome(new Event() {

                });
            }
        }).start();

        RepairMultiThreadError5 multiThreadError5 = getInstance(mySource);
    }

    private RepairMultiThreadError5(MySource mySource) {
        // 内部类持有外部类的this对象
        listener = new EventListener() {
            @Override
            public void onEvent(Event e) {
                System.out.println("\n我得到的数字是" + count);
            }
        };
        for (int i = 0; i < 10000; i++) {
            System.out.print(i);
        }
        count = 100;
    }

    public static  RepairMultiThreadError5 getInstance(MySource mySource){
        RepairMultiThreadError5 safeInstance = new RepairMultiThreadError5(mySource);
        mySource.registerListener(safeInstance.listener);
        return safeInstance;
    }

    static class MySource {
        private EventListener listener;
        void registerListener(EventListener eventListener){
            this.listener = eventListener;
        }

        void eventCome(Event e) {
            if (listener != null){
                listener.onEvent(e);
            }else{
                System.out.println("还未完成初始化");
            }
        }
    }

    interface EventListener{
        void onEvent(Event e);
    }
    interface Event{

    }
}
