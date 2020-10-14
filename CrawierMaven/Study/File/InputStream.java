package Study.File;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InputStream {
    public static void main(String[] args) {
        File file = new File("E:\\图库\\动漫图片\\网址\\1.jpg");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            System.out.println(inputStream.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
