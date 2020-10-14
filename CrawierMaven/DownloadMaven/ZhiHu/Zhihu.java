package DownloadMaven.ZhiHu;

import DownloadMaven.Request;
import org.apache.http.HttpEntity;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Zhihu {

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



    public static Elements GetElements() {
        Elements ps = new Elements();
        //访问知乎的一篇关于壁纸的回答
        String url = "https://zhuanlan.zhihu.com/p/21428874";
        //创立请求对象
        Request re = new Request();

        //请求网址的相关信息
        HttpEntity httpEntity = re.request(url);
        try {
            //建立动态数组用来存放指向图片的链接 //第一次尝试
            ArrayList<String> ToUrl = new ArrayList<>();


            String html = EntityUtils.toString(httpEntity, "utf-8");
            Document document = Jsoup.parse(html);

            //所有图片链接都在P标签中,得到p标签
            Elements classs = document.getElementsByClass("Post-RichTextContainer");

            ps = classs.select("p");
            //System.out.println(ps);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public static HashMap<String,String> GetMap() {
        //字典使用键值对来存放数据
        HashMap<String, String> map = new HashMap<>();
        Elements ps = GetElements();
        //在P标签中分别得到图片的链接和图片的类型
        for (Element p : ps) {
            //指向图片的链接
            Elements Urls = p.select("a");

            Elements pas = p.select("a");


            for (Element pa : pas) {
                List<String> listStr = new ArrayList<>();
                String Url = pa.attr("href");
                String Name = pa.text();

                listStr.add(Url);
                listStr.add(Name);

                //添加字典的键值对,将图片类型作为键,图片链接作为值
                map.put(Name, Url);
            }

/*
            for(Element pa : pas) {

                String Url = pa.attr("href");
                String Name = pa.text();

                //添加字典的键值对,将图片类型作为键,图片链接作为值
                map.put(Name,Url);
            }*/


/*                String a = p.select("a").attr("href");
            String[] as = a.split("\n");
            //图片类型 所有的全部存放在字符串name中
            String name = p.select("a").text();
            String[] names = name.split(" ");
            String[] urls;*/

  /*          int i = 0;
            for(Element Url : Urls) {
                //System.out.println(names[i]);
                //添加进数组中
                ToUrl.add(Url.attr("href"));
                //urls[i] = Url.attr("href");
                //System.out.println(Url.attr("href"));
                i++;
            }*/

      /*      for(int size = 0;size<as.length;size++) {
                map.put(names[size],as[size]);
            }*/
        }
            return map;
    }

    public static List<List<String>> GetList() {
        //另一种方法
        List<List<String>> list = new ArrayList<>();

        Elements ps = GetElements();
        //在P标签中分别得到图片的链接和图片的类型
        for (Element p : ps) {
            //指向图片的链接
            Elements Urls = p.select("a");

            Elements pas = p.select("a");
            for (Element pa : pas) {
                List<String> listStr = new ArrayList<>();
                String Url = pa.attr("href");
                String Name = pa.text();

                listStr.add(Url);
                listStr.add(Name);
                list.add(listStr);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        List<List<String>> list = new ArrayList<>();
        list = GetList();

        //数据存储的文件夹
        File file1 = new File(".\\src\\main\\resourcs", "知乎壁纸");
        if (!file1.exists()) {
            file1.mkdir();
        }

        //Excel存储表格
        String ChildFile = "图片链接表" + ".xlsx";
        File fileuser = new File(file1, ChildFile);

        try {
            OutputStream outputStream = new FileOutputStream(fileuser);
            writeExcel(list, outputStream);
            System.out.println("添加成功！");
        } catch (Exception e) {
            System.out.println("文件夹正在被另一程序占用，请关闭该程序重新下载");
        }


        //System.out.println(map);
        //System.out.println(ps);
    }
}

