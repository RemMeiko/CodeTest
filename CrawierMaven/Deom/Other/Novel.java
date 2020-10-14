package Deom.Other;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
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

import static java.lang.Thread.sleep;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
* 获取三国志白话文
* 网址:http://www.cngdwx.com/yiwenshangxi/sanguozhibaihuawen/
* http://www.cngdwx.com/该网站中的网址皆可
*/

public class Novel {

    //将网页请求打包成request函数 //返回一个httpEntity
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
        //request.setHeader("Referer","https://bing.ioliu.cn/");
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

    public static void main(String[] args) throws IOException, InterruptedException {
        //手动输入指定url
        Scanner in = new Scanner(System.in);
        System.out.print("请输入你想要下载的书籍的主页网址:");
        String Strurl = in.nextLine();

        //获取目录url
        System.out.println("请稍等,正在获取目录中...");
        HttpEntity Directory =request(Strurl);
        System.out.println("获取目录成功");
        String DirecStr = EntityUtils.toString(Directory,"utf-8");
        Document Diredocument = Jsoup.parse(DirecStr);

        Elements ul = Diredocument.select(".list_box ul");

        Elements urlli = ul.select("li");
        ArrayList<String> urls = new ArrayList<String>();
        ArrayList<String> names = new ArrayList<String>();
        for(Element url : urlli) {
            //获取所有章节的url
            String Top = "http://www.cngdwx.com/";
            String urlsusse  = Top+url.select("a").attr("href");
            urls.add(urlsusse);
            //获取所有章节的名称
            names.add(url.select("a").text());
        }

        int cnt = 0;
        for(String url : urls) {
            int time = Ran();

            //写进文件中
            File file = new File(".\\src\\main\\resourcs\\小说\\三国志白话文",names.get(cnt)+".doc");

            //判断文件夹是否存在 不存在则创建
            if(!file.exists()) {
                System.out.println("预计延迟"+time+"s,请稍等,正在下载...");
                sleep(time*1000);
                System.out.println("下载成功");
                HttpEntity httpEntity = request(url);
                String html = EntityUtils.toString(httpEntity,"utf-8");
                Document document = Jsoup.parse(html);

                //获取文章 每个p是一段文章
                Elements ps = document.select("p");
                ArrayList<String> strs = new ArrayList<String>();
                for(Element p : ps) {
                    strs.add(p.select("p").text());
                }

                PrintStream Stream = new PrintStream(new FileOutputStream(file));

                for(String str : strs) {
                    Stream.append(str);
                    Stream.append("\n\n");
                }
            }
            else {
                System.out.println("文件已存在");
            }
            cnt++;
        }




    }
}
