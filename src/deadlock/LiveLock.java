package deadlock;

import java.util.Random;

/**
 * 演示活锁问题，牛郎织女没饭吃
 */
public class LiveLock {


    /**
     * 勺子只有一把
     */
    static class Spoon{
        private Diner owner;

        public Spoon(Diner owner) {
            this.owner = owner;
        }

        public Diner getOwner() {
            return owner;
        }

        public void setOwner(Diner owner) {
            this.owner = owner;
        }

        public synchronized void use(){
            System.out.printf("%s has eaten!",owner.name);
        }
    }

    static class Diner{
        private String name;
        private boolean isHungry;

        public Diner(String name) {
            this.name = name;
            this.isHungry = true;
        }

        /**
         *
         * @param spoon
         * @param spouse 陪我吃饭的人
         */
        public void eatWith(Spoon spoon,Diner spouse){
            while (isHungry) {
                if (spoon.owner != this) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                Random random = new Random();
                // 拿到勺子但是对方饿给对方，但是我不是一直谦让，我很饿了，我就先吃了再去保护你
                if (spouse.isHungry && random.nextInt(10) < 9) {
                    System.out.printf("%s：亲爱的%s你先吃吧\n",name,spouse.name);
                    spoon.setOwner(spouse);
                    continue;
                }
                spoon.use();
                isHungry = false;
                System.out.printf("%s：我已经吃完了\n" ,name);
                spoon.setOwner(spouse);
            }
        }
    }

    public static void main(String[] args) {
        Diner husband = new Diner("牛郎");
        Diner wife = new Diner("织女");
        Spoon spoon = new Spoon(husband);
        new Thread(new Runnable() {
            @Override
            public void run() {
                husband.eatWith(spoon,wife);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                wife.eatWith(spoon,husband);
            }
        }).start();
    }
}
