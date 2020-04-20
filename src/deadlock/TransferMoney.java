package deadlock;

import sun.security.krb5.internal.TGSRep;

/**
 * 转账是发生死锁，一旦打开注释，就会发生死锁
 */
public class TransferMoney implements Runnable{

    int flag = 1;
    static Account a = new Account(500);
    static Account b = new Account(500);
    static Object lock = new Object();


    public static void main(String[] args) throws InterruptedException {
        TransferMoney r1 = new TransferMoney();;
        r1.flag = 1;
        TransferMoney r2 = new TransferMoney();
        r2.flag = 0;
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("a的余额" + a.balance + ",b的余额" + b.balance);

    }


    @Override
    public void run() {
        if (flag == 1) {
            // 从A转给B
            transferMoney(a,b,200);
        }
        if (flag == 0) {
            transferMoney(b,a,200);
        }

    }

    public static void transferMoney(Account from, Account to, int money) {
        // 锁顺序一致修复代码 ，如果hash 值一样需要加时赛
        class Helper {
            private void transfer(){
                if (from.balance < 0) {
                    System.out.println("余额不足，转账失败");
                }
                from.balance -= money;
                to.balance += money;
                System.out.println("成功转账了" + money + "元");
            }
        }
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);
        if (fromHash < toHash) {
            synchronized (from){
                synchronized (to) {
                    new Helper().transfer();
                }
            }
        }
        else if (fromHash > toHash) {
            synchronized (to){
                synchronized (from) {
                    new Helper().transfer();
                }
            }
        }
        // hash 冲突的时候 ，如果数据库有主键 ，按照主键的高低来决定获取锁的顺序
        else {
            synchronized (lock) {
                synchronized (from){
                    synchronized (to) {
                        new Helper().transfer();
                    }
                }
            }
        }
        // hashcode一样

        // 原始代码
        /*synchronized (from) {
            // 获取锁需要等待500
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            synchronized (to) {
                if (from.balance < 0) {
                    System.out.println("余额不足，转账失败");
                }
                from.balance -= money;
                to.balance += money;
                System.out.println("成功转账了" + money + "元");
            }
        }*/
    }

    static class Account{
        int balance;

        public Account(int balance) {
            this.balance = balance;
        }
    }
}
