#  x线程的八大核心基础知识
##  创建线程的方法

- 创建方式
  - 实现Runnable接口
  - 继承Thread类

- 实现Runnable接口好在哪里？ 继承Thread类是不推荐的，因为它有以下的一些缺点：  

  - 从代码架构角度：具体的任务（run方法）应该和“创建和运行线程的机制（Thread类）”解耦，用runnable对象可以实现解耦。
  -  使用继承Thread的方式的话，那么每次想新建一个任务，只能新建一个独立的线程，而这样做的损耗会比较大（比如重头开始创建一个线程、执行完毕以后再销毁等。如果线程的实际工作内容，也就是run()函数里只是简单的打印一行文字的话，那么可能线程的实际工作内容还不如损耗来的大）。如果使用Runnable和线程池，就可以大大减小这样的损耗。
  -  继承Thread类以后，由于Java语言不支持双继承，这样就无法再继承其他的类，限制了可扩展性。 通常我们优先选择方法1

- 两种方法的本质对比 

  - 方法一和方法二，也就是“实现Runnable接口并传入Thread类”和“继承Thread类然后重写run()”在实现多线程的本质上，并没有区别，都是最终调用了start()方法来新建线程。

  - 这两个方法的最主要区别在于run()方法的内容来源： 

    ```java
     @Override public void run() {     
         if (target != null) {         
             target.run();     
         } 
     } 
    ```

    

  - 方法一：最终调用target.run(); 

  - 方法二：run()整个都被重写

- 两种方式同时使用会出现什么结果

  ```java
  package threadcoreknowledge.createthreads;
  
  /**
   * 同时使用两种方式
   */
  public class BothRunnableThread {
  
      public static void main(String[] args) {
          new Thread(new Runnable() {
              @Override
              public void run() {
                  System.out.println("我来自Runnable");
              }
          }){
              @Override
              public void run() {
                  System.out.println("我来自Thread");
              }
          }.start();
      }
  }
  
  /**
  	会打印 ==》 我来自Thread
  */
  ```

- 有多少种实现线程的方法？总结回答

   答题思路，以下5点：  

  - 从不同的角度看，会有不同的答案。 

  - 典型答案是两种，分别是实现Runnable接口和继承Thread类，然后具体展开说； 

  - 但是，我们看原理，其实Thread类实现了Runnable接口，并且看Thread类的run方法，会发现其实那两种本质都是一样的，run方法的代码如下：

  ```java
   @Override public void run() {     
       if (target != null) {         
           target.run();     
       } 
   } 
  ```

  

  - 方法一和方法二，

    也就是“继承Thread类然后重写run()”和“实现Runnable接口并传入Thread类”在实现多线程的本质上，并没有区别，都是最终调用了start()方法来新建线程。这两个方法的最主要区别在于run()方法的内容来源：

    ​	 方法一：最终调用target.run(); 

    ​	 方法二：run()整个都被重写 

  - 然后具体展开说其他方式； 还有其他的实现线程的方法，例如线程池等，它们也能新建线程，但是细看源码，从没有逃出过本质，也就是实现Runnable接口和继承Thread类。 
  -  结论:我们只能通过新建Thread类这一种方式来创建线程，但是类里面的run方法有两种方式来实现，
    - 第一种是重写run方法，
    - 第二种实现Runnable接口的run方法，然后再把该runnable实例传给Thread类。除此之外，从表面上看线程池、定时器等工具类也可以创建线程，但是它们的本质都逃不出刚才所说的范围。  以上这种描述比直接回答一种、两种、多种都更准确。

## 启动线程的正确方式

- start()和run()

- start源码

  ```java
   public synchronized void start() {
          /**
           * This method is not invoked for the main method thread or "system"
           * group threads created/set up by the VM. Any new functionality added
           * to this method in the future may have to also be added to the VM.
           *
           * A zero status value corresponds to state "NEW".
           */
       	// 进行状态的判断 NEW 状态才可以启动
          if (threadStatus != 0)
              throw new IllegalThreadStateException();
  
          /* Notify the group that this thread is about to be started
           * so that it can be added to the group's list of threads
           * and the group's unstarted count can be decremented. */
          // 加入线程组
          group.add(this);
  
          boolean started = false;
          try {
              // 执行start0方法
              start0();
              started = true;
          } finally {
              try {
                  if (!started) {
                      group.threadStartFailed(this);
                  }
              } catch (Throwable ignore) {
                  /* do nothing. If start0 threw a Throwable then
                    it will be passed up the call stack */
              }
          }
      }
  ```

- 两次执行start 会抛出异常，因为start方法会判断线程的状态是否为NEW状态

- start 最终会调用run方法，为什么还要调用start方法，因为run方法是一个普通方法，只有start方法才会进行线程的准备工作，启动子线程调用run方法执行需要的任务

## 停止线程的方式

**使用interrupt来通知，而不是强制停止**

### 停止的方式

- 通常情况下如何停止

  ```java
  package threadcoreknowledge.stopthreads;
  
  /**
   * run 方法内没有sleep和wait方法下停止线程
   */
  public class RightWayStopThreadWithoutSleep implements Runnable{
  
  
      @Override
      public void run() {
          int num = 0;
          while (!Thread.currentThread().isInterrupted() && num < Integer.MAX_VALUE/2) {
              if (num % 10000 == 0) {
                  System.out.println(num + "是10000的倍数");
              }
              num++;
          }
          System.out.println("线程执行结束");
      }
  
      public static void main(String[] args) throws InterruptedException {
          Thread thread = new Thread(new RightWayStopThreadWithoutSleep());
          thread.start();
          Thread.sleep(2000);
          thread.interrupt();
      }
  }
  ```

  

- 线程被阻塞如何停止

  ```java
  package threadcoreknowledge.stopthreads;
  
  /**
   *  带有sleep的中断线程的方法
   */
  public class RightWayStopThreadWithSleep{
  
  
      public static void main(String[] args) throws InterruptedException {
  
          Runnable runnable = () ->{
              int num = 0;
              while (num <=300 && !Thread.currentThread().isInterrupted()) {
                  if (num % 100 ==0) {
                      System.out.println(num + "是100的倍数");
                  }
                  num++;
              }
  
              try {
                  Thread.sleep(1000); // 睡眠过程中，如果收到停止信号，则会抛出异常java.lang.InterruptedException: sleep interrupted
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              System.out.println("任务执行结束了！");
          };
  
          Thread thread = new Thread(runnable);
          thread.start();
          Thread.sleep(500);
          thread.interrupt();
  
      }
  }
  
  ```

  

- 每次迭代之后都阻塞如何停止

  ```java
  package threadcoreknowledge.stopthreads;
  
  /**
   * 每次循环都sleep或wait等待的情况下停止线程
   */
  public class RightWayStopThreadWithSleepEveyLoop {
  
      public static void main(String[] args) throws InterruptedException {
          Runnable runnable = ()->{
              int num = 0;
              try {
                  while (num <=10000 /*&& !Thread.currentThread().isInterrupted()*/){ // 此处不需要每次都判断是否中断
                      if (num % 100 == 0) {
                          System.out.println(num + "是100倍数");
                      }
                      num++;
                      Thread.sleep(10);
                  }
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          };
        	// 如果try catch 在while 循环里面 intertupt 是没有生效的
          /**while (num <= 10000 && !Thread.currentThread().isInterrupted()) {
            if (num % 100 == 0) {
              System.out.println(num + "是100的倍数");
            }
  
            num++;
            // try catch 在while的内部线程不会中断
            try {
            	// sleep 会清楚interrupt的标记位，如果try catch 的话当前线程的停止标志就会被清除
              Thread.sleep(10);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }*/
  
          Thread thread = new Thread(runnable);
          thread.start();
          Thread.sleep(5000);
          thread.interrupt();
      }
  }
  
  ```

- 最佳实践

  - 优选选择：传递中断

    ```java
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
    
    ```

    

  - 不想或无法传递：**恢复中断**

    ```java
    package threadcoreknowledge.stopthreads;
    
    /**
     * catch住InterruptedException后调用Thread.currentThread().interrupt()来
     */
    public class RightWayStopThreadInProd2 implements Runnable{
    
    
        @Override
        public void run() {
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("Interrupt 线程被中断了");
                    break;
                }
                System.out.println("go");
                reInterrupt();
            }
        }
    
        /**
         * 如果一定要catch 那么在catch后面再继续中断线程
         * @throws InterruptedException
         */
        private void reInterrupt() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 继续中断异常
                e.printStackTrace();
            }
        }
    
        public static void main(String[] args) throws InterruptedException {
            Thread thread = new Thread(new RightWayStopThreadInProd2());
            thread.start();
            Thread.sleep(1000);
            thread.interrupt();
        }
    }
    
    ```

    

  - 不应屏蔽中断

    - 如果屏蔽中断会产生信息不畅通

- 响应中断的方式

  - Object类的 wait()、wait(long) 、wait(long,int)
  - Thread.sleep(long)/sleep(long,int)
  - Thread.join()/join(long)/join(long,int)
  - java.util.concurrent.BlockingQueue.take()/put(E)
  - java.util.concurrent.locks.Lock.lockInterruptibly()
  - java.util.concurrent.CountDownLatch.await()
  - java.util.concurrent.CyclicBarrier.await()
  - java.util.concurrent.Exchanger.exchange(V)
  - java.nio.channels.InterruptibleChannel相关方法
  - java.nio.channels.Selector的相关方法







## 线程的状态

## 线程各种属性

## 线程中的异常处理

## 线程会产生的问题



