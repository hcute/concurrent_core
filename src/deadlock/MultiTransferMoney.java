package deadlock;

import java.util.Random;

/**
 * 模拟多人随机转账
 */
public class MultiTransferMoney {

    private static final int NUM_ACCOUNTS = 5000;
    private static final int NUM_MONEY = 500;
    private static final int NUM_ITERATIONS = 100000;
    private static final int NUM_THREADS = 500;


    public static void main(String[] args) {

        Random random = new Random();
        TransferMoney.Account[] accounts = new TransferMoney.Account[NUM_ACCOUNTS];
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            accounts[i] = new TransferMoney.Account(NUM_MONEY);
        }

        class TransferThread extends Thread{


            @Override
            public void run() {
                for (int i = 0; i < NUM_ITERATIONS; i++) {
                    int fromAcc = random.nextInt(NUM_ACCOUNTS);
                    int toAcc = random.nextInt(NUM_ACCOUNTS);
                    int amount = random.nextInt(NUM_MONEY);
                    TransferMoney.transferMoney(accounts[fromAcc],accounts[toAcc],amount);
                }
                System.out.println("转账结束");
            }
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            new TransferThread().start();
        }

    }
}
