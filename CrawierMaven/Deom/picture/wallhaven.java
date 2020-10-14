package Deom.picture;

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
import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class wallhaven {

    private static HttpEntity request(String url) {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        Document document = null;
        HttpEntity  httpEntity = null;

        //1.设置超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(300000).setConnectionRequestTimeout(200000)
                .setSocketTimeout(300000).build();
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
        int time = random.nextInt(15);
        //如果随机时间间隔小于5则循环重新随机生成
        do {
            time = random.nextInt(15);
        }while(time < 10);
        return time;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        try {
            //构建第一个url，初始页面 keyword为关键字
            System.out.print("请输入关键字:");
            Scanner inScan = new Scanner(System.in);
            String keyword = inScan.nextLine();
            keyword = keyword.replace(" ","+");

            for (int i = 1; i < 100; i++) {
                //搜索url 第一个document
                String Url = "https://wallhaven.cc/search?q=" + keyword + "&page=" + i;
                System.out.println("正在请求中，请稍后...");
                HttpEntity firstentity = request(Url);
                String firsthtml = EntityUtils.toString(firstentity, "utf-8");
                Document firstdocument = Jsoup.parse(firsthtml);

                //解析第一个文档对象
                Elements Class = firstdocument.getElementsByClass("thumb-listing-page");
                //获取所有图片等信息的标签
                Elements lis = Class.select("ul li");

                //从标签中提取url并存储在urlStrs动态数组中
                ArrayList<String> urlStrs = new ArrayList<String>();
                for (Element li : lis) {
                    String urlStr = li.select("figure a").attr("href");
                    urlStrs.add(urlStr);
                }

                //创建存储的文件夹
                File file = new File(".\\src\\main\\resourcs\\wallhaven壁纸", "动漫女孩");
                //判断文件夹是否存在
                if (!file.exists()) {
                    file.mkdir();
                }

                //循环请求在urlStrs中获取的url
                int cnt = 1;
                for (String str : urlStrs) {
                    int time = Ran();//下载间隔时间

                    //进行请求并生成文档对象seconddocument
                    HttpEntity secondentity = request(str);
                    String secondhtml = EntityUtils.toString(secondentity, "utf-8");
                    Document seconddocument = Jsoup.parse(secondhtml);

                    /*
                     * 因为图片虽然在这一网址中，但直接获取无法获取存储
                     * 进行进一步解析提取图片url准备第三次请求
                     **/
                    //获取类
                    Elements scrollClass = seconddocument.select(".scrollbox");
                    String endurl = scrollClass.select("img").attr("src");

                    String childFile = i + "-" + cnt + ".jpg";
                    File Fileuser = new File(file, childFile);

                    if (!Fileuser.exists()) {
                        System.out.println("注意：<该网站图片下载根据网速而定，可能会很慢>");
                        System.out.println("请稍等，正在下载图片中....");
                        //进行最后一次请求
                        HttpEntity endhttpEntity = request(endurl);
                        sleep(time * 1000);
                        //将数据写进文件中
                        InputStream in = endhttpEntity.getContent();
                        FileUtils.copyInputStreamToFile(in, Fileuser);
                        System.out.println("下载成功");
                        System.out.println("下一次时间间隔是" + time + "s");
                    } else {
                        System.out.println("图片已存在，跳过下载");
                    }
                    cnt++;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
    }
}
