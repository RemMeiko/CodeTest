package DownloadMaven.UrlWallHaven;

import DownloadMaven.Request;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Wallhaven {
    static Request re = new Request();

    public static void main(String[] args) {
        try {
            download(key());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String key() {
            //构建第一个url，初始页面 keyword为关键字
            System.out.print("请输入关键字:");
            Scanner inScan = new Scanner(System.in);
            String keyword = inScan.nextLine();
            keyword = keyword.replace(" ","+");
            return keyword;

    }

    //获取每一页的24个url
    public static List<String> GetPageUrls(String keyword,int i) throws IOException {
        //搜索url 第一个document
        String Url = "https://wallhaven.cc/search?q=" + keyword + "&page=" + i;
        System.out.println("正在请求中，请稍后...");
        HttpEntity firstentity = re.request(Url);
        String firsthtml = EntityUtils.toString(firstentity, "utf-8");
        Document firstdocument = Jsoup.parse(firsthtml);

        //解析第一个文档对象
        Elements Class = firstdocument.getElementsByClass("thumb-listing-page");
        //获取所有图片等信息的标签
        Elements lis = Class.select("ul li");

        //从标签中提取url并存储在urlStrs动态数组中
        ArrayList<String> urlStrs = new ArrayList<String>();
        int ii = 1;
        for (Element li : lis) {
            String urlStr = li.select("figure a").attr("href");
            System.out.println("第"+ii+"张图片的网址是:"+urlStr);
            urlStrs.add(urlStr);
            ii++;
        }
        return urlStrs;
    }

    //获取最终的图片的下载url 一次解析一个url拿到正式下载页面的url
    public static String GetEndUrl(String str) throws IOException {
        List<String> EndUrls = new ArrayList<>();

        //进行请求并生成文档对象seconddocument
        HttpEntity secondentity = re.request(str);
        String secondhtml = EntityUtils.toString(secondentity, "utf-8");
        Document seconddocument = Jsoup.parse(secondhtml);

        /*
         * 因为图片虽然在这一网址中，但直接获取无法获取存储
         * 进行进一步解析提取图片url准备第三次请求
         **/
        //获取类
        Elements scrollClass = seconddocument.select(".scrollbox");
        String endurl = scrollClass.select("img").attr("src");
        return endurl;
    }

    //返回该网页的图片数量
    public static int GetPage(String keyword) throws IOException {
        keyword = keyword.replace(" ","+");
        //请求第一次网址拿到图片的总个数和页数等信息
        HttpEntity NumFirst = re.request("https://wallhaven.cc/search?q=" + keyword);
        String NumHtml = EntityUtils.toString(NumFirst,"utf-8");
        Document DONum = Jsoup.parse(NumHtml);
        String StrNum = DONum.select(".listing-header h1").text();
        String[] StrFirst = StrNum.split(" ");
        String Strnum = StrFirst[0].replace(",","");
        int Num = Integer.parseInt(Strnum);//图片个数

        //可能会出现该关键字没有搜到图片
        if(Num == 0) {
            return Num;
        }

        int PageNum = Num/24 + 1;//图片页数
        return PageNum;
    }

    public static void download(String keyword) throws IOException, InterruptedException {
        try {

            int PageNum = GetPage(keyword);

            for (int i = 1; i < PageNum; i++) {
                List<String> urlStrs = GetPageUrls(keyword,i);

                //循环请求在urlStrs中获取的url
                int cnt = 1;
                for(String str : urlStrs) {
                    //调用下载图片的方法
                    LastDown(str,keyword,i,cnt);
                    cnt++;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("抱歉,出错了");
        }
    }

    //该方法每次调用下载一张图片
    public static  void LastDown(String str,String keyword,int i,int cnt) throws IOException, InterruptedException {
        //创建存储的文件夹
        File file = new File(".\\src\\main\\resourcs\\wallhaven壁纸", keyword);
        //判断文件夹是否存在
        if (!file.exists()) {
            file.mkdir();
        }

        int time = re.Ran();//下载间隔时间
        String endurl = GetEndUrl(str);
        String childFile = i + "-" + cnt + ".jpg";
        File Fileuser = new File(file, childFile);

        if (!Fileuser.exists()) {
            System.out.println("注意：<该网站图片下载根据网速而定，可能会很慢>");
            System.out.println("请稍等，正在下载图片中....");
            //进行最后一次请求
            HttpEntity endhttpEntity = re.request(endurl);
            sleep(time * 1000);
            //将数据写进文件中
            InputStream in = endhttpEntity.getContent();
            FileUtils.copyInputStreamToFile(in, Fileuser);
            sleep(1000);
            System.out.println("下载成功");
            System.out.println("下一次时间间隔是" + time + "s");
        } else {
            System.out.println("图片已存在，跳过下载");
        }
    }
}
