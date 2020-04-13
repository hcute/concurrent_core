package threadcoreknowledge.stopthreads;

/**
 * catch住InterruptedException优选选择抛出，那么在run方法就会强制try catch
 */
public class RightWayStopThreadInProd implements Runnable{


    @Override
    public void run() {
        try {
            while (true){
                System.out.println("go");
                throwInMethod();
            }
        } catch (InterruptedException e) {
            System.out.println("保存日志");
            e.printStackTrace();
        }
    }

    /**
     * 抛出异常 不要tryCatch 如果tryCatch就达不到停止的效果
     * @throws InterruptedException
     */
    private void throwInMethod() throws InterruptedException {
        Thread.sleep(2000);
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProd());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
