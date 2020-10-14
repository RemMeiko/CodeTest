package Deom.picture;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class Panoramic {
    //将网页请求打包成request函数
    private static Document request(String url) {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        Document document = null;
        //2.创建get请求，相当于在浏览器地址栏输入 网址
        HttpGet request = new HttpGet(url);
        //3、设置请求头配置
        request.setHeader("User-Agent", "Mozilla/5.0 (UrlWallHaven NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        //request.setHeader("Referer",url);
        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            sleep(1000);
            response = httpClient.execute(request);

            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                //请求的html文档
                String html = EntityUtils.toString(httpEntity, "utf-8");

                //进行jsoup解析
                document = Jsoup.parse(html);
            } else {
                //如果返回状态不是200，比如404（页面不存在）等
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
        }
        return document;
    }

    public static void main(String[] args) {
        try {
            /*Scanner scanner = new Scanner(System.in);
            System.out.print("请输入关键字:");
            Stristr = scanner.nextLine();*/
            String str = "壁纸";

            Document document = request("https://www.quanjing.com/search.aspx?q="+str);

            Elements lists = document.select("#content");
            //Elements lists = ID.getElementsByTag("li");

            for(Element list : lists) {
                System.out.println(list);
            }
    /*        //设置动态数组存储url
            ArrayList<String> Urls = new ArrayList<String>();
            //获取元素所在的所有列表
            Elements containers = document.getElementsByClass("item");
            for (Element container : containers) {
                Elements item = container.select(".card.progressive");
                //System.out.println("图片名字：" + item.select(".description h3").text());
                //System.out.println("网址：" + item.select("img").attr("src"));
                Urls.add(item.select("img").attr("src"));
            }
*/
            /*//第一次随机函数 构造器ran
            Random ran = new Random(5);
            for(String str : Urls) {
                //随机函数让间隔随机//第一次随机函数生成下一次函数的种子数
                int taowa = ran.nextInt(10);
                //第一次随机数作为第二次Randomh函数的种子数 构造器random
                Random random = new Random(taowa);
                int time = random.nextInt(10);
                //如果随机时间间隔小于5则循环重新随机生成
                do {
                    time = ran.nextInt(10);
                }while(time < 5);
                //挂起延迟 增加访问间隔时间
                sleep(time*1000);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
