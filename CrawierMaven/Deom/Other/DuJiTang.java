package Deom.Other;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;


public class DuJiTang {
    //每一次请求的时间间隔 随机函数生成
    public static int Ran() {
        ////挂起延迟 增加访问间隔时间
        //第一次随机数作为第二次Random函数的种子数 构造器random
        Random random = new Random();
        int time = random.nextInt(10);
        //如果随机时间间隔小于5则循环重新随机生成
        do {
            time = random.nextInt(8);
        }while(time < 3);
        return time;
    }


    public static String word(){

        String ID = "";

        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse httpResponse = null;

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000).setConnectionRequestTimeout(20000)
                .setConnectTimeout(30000).build();


        HttpGet request = new HttpGet("https://8zt.cc/");
        request.setConfig(requestConfig);

        request.setHeader("User-Agent", "Mozilla/5.0 (UrlWallHaven NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");

        try {
            httpResponse = httpClient.execute(request);


            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();

                String html = EntityUtils.toString(httpEntity);

                Document document = Jsoup.parse(html);

                Element id = document.getElementById("sentence");

                ID = id.text();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ID;
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> words = new ArrayList<String>();

        for (int i = 0; i < 30; i++) {
            int time = Ran();
            words.add("\""+word()+"\",");
            //sleep(time*1000);
        }

        for(String word : words) {
            System.out.println(word);
        }



    }
}
