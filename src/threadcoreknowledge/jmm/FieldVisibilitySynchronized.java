package threadcoreknowledge.jmm;

/**
 * 演示可见性带来的问题
 *  可能出现
 *      a=3,b=2
 *      a=3,b=3
 *      a=1,b=2
 *      b=3,a=1 出现了可见性问题
 *
 */
public class FieldVisibilitySynchronized {

    int a = 1;
    int b = 2;
    int c = 3;
    int d = 4;

    public static void main(String[] args) {
        while (true) {
            FieldVisibilitySynchronized test = new FieldVisibilitySynchronized();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.change();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.print();
                }
            }).start();
        }

    }

    private void print() {
        synchronized (this) {
            int aa = a;
        }
        int bb = b;
        int cc = c;
        int dd = d;
        System.out.println("bb = " + bb + ",cc = " + cc);
    }

    private void change() {
        a = 3;
        b = 4;
        c = 4;
        d = 5;
        synchronized (this) {
            d = 6;
        }
    }
}
