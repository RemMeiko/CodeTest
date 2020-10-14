package DownloadMaven.ZhiHu;

/*
* 简单介绍:知乎上周广恩的回答的的部分信息的爬取
* 时间:8.23
* 网址:https://www.zhihu.com/people/xi1zi/answers
*/

import DownloadMaven.Request;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ZhouGuangEn {
    public static void main(String[] args) throws IOException {
        //请求网页信息
        Request request = new Request();
        String url = "https://www.zhihu.com/people/xi1zi/answers";
        String cookies = "SESSIONID=3o7TQIWfwg4bCwIETIivT1OFVYALUV9hAeNz7tqgh3C; JOID=UFoXBE9sc9-yzj7hBmmFAvU7dG4XPDGv9fx4m1o_I7jAjleUcKt9m-jIPuQA7VWSczzT3Yz4LU_gISW3tYBL8DY=; osd=VFEVBU1oeN2zzDrqBGiHBv45dWwTNzOu9_hzmVs9J7PCj1WQe6l8mezDPOUC6V6Qcj7X1o75L0vrIyS1sYtJ8TQ=; _zap=5d5be885-3958-421a-a3f1-cf0237abd1d5; _xsrf=2c654a3d-6ea9-4697-a8f7-3333164c87af; d_c0=\"ADAlHiCmfxCPTsUPyH05hbWjQmd2cgmJEKg=|1576224281\"; _ga=GA1.2.1183689345.1584115024; l_n_c=1; n_c=1; __utmz=51854390.1585029249.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); __utmc=51854390; z_c0=\"2|1:0|10:1586071832|4:z_c0|92:Mi4xSjZDSURRQUFBQUFBTUNVZUlLWl9FQ1lBQUFCZ0FsVk5HTmQyWHdEVktTa1pQVGlydFpYd2hISU5iN0tRNWx2UTB3|3a6e1d5e0bf6814babe322ad0a7a0968949873f661a2b83e8ad71ed46c10111e\"; __utmv=51854390.100--|2=registration_date=20181213=1^3=entry_date=20181213=1; __utma=51854390.1183689345.1584115024.1594711732.1594717207.3; q_c1=9a9abbfcc2854ce39132a156fc9d0972|1596187059000|1585029245000; tst=r; _gid=GA1.2.951577597.1598069945; SESSIONID=PBuD4FzYUPgwCouvoo5VE1p19rSbaqajWTRxxSuYmjJ; JOID=UFEcAkKvkAuQECNqUaxg3drjb-NL9NJ23C9uEADxy2zvW0cUJxkqS8wQKGJRFSlIOa19iPaysLY6a2FXB_kx4wQ=; osd=UloUBUutmwOXGSFhWatp39HraOpJ_9px1S1lGAf4yWfnXE4WLBEtQs4bIGVYFyJAPqR_g_61ubQxY2ZeBfI55A0=; Hm_lvt_98beee57fd2ef70ccdd5ca52b9740c49=1597497164,1598071375,1598078590,1598163902; Hm_lpvt_98beee57fd2ef70ccdd5ca52b9740c49=1598163980; KLBRSID=ed2ad9934af8a1f80db52dcb08d13344|1598164036|1598161447";
        HttpEntity httpEntity = request.request(url,cookies);

        String html = EntityUtils.toString(httpEntity);
        Document document = Jsoup.parse(html);

        Elements elements = document.getElementsByClass("List-item");

        for(Element element : elements) {
            Elements Url = element.select("ContentItem-meta");
        }
        System.out.println(elements);
    }
}
