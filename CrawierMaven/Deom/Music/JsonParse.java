package Deom.Music;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JsonParse {
    public static Map<String, Integer> getSongId(String fileName){
        Map<String, Integer> songMap = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"utf-8"));
            BufferedReader reader2 = new BufferedReader(new FileReader(fileName));//会出现中文乱码问题
            String data = reader.readLine();
            JSONObject jo = new JSONObject(data);
            JSONObject jo1 = jo.getJSONObject("playlist");
            Boolean b = jo1.getBoolean("subscribed");
            System.out.println(b);
            JSONArray jo2 = jo1.getJSONArray("tracks");
            for(int i=0; i<jo2.length(); i++){
                JSONObject temp = (JSONObject) jo2.get(i);
                int id = temp.getInt("id");
                String name = temp.getString("name");
                songMap.put(name, id);
            }
            return songMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null ;
    }


    public static String getSongUrl(String fileName){
        String url = "" ;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String data = reader.readLine();
            JSONObject jo = new JSONObject(data);
            JSONArray ja = jo.getJSONArray("data");
            JSONObject jo1 = ja.getJSONObject(0);
            Object object = jo1.get("url");
            if(object instanceof String){
                System.out.println("ok");
                return (String)object;
            }else
                return "";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url ;
    }
}

