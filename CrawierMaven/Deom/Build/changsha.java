package Deom.Build;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static java.lang.Thread.sleep;

public class changsha {

    public static void writeExcel(List<List<String>> list, OutputStream outputStream) {
        //创建工作簿
        XSSFWorkbook xssfWorkbook = null;
        xssfWorkbook = new XSSFWorkbook();

        //创建工作表
        XSSFSheet xssfSheet;
        xssfSheet = xssfWorkbook.createSheet();

        //创建行
        XSSFRow xssfRow;

        //创建列，即单元格Cell
        XSSFCell xssfCell;

        //把List里面的数据写到excel中
        for (int i = 0; i < list.size(); i++) {
            //从第一行开始写入
            xssfRow = xssfSheet.createRow(i);
            //创建每个单元格Cell，即列的数据
            List sub_list = list.get(i);
            for (int j = 0; j < sub_list.size(); j++) {
                xssfCell = xssfRow.createCell(j); //创建单元格
                xssfCell.setCellValue((String) sub_list.get(j)); //设置单元格内容
            }
        }
        try {
            //用输出流写到excel
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //将网页请求打包成request函数
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

                //进行Jsonp解析
                //document = Jsoup.parse(html);
            } else {
                //如果返回状态不是200，比如404（页面不存在）等
                httpEntity = response.getEntity();
                System.out.println("返回状态不是200");
                System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return httpEntity;
    }

    //每一次请求的时间间隔 随机函数生成
    public static int Ran() {
        ////挂起延迟 增加访问间隔时间
        //第一次随机数作为第二次Random函数的种子数 构造器random
        Random random = new Random();
        int time = random.nextInt(10);
        //如果随机时间间隔小于5则循环重新随机生成
        do {
            time = random.nextInt(10);
        }while(time < 5);
        return time;
    }


    public static void main(String[] args) {
        //输入关键字,确定第一次申请的Url
        Scanner in = new Scanner(System.in);
        System.out.print("请输入房源特征、地址、小区、楼盘名:");
        String keyword = in.nextLine();
        int flag = 0;

        //数据存储的文件夹
        File file1 = new File(".\\src\\main\\resourcs", "房源下载");
        if (!file1.exists()) {
            file1.mkdir();
        }

        //存储数据的列表结构，用来作为参数传至Excel的写入方法
        List<List<String>> result = new ArrayList<>();

        //Excel存储表格
        String ChildFile = keyword + ".xlsx";
        File fileuser = new File(file1, ChildFile);

        //用户输入的下载页数
        int Num = 1;
        int page = 1;
        //正式请求，第几页
        while(true) {
            //请求
            HttpEntity httpEntity = request("https://cs.anjuke.com/community/p" + page + "/?kw=" + keyword);
            System.out.println("请求完成");
            try {
                String Html = EntityUtils.toString(httpEntity, "utf-8");
                Document document = Jsoup.parse(Html);

                Elements Divs = document.select(".list-content .li-itemmod");

                //搜索结果
                Elements nums = document.select(".sortby .tit em");
                String Strnum = nums.last().text();
                //转换为整数型
                int num = Integer.parseInt(Strnum);


                //总共该关键字相关房源页数
                int pageNum = num/30+1;

                //用户输入需要下载的页数
                if(page == 1) {
                    System.out.print("当前一共有" + num + "个房源信息，总计"+pageNum+"页，请输入目标下载页数：");
                    Scanner ins = new Scanner(System.in);
                    Num = ins.nextInt();
                    if(Num > pageNum) {
                        System.out.print("你输入的页数超出范围，请重新输入：");
                        Num = ins.nextInt();
                    }
                    System.out.println("开始请求...");
                }
                //详细房源信息的url
                List<String> Urls = new ArrayList<String>();

                //房子价格和价格的升降率，用数组存储
                List<String> prices = new ArrayList<>();
                List<String> shenjiangs = new ArrayList<>();

                for (Element Div : Divs) {
                    //获取内部房源详细信息Url
                    Elements urls = Div.select("a");
                    //Elements title = Div.select(".li-info h3");
                    Elements price = Div.select(".li-side p strong");
                    prices.add(price.text()+"元/平米");

                    Elements shenjiang = Div.select(".li-side .price-txt.price-down");
                    shenjiangs.add(shenjiang.text());
                    //Elements address = Div.select(".li-info address");

                    Urls.add(urls.attr("href"));
                }

                int i = 0;
                for (String Url : Urls) {
                    //挂起
                    int time = Ran();
                    sleep(time * 1000);

                    HttpEntity httpEntity1 = request(Url);
                    String HtmlDetail = EntityUtils.toString(httpEntity1, "utf-8");
                    Document docuDetail = Jsoup.parse(HtmlDetail);

                    Elements ram = docuDetail.select(".basic-parms-mod");

                    Elements title = docuDetail.select(".comm-title a");

                    //dt是标题，类似于键值对中的键
                    //deA为需要的信息，类似于键值对中值
                    Elements dt = ram.select("dt");
                    Elements dtA = ram.select("dd");

                    //将值通过分割转换为数组，再将数组转换为列表
                    String[] values = dtA.text().split(" ");
                    //有些房源无旁边学校信息，进行简单判断
                    String school = "暂无数据";
                    if (!values[10].contains("学") && !values[10].contains("小")) {
                        school = values[10];
                        values[10] = "暂无数据";
                    }

                    //定义集合列表接受所有的字符串数组中的数据
                    List<String> Values = new ArrayList<String>();
                    List<String> Keys = new ArrayList<>();

                    if (flag == 0) {
                        //添加列表标题
                        String[] keys = dt.text().split("：");
                        Keys.add("名字");
                        Keys.add("价格");
                        Keys.add("价格变动");
                        //将keys数组集合中的数据全部添加至Keys列表集合中
                        Keys.addAll(Arrays.asList(keys));
                        result.add(Keys);
                    }
                    //额外且重要先添加地点名字,价格,价格变动率
                    Values.add(title.attr("title"));
                    Values.add(prices.get(i));
                    Values.add(shenjiangs.get(i));

                    //该写法等同于下面的循环添加写法
                    Values.addAll(Arrays.asList(values));
                /*for(String value:values) {
                    Values.add(value);
                }*/
                    if (!school.equals("暂无数据")) {
                        Values.add(school);
                        school = "暂无数据";
                    }
/*                for(String Value :Values) {
                    System.out.println(Value);
                }*/
                    result.add(Values);
                    //System.out.println("添加成功");
                    i++;
                    flag = 1;

                    System.out.println("第"+page+"页"+"第" + i + "个，" + title.attr("title") + "请求完毕，距离下一次请求：" + time + "s");
/*                System.out.println(dt.text());
                System.out.println(dtA.text());*/
                }
                if(page == Num) {
                    break;
//                    page = 100;
                }
            } catch (InterruptedException | IOException ex) {
                ex.printStackTrace();
            }
            page++;
        }
        try {
            OutputStream outputStream = new FileOutputStream(fileuser);
            writeExcel(result,outputStream);
        }catch (Exception e) {
            System.out.println("文件夹正在被另一程序占用，请关闭该程序重新下载");
        }
        System.out.println("添加成功！");
    }

}
