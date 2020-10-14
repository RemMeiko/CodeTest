package Deom.Other;

import org.apache.commons.io.FileUtils;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/*
 * 获取恺兄博客
 * 网址:https://wlaport.top/
 */

public class wlaport {

    //将网页请求打包成request函数
    private static HttpEntity request(String url) {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        Document document = null;
        HttpEntity httpEntity = null;

        //1.设置超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000).setConnectionRequestTimeout(2000)
                .setSocketTimeout(3000).build();
        //2.创建get请求，相当于在浏览器地址栏输入 网址
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        //3、设置请求头配置
        request.setHeader("User-Agent", "Mozilla/5.0 (UrlWallHaven NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        //request.setHeader("Referer", "https://bing.ioliu.cn/");
        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键

            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
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
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
        }
        return httpEntity;
    }

    public static void main(String[] args) {
        try {
            for (int i = 1; i < 4; i++) {
                try {
                    HttpEntity httpEntity = request("https://wlaport.top/?paged=" + i);
                    String html = EntityUtils.toString(httpEntity, "utf-8");
                    Document document = Jsoup.parse(html);

                    Elements layouts = document.getElementsByClass("kratos-hentry clearfix");

                    ArrayList<String> titles = new ArrayList<String>();//文章标题
                    ArrayList<String> urls = new ArrayList<String>();//url
                    ArrayList<String> images = new ArrayList<String>();//文章首页图片
                    for (Element layout : layouts) {
                        Elements title = layout.select(".kratos-entry-title-new");
                        Elements url = layout.select(".kratos-entry-title-new");
                        Elements image = layout.select(".kratos-entry-thumb-new");
                        titles.add(title.text());
                        urls.add(url.select("a").attr("href"));
                        images.add(image.select("img").attr("src"));
                        //System.out.println(url);
                    }
                        /*for (int j = 0; j < urls.size(); j++) {
                            System.out.println("文章标题: " + titles.get(j));
                            System.out.println("对应的网址: " + urls.get(j));
                            System.out.println("文章图片网址: " + images.get(j));
                        }*/

                    //获取Html存储在文件中
                    File fileHtml = new File(".\\src\\main\\resourcs", "wlaport恺");
                    //判断文件夹是否存在
                    if (!fileHtml.exists()) {
                        fileHtml.mkdir();
                    }
                    int cnt = 0;
                    for (String str : urls) {
                        HttpEntity httpEntity1 = request(str);

                        //去掉标题中的|符号,空格无法命名为文件夹
                        String title = titles.get(cnt).replace("|","_");
                        //再去掉斜杠,防止生成文件夹
                        title = title.replace("/","_");
                        String ChildFile = i +"-"+ (cnt+1) +"-"+title+ ".html";

                        File Fileuser = new File(fileHtml, ChildFile);
                        //判断图片是否存在
                        if (!Fileuser.exists()) {
                            //写入文件夹
                            InputStream in = httpEntity1.getContent();
                            FileUtils.copyInputStreamToFile(in, Fileuser);
                        }
                        else {
                            System.out.println("-----------------文件已存在---------------");
                        }
                        cnt++;
                        System.out.println(title+"-第"+i +"-"+ (cnt+1)+"篇下载成功");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }finally {
        }
    }
}




