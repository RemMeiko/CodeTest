package DownloadMaven.BiYing;

import DownloadMaven.Request;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

/*
 * 获取必应网站的壁纸
 * 网址:https://bing.ioliu.cn/
 */

public class BiYing {
    static Request re = new Request();

    //拿取首页相关数量信息函数PageNum //未曾调用
    public static String[] PageNum() {
        String[] num = null;
        //拿取首页的图片数量相关信息
        String StrUrl = "https://bing.ioliu.cn";
        HttpEntity PageHome = re.request(StrUrl);
        try {
            String Pagehtml = EntityUtils.toString(PageHome,"utf-8");
            Document pagedocu = Jsoup.parse(Pagehtml);
            String Numdiv = pagedocu.select(".page span").first().text();//结果是1 / 133 字符串
            String Num = Numdiv.replace(" ","");//去掉空格 结果是1/133 字符串
            num = Numdiv.split(" ");//以空格分割成字符串数组
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
        return num;
    }


    public static List<String> GetUrls(int i) {
        //设置动态数组存储url
        ArrayList<String> Urls = new ArrayList<String>();
        try {
            //请求主页时候挂起5s
            System.out.println("正在请求主页中...."+"请等待5s");
            sleep(5000);
            HttpEntity PageHome = re.request("https://bing.ioliu.cn/?p="+i);
            System.out.println("请求成功,接下来开始下载这一页图片...");
            //请求的html文档
            String Pagehtml = EntityUtils.toString(PageHome,"utf-8");
            Document pagedocu = Jsoup.parse(Pagehtml);
            //Document document = request("https://bing.ioliu.cn/?p="+i);
            //获取url所在的所有列表
            Elements containers = pagedocu.getElementsByClass("item");
            for (Element container : containers) {
                Elements item = container.select(".card.progressive");
                //System.out.println("图片名字：" + item.select(".description h3").text());
                //System.out.println("网址：" + item.select("img").attr("src"));
                Urls.add(item.select("a").attr("href"));
                System.out.println(item.select("a").attr("href"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Urls;
    }

    public static void main(String[] args) {
        try {

            Scanner shuru  = new Scanner(System.in);
            System.out.print("请输入下载的起始页码:");
            String start = shuru.nextLine();
            int startNum = Integer.parseInt(start);
            System.out.print("你希望下载多少页:");
            String Num1 = shuru.nextLine();
            int NumNum = Integer.parseInt(Num1);

            String StrUrl = "https://bing.ioliu.cn";
            for (int i = startNum; i < startNum+NumNum; i++) {
                //请求主页时候挂起5s
                System.out.println("正在请求主页中...."+"请等待5s");
                sleep(5000);
                HttpEntity PageHome = re.request("https://bing.ioliu.cn/?p="+i);
                System.out.println("请求成功,接下来开始下载这一页图片...");
                //请求的html文档
                String Pagehtml = EntityUtils.toString(PageHome,"utf-8");
                Document pagedocu = Jsoup.parse(Pagehtml);

                //拿取请求页面的图片相关数量信息
                String Numdiv = pagedocu.select(".page span").first().text();//结果是1 / 133 字符串
                String Num = Numdiv.replace(" ","");//去掉空格 结果是1/133 字符串
                String[] num = Numdiv.split(" ");//以空格分割成字符串数组

                //拿取图片的相关其他信息存储在数组Names中
                ArrayList<String> Names = new ArrayList<String>();
                Elements Namedivs = pagedocu.getElementsByClass("description");
                for (Element Namediv : Namedivs) {
                    String name = Namediv.select("h3").text();
                    String[] Name = name.split("\\(");
                    Names.add(Name[0]);
                    //System.out.println(Names);//譬如奥林匹克国家公园中的可可西里雨林，华盛顿州
                }
                //String Namediv =  pagedocu.select(".description h3").first().text();
                //String[] Name = Namediv.split("\\(");//图片名字


                List<String> Urls  = new ArrayList<>();
                //Document document = request("https://bing.ioliu.cn/?p="+i);
                //获取url所在的所有列表
                Elements containers = pagedocu.getElementsByClass("item");
                for (Element container : containers) {
                    Elements item = container.select(".card.progressive");
                    //System.out.println("图片名字：" + item.select(".description h3").text());
                    //System.out.println("网址：" + item.select("img").attr("src"));
                    Urls.add(item.select("a").attr("href"));
                }
                //写入文件中 //父路径
                File file = new File(".\\src\\main\\resourcs","必应壁纸");
                File file2 = new File("E:\\1");
                //判断文件夹是否存在
                if(!file.exists()) {
                    file.mkdir();
                }
                int cnt = 1;
                for(String str : Urls) {
                    /*
                     * https://bing.ioliu.cn/photo/WaterRipplesVideo_ZH-CN8790763092?force=1可以访问的url,但无法下载,最后需要变化
                     * https://bing.ioliu.cn/photo/WaterRipplesVideo_ZH-CN8790763092?force=download下载url*/
                    //url中需要替换的目标字符串
                    String strobeNam = "home_"+i;
                    str = str.replace(strobeNam,"download");
                    System.out.println("图片网址是:"+StrUrl+str);

                    //文件子路径
                    String ChildFile = i+"-"+cnt+"("+num[2]+")-"+Names.get(cnt-1)+".jpg";
                    //时间间隔
                    int time = re.Ran();

                    File Fileuser = new File(file,ChildFile);
                    //判断图片是否存在
                    if(!Fileuser.exists()) {
                        //第一种请求 Httpclient请求
                        HttpEntity httpEntity1 = re.request(StrUrl+str);
                        //写入文件夹
                        InputStream in = httpEntity1.getContent();
                        FileUtils.copyInputStreamToFile(in, Fileuser);

                        /*另一种下载方式
                         * URL url = new URL(str);
                         * FileUtils.copyURLToFile(url, Fileuser);*/

                        //打印下载信息
                        System.out.println("第"+num[0]+"页"+i+"-"+cnt+"下载成功，总计有"+num[2]+"页,下次时间间隔是"+time+"s");
                        sleep(time*1000);
                    }
                    else {
                        System.out.println("第"+num[0]+"页"+i+"-"+cnt+"张图片已存在"/*+",本次时间间隔是"+(time*1000)*/);
                    }
                    cnt++;


                    //第二种请求---Jsoup请求
                    /*使用Jsoup连接请求进行下载
                    Connection.Response images = Jsoup.connect(str).ignoreContentType(true).execute();
                    Document document1 = request(Urls.get(1));
                    FileOutputStream Out = new FileOutputStream(new java.io.File(file,ChildFile));
                    if(images.bodyAsBytes() != null) {
                        Out.write(images.bodyAsBytes());
                        Out.close();
                        System.out.println("下载成功，第"+cnt+"张"+",本次时间间隔是"+(time*1000));
                        cnt++;
                    }
                    else {
                        System.out.println("下载失败,该图片不存在");
                    }*/
                }
            }
            System.out.println("下载完成!");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
    }
}

