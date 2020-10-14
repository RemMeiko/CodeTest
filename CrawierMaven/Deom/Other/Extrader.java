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
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Extrader {
    public static void main(String[] args) {
        //1.生成httpclient,打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        //请求配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000).setConnectionRequestTimeout(2000)
                .setSocketTimeout(9000).build();
        HttpGet httpGet = new HttpGet("https://www.extrader.top/");
        httpGet.setConfig(requestConfig);
        //请求头设置
        httpGet.setHeader("User-Agent","Mozilla/5.0 (UrlWallHaven NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        try {
            response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity,"utf-8");
                Document document = Jsoup.parse(html);

                Elements layouts = document.getElementsByClass("recent-post-item article-container");

                ArrayList<String> titles = new ArrayList<String>();//文章标题
                ArrayList<String> urls = new ArrayList<String>();//url
                for(Element layout : layouts) {
                    Elements title = layout.select(".article-title");
                    Elements url = layout.select("a[href]");
                    titles.add(title.text());
                    System.out.println(url);
                }
               // System.out.println(titles);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }




    }
}
