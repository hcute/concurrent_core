package threadcoreknowledge.concurrcyinpractice;

import java.util.HashMap;
import java.util.Map;

/**
 * 发布逸出
 */
public class MultiThreadError6 {

    private Map<String,String> states;

    public MultiThreadError6() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                states = new HashMap<>();
                states.put("1","周一");
                states.put("2","周二");
                states.put("3","周三");
                states.put("4","周四");
            }
        }).start();
    }

    public Map<String,String> getStates(){
        return states;
    }

    public static void main(String[] args) {
        MultiThreadError6 multiThreadError3 = new MultiThreadError6();
        Map<String, String> states = multiThreadError3.getStates();
        System.out.println(states.get("1"));
        states.remove("1");
        System.out.println(states.get("1"));

    }
}
