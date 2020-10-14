package Study.Likou;

/*
* 每日一题:第一题
* 魔术索引
*/

import java.util.ArrayList;
import java.util.List;

public class Moshusuoyin {
    public static void main(String[] args) {
        int[] nums = {2,1,4,4,5,2};
        /*int[] num = findMagicIndex(nums);
        for(int i : num) {
            System.out.println(i);
        }*/
        int i = findMagicIndex(nums);
        System.out.println(i);

    }

    public static int findMagicIndex(int[] nums) {
        List<String> datas = new ArrayList<String>();
        int i = 0;
        boolean flag = false;
        for(int num : nums) {
            if(num == i) {
                datas.add(String.valueOf(num));
                flag = true;
                break;
            }
            i++;
        }
        if(!flag) {
            datas.add("-1");
        }
        String[] strings = new String[datas.size()];
        datas.toArray(strings);


        int[] data = new int[strings.length];
        for(int i2 = 0;i2<strings.length;i2++) {
            data[i2] = Integer.parseInt(strings[i2]);
        }
        return data[0];
    }
}
