package Deom.Baidu;

import java.util.Scanner;

public class Main {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20200715000519690";
    private static final String SECURITY_KEY = "CTUk7qoAciXoez_Qz4aX";

    public static void main(String[] args) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        Scanner in = new Scanner(System.in);
        System.out.print("请输入需要翻译:");
        String query = in.nextLine();

        //HttpResponse httpResponse = null;

        String a = api.getTransResult(query, "auto", "en");
        //ArrayList<String> strs = new ArrayList<String>();

        //JSONObject json = new JSONObject(Boolean.parseBoolean(a));

        String[] str = a.split(":");
        String target = str[5];
        target  = target.replace("\"","");
        str = target.split("}");
        System.out.print("翻译的结果:");
        System.out.println(str[0]);

        //System.out.println(api.getTransResult(query, "auto", "en"));
    }
}
