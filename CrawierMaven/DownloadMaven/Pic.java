package DownloadMaven;


import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Pic {

    public static void main(String[] args) throws IOException {
        Request re = new Request();

        //写入文件中 //父路径
        File file = new File(".\\src\\main\\resourcs","学习通PPT图片");
        File file2 = new File("E:\\1");
        //判断文件夹是否存在
        if(!file.exists()) {
            file.mkdir();
        }
        for (int j = 1; j < 18; j++) {
            String Url = "https://s3.ananas.chaoxing.com/doc/b8/ef/00/0de07c827629d61b34e25f700c7d5458/thumb/"+j+".png";

            //下载文件名字
            String Children = j+".jpg";
            File Fileuser = new File(file,Children);
            //判断图片是否存在
            if(!Fileuser.exists()) {
                //第一种请求 Httpclient请求
                HttpEntity httpEntity1 = re.request(Url);
                //写入文件夹
                InputStream in = httpEntity1.getContent();
                FileUtils.copyInputStreamToFile(in, Fileuser);
            }
            else {
                System.out.println("错误");
            }
        }
    }
}
