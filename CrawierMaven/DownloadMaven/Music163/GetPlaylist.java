package DownloadMaven.Music163;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class GetPlaylist {
    public static String GetHtml(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String html = null;
        HttpResponse response = null;

        HttpGet httpget = new HttpGet(url);

        httpget.setHeader("User-Agent", "Mozilla/5.0 (UrlWallHaven NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        httpget.setHeader("cookie", Login.GetCookie());

        response = httpClient.execute(httpget);
        //判断响应码
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity httpEntity = response.getEntity();
            html = EntityUtils.toString(httpEntity);
        }
        return html;
    }

    //得到歌曲的名字和下载Url并存储在Map中
    public static Map<String, String> GetUrls(String SongID) throws IOException {
        //使用字典存储歌曲名字和对应的id号
        Map<String, String> DataMap = new HashMap<>();



        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpResponse response = null;

        HttpGet httpget = new HttpGet("http://localhost:3000/playlist/detail?id="+SongID);

        httpget.setHeader("User-Agent", "Mozilla/5.0 (UrlWallHaven NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        httpget.setHeader("cookie", Login.GetCookie());

        //输入网址后执行请求
        try {
            response = httpClient.execute(httpget);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //判断响应码
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity httpEntity = response.getEntity();
            String html = EntityUtils.toString(httpEntity);

            JSONObject data = new JSONObject(html);

            JSONObject jo1 = data.getJSONObject("playlist");
            JSONArray jo2 = jo1.getJSONArray("tracks");

            for (int i = 0; i < jo2.length(); i++) {
                //拿取到美首歌曲的json对象
                JSONObject Data = (JSONObject) jo2.get(i);
                String name = Data.getString("name");
                int id = Data.getInt("id");
                DataMap.put(name, String.valueOf(id));
            }


            //从"privileges"中拿取id,单纯拿取id很简单,但是无法拿取到歌曲的名字信息
            /*JSONArray id = data.getJSONArray("privileges");

            ArrayList<String> SongIDs = new ArrayList<String>();
            for(int i = 0;i < id.length();i++) {
                JSONObject JsonSong = id.getJSONObject(i);

                int SongID = JsonSong.getInt("id");

                SongIDs.add(String.valueOf(SongID));
               *//*
               String str = id.getString(i);
               Gson gson = new Gson();
                Map<String,String> map = new HashMap<String,String>();
                map = gson.fromJson(str,map.getClass());
                System.out.println(map);*//*


                //JSONObject JsonSong = new JSONObject(id.get(i));

                //String ArraySong = JsonSong.getString("id");

                //String url = ArraySong.getString(1);
            }*/
        }
        return DataMap;
    }


    public static void Run(String SongID)  throws IOException{
        //接受歌曲信息
        Map<String, String> DataMap = GetUrls(SongID);
        //转换成set以进一步转换为数组操作
        Set name = DataMap.keySet();
        //得到歌曲的名称数组
        Object[] names = name.toArray();

        for (int i = 0; i < names.length; i++) {
            String SongUrl = "http://localhost:3000/song/url?id=" + DataMap.get(names[i]);
            String SongHtml = GetHtml(SongUrl);

            JSONObject JsonSong = new JSONObject(SongHtml);
            JSONArray ArraySong = JsonSong.getJSONArray("data");
            JSONObject DownUrl = ArraySong.getJSONObject(0);
            String Url = DownUrl.getString("url");
            //得到Url后准备写入文件中 文件夹的创建和判定
            File file = new File(".\\src\\main\\resourcs", "网易云音乐");
            if (!file.exists()) {
                file.mkdir();
            }

            //mp3文件
            String ChildFile = names[i] + ".mp3";
            File FileUser = new File(file, ChildFile);

            if (!FileUser.exists()) {
                System.out.println("正在请求中...");
                System.out.println("歌曲下载网址:"+Url);
                //将字节流写入文件中得到MP3文件中
                InputStream in = new URL(Url).openConnection().getInputStream();
                FileOutputStream f = new FileOutputStream(FileUser);
                byte[] bb = new byte[1024]; //接收bai缓存
                int len;
                while ((len = in.read(bb)) > 0) { //接收
                    f.write(bb, 0, len); //写入文件
                }
                f.close();
                in.close();
                //MP3Url.add(Url);
                //System.out.println(Url);
                System.out.println("请求成功!");
            } else {
                System.out.println("该歌曲已经下载过,已跳过");
            }
        }
            /*for(String Song : SongIDs) {
                System.out.println(Song);
            }*/
    }

    public static void main(String[] args) throws IOException {
        //歌单id
        String SongID = "3119864035";
        Run(SongID);
    }

}
