package Deom.Music;

import java.io.*;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public class Download {
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException {
/*        DefaultHttpClient httpclient = new DefaultHttpClient(new PoolingClientConnectionManager());
        HttpGet httpget = new HttpGet("http://localhost:3000/login/cellphone?phone=17508470656&password=123music163.");
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            response = httpclient.execute(httpget);
            entity = response.getEntity();

            CookieStore cookieStore = httpclient.getCookieStore();//获取cookies
            httpclient.setCookieStore(cookieStore);//设置cookies
            String S = cookieStore.toString();

        }catch (Exception e) {
            e.printStackTrace();
        }*/
        /*URL url2 = new URL("http://localhost:3000/login/cellphone?phone=17508470656&password=123music163.");
        HttpURLConnection conn = (HttpURLConnection)url2.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; UrlWallHaven NT; DigExt)");
        CookieManager manager = new CookieManager(); //设置cookie策略，只接受与你对话服务器的cookie，而不接收Internet上其它服务器发送的cookie
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);*/


        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; UrlWallHaven NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }
        System.out.println("info:"+url+" download success");

    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void main(String[] args) {
        FileWriter fileWriter = null ;
        try {
            fileWriter = new FileWriter("d:\\url.txt");
        } catch (IOException e) {
            System.out.println("文件创建失败");
        }
        //通过第一次获得的是关于歌曲的name和对应的id
        Map<String, Integer> songMap = JsonParse.getSongId("d:\\id.json");
        Set name = songMap.keySet();
        Object[] names = name.toArray();
        for(int i=0; i<names.length; i++){
            //然后我们通过歌曲的id,循环得到歌曲对应的具体的json文件
            //其中就包含了我们想要的下载地址的url
            try{
                downLoadFromUrl("http://127.0.0.1:3000/song/url?id="+songMap.get(names[i]),
                        "temp.json","d:/");
            }catch (Exception e) {
                // TODO: handle exception
            }
            //通过第二次解析，我们的到的是所有歌曲的json文件中的url
            String url = JsonParse.getSongUrl("D:\\temp.json");
            if(url=="") continue;
            try {
                //进行保存歌曲文件
                downLoadFromUrl(url,  names[i].toString()+".mp3", "e:/song");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

