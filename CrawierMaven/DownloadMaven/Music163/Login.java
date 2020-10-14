package DownloadMaven.Music163;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;


public class Login {
    public static void main(String[] args) {
        GetCookie();
    }

    public static String GetCookie() {
        //打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //定义转换过程中使用到的变量
        CloseableHttpResponse response = null;
        HttpEntity httpEntity = null;
        String Url = "http://localhost:3000/login/cellphone?phone=17508470656&password=123music163.";
        String cookies = null;

        //创建get请求,输入网址
        HttpGet httpGet = new HttpGet(Url);

        //定义请求中的设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000).setConnectionRequestTimeout(2000)
                .setSocketTimeout(3000).build();
        //设置请求的头部信息
        httpGet.setHeader("User-Agent","Mozilla/5.0 (UrlWallHaven NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        //将设置生效
        httpGet.setConfig(requestConfig);

        //输入网址后执行请求
        try {
            response = httpClient.execute(httpGet);
            //判断响应码
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity);

                //通过将网页信息转换成JSONObjecet对象
                JSONObject data1 = new JSONObject(html);
                //System.out.println(data1);

                //获取cookie
                cookies = data1.getString("cookie");

                //此处是采用解析文档对象模型中对象的数据
                /*System.out.println(cookies);
                //文档对象模型
                Document document = Jsoup.parse(html);
                String a = document.text();
                String[] datas = a.split("cookie");
                String[] data2s = datas[1].split("\"");
                String cookie = data2s[2];
                System.out.println(cookie);*/

            }
            //得到响应后进行解析
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cookies;


    }
}
