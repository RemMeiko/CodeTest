package DownloadMaven.ZhiHu;

import DownloadMaven.Request;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class Chinese {
    public static void main(String[] args) {
        GetKey();
        String keyword = Getkeyword();
        List<String> urls = obtain(keyword);
        download(urls,keyword);
    }

/*    private static class System {
        private static class out {
            public static void println(String a) {
                UrlWindows.show.append(a);
            }
            public static void print(String a) {
                UrlWindows.show.append(a);
            }
        }
    }*/


    public static String Getkeyword() {
        //选择你想要的图片类型
        System.out.print("请输入你想要的图片类型:");
        Scanner inkey = new Scanner(System.in);
        String keyword = inkey.nextLine();
        return keyword;
    }

    public static Set GetKey() {
        //创立一个字典接受数据
        HashMap<String, String> map = new HashMap<>();
        map = Zhihu.GetMap();
        //关于键值对字典数据结构的处理
        //获取所有的键
        System.out.println("可供选择的图片类型:");
        Set<String> keys = map.keySet();
        //打印可供选择的图片类型至输出台
        for (String key : keys) {
            System.out.print(key + "  ");
        }
        System.out.println("\n");
        return keys;
    }


    public static List<String> obtain(String keyword) {
        //创立一个字典接受数据
        HashMap<String, String> map = new HashMap<>();
        map = Zhihu.GetMap();
        ArrayList<String> urls = new ArrayList<>();
        //创立一个动态数组
        List<List<String>> list = new ArrayList<>();
        list = Zhihu.GetList();

/*        //选择你想要的图片类型
        System.out.print("请输入你想要的图片类型:");
        Scanner inkey = new Scanner(System.in);
        String keyword = inkey.nextLine();*/

        //自动确定对应的值
        String KeyUrl = map.get(keyword);
        /*while(KeyUrl == null) {
            System.out.print("对不起,暂不支持此类型,请重新输入:");
            keyword = inkey.nextLine();
            KeyUrl = map.get(keyword);
        }*/
        System.out.println(KeyUrl);

        //获取每个答案的特定数字标识
        String[] word = KeyUrl.split("/");
        String num = word[4];
        System.out.println("该图片类型标识数字是:" + num);

        System.out.println("开始进行请求....");

        /*//获取所有的值
        Collection<String> values = map.values();
        for(String value : values) {
            System.out.print(value+"  ");
        }*/

        String flag = num;
        //访问知乎的一篇关于壁纸的回答
        String url = "https://www.zhihu.com/api/v4/questions/" + flag + "/answers?include=data%5B*%5D.is_normal%2Cadmin_closed_comment%2Creward_info%2Cis_collapsed%2Cannotation_action%2Cannotation_detail%2Ccollapse_reason%2Cis_sticky%2Ccollapsed_by%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Ceditable_content%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Ccreated_time%2Cupdated_time%2Creview_info%2Crelevant_info%2Cquestion%2Cexcerpt%2Crelationship.is_authorized%2Cis_author%2Cvoting%2Cis_thanked%2Cis_nothelp%2Cis_labeled%2Cis_recognized%2Cpaid_info%2Cpaid_info_content%3Bdata%5B*%5D.mark_infos%5B*%5D.url%3Bdata%5B*%5D.author.follower_count%2Cbadge%5B*%5D.topics&offset=&limit=3&sort_by=default&platform=desktop";
        String cookies = "SESSIONID=oPT8F8PPWPRnSnNuQJHlsbtGUxxWHYuX7Xo7CsDui6L; osd=W1gQB0I6oqp5-fxmVTdbez8GvuZIR_7gGbO2MwB__pYsrqUeaFQK2Cvw8GJZKVadbiazsNXg9RsPKLDwk448SdA=; JOID=VlkcAE03o6Z-9vFnWTBUdj4KuelFRvLnFr63Pwdw85cgqaoTaVgN1ybx_GVWJFeRaSm-sdnn-hYOJLf_no8wTt8=; SESSIONID=Jc3WWylsAUP6kaCX69u8r9LAJ9M1UDKlbn71g8w3Prl; JOID=W1kVBU-PzuOWp622JIA7N9NQ7To885au-eDm53bHk9vE__nPHQUPnsaipLEggC9Ky8v5Dp4Pd23S8kCcUIGGU7k=; osd=V1oXCkmDzeGZoaG1Jo89O9BS4jww8JSh_-zl5XnBn9jG8P_DHgcAmMqhpr4mjCxIxM31DZwAcWHR8E-aXIKEXL8=; _zap=5d5be885-3958-421a-a3f1-cf0237abd1d5; _xsrf=2c654a3d-6ea9-4697-a8f7-3333164c87af; d_c0=\"ADAlHiCmfxCPTsUPyH05hbWjQmd2cgmJEKg=|1576224281\"; _ga=GA1.2.1183689345.1584115024; l_n_c=1; n_c=1; __utmz=51854390.1585029249.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); __utmc=51854390; __utmv=51854390.100--|2=registration_date=20181213=1^3=entry_date=20181213=1; __utma=51854390.1183689345.1584115024.1594711732.1594717207.3; JOID=UFEcAkKvkAuQECNqUaxg3drjb-NL9NJ23C9uEADxy2zvW0cUJxkqS8wQKGJRFSlIOa19iPaysLY6a2FXB_kx4wQ=; osd=UloUBUutmwOXGSFhWatp39HraOpJ_9px1S1lGAf4yWfnXE4WLBEtQs4bIGVYFyJAPqR_g_61ubQxY2ZeBfI55A0=; q_c1=9a9abbfcc2854ce39132a156fc9d0972|1599134403000|1585029245000; tst=r; SESSIONID=tYKmJCPTEU9TZYq8u9KTJpC9WlnjnvPq38WAsXQT44M; _gid=GA1.2.190681899.1602082469; l_cap_id=\"MzcxMzVlYmFlNThlNDAxMjg2ZTkwODVhNmNkMmEwYTQ=|1602137184|9389103335e6a1bdb0b8c618645395afdd12a542\"; r_cap_id=\"OGE4OTY1NWNjNmU2NGM1MDg0MjUyOWM4MWEzN2QyY2Q=|1602137184|504ecda59f31c51e49d56e138ed899bc2f1be302\"; cap_id=\"MWE3NWE0Yjg2YmY1NDQyNjhkY2FkZWFjYTRmZTJmNzQ=|1602137184|33cd2eb9192fec7c883eff9aa7199711ad2b462b\"; _gat_gtag_UA_149949619_1=1; Hm_lvt_98beee57fd2ef70ccdd5ca52b9740c49=1602082469,1602128415,1602137186,1602147627; capsion_ticket=\"2|1:0|10:1602147627|14:capsion_ticket|44:ODUwZmE1MjVkN2FjNGExMmJjODNiYTIyOTM5NjFkODQ=|d2f0f90a88b9158edec986997584035e4fcea8b266146392819bdb3b57534f3b\"; z_c0=\"2|1:0|10:1602147649|4:z_c0|92:Mi4xSjZDSURRQUFBQUFBTUNVZUlLWl9FQ1lBQUFCZ0FsVk5RU05zWUFDcjVJQm82bWZVVmdaYlNMZHFmZ045NW9Cb1dB|a0e8ee849dc95270f716a953c75de3d579935904f243a377f73d4a528bb9a740\"; unlock_ticket=\"ANCiNO5WqQ4mAAAAYAJVTUncfl_sIcfTADW8aVfAsIPjrmPr9OuCrg==\"; Hm_lpvt_98beee57fd2ef70ccdd5ca52b9740c49=1602147651; KLBRSID=e42bab774ac0012482937540873c03cf|1602147651|1602147625";
        //创立请求对象
        Request re = new Request();
        //请求网址的相关信息
        HttpEntity httpEntity = re.request(url, cookies);
        try {
            String html = EntityUtils.toString(httpEntity, "utf-8");
            Document document = Jsoup.parse(html);

            String data = document.text();

            //通过正则表达式获取图片的url
            String regex = "data-original=\\\\\"[a-zA-z]+://pic[^\\s\\\"\\\\]*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(data);


            while (matcher.find()) {
                String[] a = matcher.group().split("\"");
                urls.add(a[1]);
            }

            HashSet<String> set = new HashSet<>(urls);
            urls.clear();
            urls.addAll(set);

            System.out.println("所有图片的对应下载网址是:");
            for (String i : urls) {
                System.out.println(i);
            }
            System.out.println("该类型总共" + urls.size() + "张图片");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return urls;
    }

    //下载所有图片
    public static void download(List<String> urls,String keyword) {
        int cnt = 1;
        for (String str : urls) {
            GetOnePiture(cnt,keyword,str);
            cnt++;
        }
    }

    //下载单个图片
    public static void GetOnePiture(int cnt,String keyword,String str){ //文件子路径
        //写入文件中 //父路径
        File file = new File(".\\src\\main\\resourcs\\知乎壁纸", keyword);
        //判断文件夹是否存在
        if (!file.exists()) {
            file.mkdir();
        }

        String ChildFile = cnt + ".jpg";
        Request re = new Request();
        //时间间隔
        int time = re.Ran();

        File Fileuser = new File(file, ChildFile);
        //判断图片是否存在
        if (!Fileuser.exists()) {
            System.out.println("开始请求第" + cnt + "张图片....");
            //第一种请求 Httpclient请求
            HttpEntity httpEntity1 = re.request(str);
            //写入文件夹
            InputStream in = null;
            try {
                in = httpEntity1.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileUtils.copyInputStreamToFile(in, Fileuser);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*另一种下载方式
             * URL url = new URL(str);
             * FileUtils.copyURLToFile(url, Fileuser);*/

            //打印下载信息
            System.out.println("第" + cnt + "张图片请求下载成功");
            try {
                sleep(time * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("第" + cnt + "张图片已存在"/*+",本次时间间隔是"+(time*1000)*/);
        }
    }
}
