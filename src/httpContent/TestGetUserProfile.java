package httpContent;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestGetUserProfile {
    public static void main(String[] args) throws Exception {

        String s = doGet("http://jxtest.vaiwan.com/user/profile");
        System.out.println(s);
    }

    public static String doGet(String url) throws Exception {
        StringBuffer sbf = new StringBuffer();
        HttpURLConnection conn = null;
        BufferedReader br = null;
        String content = null;
        try{
            URL u = new URL(url);
            conn = (HttpURLConnection)u.openConnection();
            conn.setReadTimeout(50000);
            conn.setConnectTimeout(60000);
            //设置请求头
            conn.setRequestMethod("GET");
            conn.setRequestProperty("app-code","JX_DRC");
            String timestamp = System.currentTimeMillis()/1000+"";
            conn.setRequestProperty("timestamp", timestamp);
            String signValue = "timestamp=" + timestamp +"&"+
                    "secretKey=VL8UV2rmg7H1MvsPW0YiuIgcN4PFmCrx";
            conn.setRequestProperty("signature",MD5Utils.getPwd(signValue));
            conn.setRequestProperty("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6IkJlYXJlciBleUpoYkdjaU9pSklVelV4TWlKOS5leUp5WVc1a2IyMUxaWGtpT2lKaU5YVjJZM0poYUNJc0luTjFZaUk2SWpJaUxDSnBZWFFpT2pFMU9EWXpPVGcxTVRCOS5aZkRtUFhnN2tWdWV0bEpXaDhIeERnNHpLcnRLa1QxUVlvRnpRak5YSk1nTXMyWm96OElFWlN0U21wdmloUm54X1hKUlVwZFM5N2Y3Q0FzNUtUZTdDdyIsImlhdCI6MTU4NjM5ODU1MCwibmJmIjoxNTg2Mzk4NTUwLCJleHAiOjE1ODY0MDU3NTAsImF1ZCI6IkpYX0RSQyIsImlzcyI6IkpYX1NNSyIsInN1YiI6IkNJVFlfV0VCX09BVVRIIiwianRpIjoiMDg2ZmMwOTAtN2EwOC0xMWVhLWFlYWUtYTkzZTU4ZmIxY2QwIn0.vYuxmRHNsbgpzbuTiQoJP3k4o0p9X56JYBOx1nC27Uw");

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if(conn.getResponseCode()==200){
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
                while((content=br.readLine())!=null){
                    sbf.append(content);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("请求数据失败");
        }finally{
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            conn.disconnect();
        }
        return sbf.toString();
    }



    public static String doPost(String url,String params){
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        String content = null;
        StringBuffer sbf = new StringBuffer();
        try{
            URL u = new URL(url);
            conn = (HttpURLConnection)u.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(50000);
            conn.setConnectTimeout(60000);
            conn.setRequestProperty("accept","*/*");
            conn.setRequestProperty("connection","Keep-Alive");
            conn.setRequestProperty("content-Type","application/json");

            writer = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
            writer.print(params);
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((content = reader.readLine())!=null){
                sbf.append(content);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(writer!=null){
                writer.close();
            }
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            conn.disconnect();
        }
        return sbf.toString();

    }
}
