package deadlock;

import sun.security.krb5.internal.TGSRep;

/**
 * 转账是发生死锁，一旦打开注释，就会发生死锁
 */
public class TransferMoney implements Runnable{

    int flag = 1;
    static Account a = new Account(500);
    static Account b = new Account(500);


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

        synchronized (from) {
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
        }
    }

    static class Account{
        int balance;

        public Account(int balance) {
            this.balance = balance;
        }
    }
}
