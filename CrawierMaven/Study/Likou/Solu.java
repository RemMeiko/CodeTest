package Study.Likou;

import javax.sound.midi.SoundbankResource;

public class Solu {

    public static  int missingNumber(int[] nums) {
        if(nums.length < 1 | nums.length>10000) {
            return 0;
        }
        int result = 0;
        boolean judge;
        for(int q = 0;q<nums.length+1;q++) {
            judge =false;
            for(int i :nums) {
                if(q == i) {
                    judge = true;
                    break;
                }
            }
            if(!judge) {
                result = q;
            }
        }
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        int[] a = new int[10001];
        for(int i = 0;i<10001;i++) {
            a[i] = i;
        }
        missingNumber(a);
    }
}
