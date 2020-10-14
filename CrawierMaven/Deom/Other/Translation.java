package Deom.Other;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Scanner;

public class Translation {
    public static void main(String[] args) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = null;

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(3000).setConnectionRequestTimeout(3000)
                    .setSocketTimeout(3000).build();

            System.out.print("请输入翻译目标:");
            Scanner in = new Scanner(System.in);
            String str = in.nextLine();

            HttpPost httpPost = new HttpPost("https://fanyi.baidu.com/v2transapi?from=zh&to=en");
            //设置header信息
//指定报文头【Content-type】、【User-Agent】
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; UrlWallHaven NT; DigExt)");
            //httpPost.setEntity();

            data datas = new data();
            datas.setFrom("zh");
            datas.setTo("en");
            datas.setQuery("中国");
            datas.setTranstype("realtime");
            datas.setSimple_means_flag("3");
            datas.setToken("a3a61f5a7fd942c16f667d7eb9cb8987");
            datas.setDomain("common");

            StringEntity entity = new StringEntity(JSON.toJSONString(datas));

            httpPost.setEntity(entity);

            response = httpClient.execute(httpPost);
            System.out.println(response);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class data {
    private String from;
    private String to;
    private String query;
    private String transtype;
    private String simple_means_flag;
    private String token;
    private String domain;

    public String getTo() {
        return to;
    }

    public String getQuery() {
        return query;
    }

    public String getTranstype() {
        return transtype;
    }

    public String getSimple_means_flag() {
        return simple_means_flag;
    }

    public String getToken() {
        return token;
    }

    public String getDomain() {
        return domain;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    public void setSimple_means_flag(String simple_means_flag) {
        this.simple_means_flag = simple_means_flag;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
