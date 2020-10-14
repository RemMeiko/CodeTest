package Deom.Job;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class lagou {

    private static HttpEntity request(String url) {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        Document document = null;
        HttpEntity  httpEntity = null;

        //1.设置超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(20000)
                .setSocketTimeout(30000).build();
        //2.创建get请求，相当于在浏览器地址栏输入 网址
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        //3、设置请求头配置
        request.setHeader("User-Agent", "Mozilla/5.0 (UrlWallHaven NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        //request.setHeader("Referer",url);
        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键

            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                httpEntity = response.getEntity();
                //请求的html文档
                //String html = EntityUtils.toString(httpEntity, "utf-8");

                //进行jsoup解析
                //document = Jsoup.parse(html);
            } else {
                //如果返回状态不是200，比如404（页面不存在）等
                httpEntity = response.getEntity();
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  catch (ParseException e) {
            e.printStackTrace();
        } finally {
        }
        return httpEntity;
    }

    //每一次请求的时间间隔 随机函数生成
    public static int Ran() {
        ////挂起延迟 增加访问间隔时间
        //第一次随机数作为第二次Randomh函数的种子数 构造器random
        Random random = new Random();
        int time = random.nextInt(10);
        //如果随机时间间隔小于5则循环重新随机生成
        do {
            time = random.nextInt(10);
        }while(time < 5);
        return time;
    }

    public static void main(String[] args) {
        try {
/*            Scanner in = new Scanner(System.in);
            System.out.println("请输入关键字:");*/
            String keywrd = "java";
            String url = "https://www.lagou.com/jobs/positionAjax.json?needAddtionalResult=false";

            CookieStore httpcookie = new BasicCookieStore();
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(httpcookie).build();
            CloseableHttpResponse response = null;

            //1.设置超时配置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(30000).setConnectionRequestTimeout(20000)
                    .setSocketTimeout(30000).build();

    /*        HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            response = httpClient.execute(httpGet);*/




            //2.创建post请求，相当于在浏览器地址栏输入 网址
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            //HttpEntity httpEntity = request("https://www.lagou.com/jobs/list_java?labelWords=&fromSearch=true&suginput=");

            httpPost.addHeader("User-Agent", "Mozilla/5.0 (UrlWallHaven NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");

            ArrayList<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
            parameters.add(new BasicNameValuePair("first","true"));
            parameters.add(new BasicNameValuePair("pn","1"));
            parameters.add(new BasicNameValuePair("kd","java"));
            //parameters.add(new BasicNameValuePair("cookies","JSESSIONID=ABAAABAAAECABEHE30245E1DF2D43B51F19DDCBC183D5E5; user_trace_token=20200717210132-fd7e41f3-f3ed-4c4f-b3cf-6952ea418c3f; sensorsdata2015session=%7B%7D; WEBTJ-ID=20200718124502-173603d61f8116-0f5928a0eec634-d373666-2073600-173603d61f9281; RECOMMEND_TIP=true; LGUID=20200718124501-a3949358-48ed-4b89-bf22-a77c0ace666a; _ga=GA1.2.687235627.1595047503; _gid=GA1.2.1484028695.1595047503; index_location_city=%E5%85%A8%E5%9B%BD; TG-TRACK-CODE=search_code; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1595047503,1595048736,1595049928; X_MIDDLE_TOKEN=d2d2a8129d07732307487bfa25748894; LGSID=20200718141136-9690580e-ea46-402a-a7d4-1b215ef42069; PRE_UTM=; PRE_HOST=; PRE_SITE=https%3A%2F%2Fwww.lagou.com; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2Fjobs%2Flist%5F%3Fcity%3D%25E4%25B8%258A%25E6%25B5%25B7%26cl%3Dfalse%26fromSearch%3Dtrue%26labelWords%3D%26suginput%3D%25E4%25BD%259C%25E8%2580%2585%25EF%25BC%259A%25E5%2590%2589%25E7%25A5%25A5%25E9%25B8%259Fhu%25E9%2593%25BE%25E6%258E%25A5%25EF%25BC%259Ahttps%3A%2F%2Fwww.jianshu.com%2Fp%2Fb4238a0c68ff; _gat=1; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%221735cdd9ca97ef-0443ee9c960ad7-d373666-2073600-1735cdd9caa969%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24os%22%3A%22Windows%22%2C%22%24browser%22%3A%22Chrome%22%2C%22%24browser_version%22%3A%2281.0.4044.138%22%2C%22%24latest_referrer_host%22%3A%22%22%7D%2C%22%24device_id%22%3A%221735cdd9ca97ef-0443ee9c960ad7-d373666-2073600-1735cdd9caa969%22%7D; X_HTTP_TOKEN=18caba0191243a6d1482505951e0c733e1ca70c8d2; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1595052862; LGRID=20200718141421-43dc5822-f1ad-406c-9bb4-038a4d40f55b; SEARCH_ID=f275d001d94b4b2096b8697ec3cefbaa"));
            httpPost.setEntity(new UrlEncodedFormEntity(parameters));



            response = httpClient.execute(httpPost);

            httpcookie.getCookies();

            String html = EntityUtils.toString(response.getEntity(),"utf-8");

            System.out.println(httpcookie.getCookies());


       /*     //提取信息
            Elements calss = document.select(".s_position_list .item_con_list");
            System.out.println(calss);*/

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
