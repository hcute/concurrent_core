package threadcoreknowledge.stopthreads;

/**
 * 用stop 停止线程，会导致线程运行一半突然停止，没办法完成一个基本单位的操作
 * 比如：军队发送弹药，基本单位为连队，会导致多领或少领装备
 */
public class StopsThreadError implements Runnable{

    @Override
    public void run() {
        // 模拟发放弹药，一共有5个连队，每个连队10个士兵，叫到号的士兵前来领取弹药

        for (int i = 0; i < 5; i++) {
            System.out.println("连队："+ i+"开始领取弹药");
            for (int j = 0; j < 10; j++) {
                System.out.println(j);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("连队："+ i+"士兵领完弹药完毕");

        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new StopsThreadError());
        thread.start();
        try {
            Thread.sleep(1000); // 模拟接到紧急任务，需要停止领取装备
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.stop(); // 错误说法：不会释放monitor锁 ，官方指出会释放monitor
    }
}
