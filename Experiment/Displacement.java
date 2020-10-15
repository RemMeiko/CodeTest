package Experiment;

import java.util.ArrayList;
import java.util.Scanner;

public class Displacement {
    public static void main(String[] args) {
        Password();
    }

    // 加密解密方法
    public static void Password() {
        //小写字母
        char[] Capitals = new char[26];
        for (int i = 0; i < 26; i++) {
            Capitals[i] = (char) ('a' + i);
        }

        Scanner in = new Scanner(System.in);
        Scanner out = new Scanner(System.in);

        System.out.print("请输入密钥短语:");
        String str = in.nextLine();

        //取出空格
        str = str.replace(" ","");
        str = Removal(str);

        ArrayList<Character > cipher = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            cipher.add(str.charAt(i));
        }

        boolean flag = false;
        for (int i = 0; i < Capitals.length; i++) {
            for (int j = 0; j < str.length(); j++) {
                if(Capitals[i] ==  cipher.get(j)) {
                    flag = true;
                }
            }
            if(!flag) {
                cipher.add(Capitals[i]);
            }
            flag = false;
        }

        boolean cycleFlag = true;
        while(cycleFlag) {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~移位置换加密算法~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("1.加密  2.解密  3.退出");
            System.out.print("请输入你的选择:");
            int choice = in.nextInt();

            if(choice == 3)
                break;

            System.out.print("请输入目标字符串:");
            String word = out.nextLine();

            //解密
            StringBuilder stringBuilder = new StringBuilder();
            int flag2;
            for (int i = 0; i < word.length(); i++) {
                for (int j = 0; j < cipher.size(); j++) {
                    if (word.charAt(i) == ' ') {
                        stringBuilder.append(' ');
                        break;
                    }
                    if (choice == 2) {
                        if (word.charAt(i) == cipher.get(j)) {
                            flag2 = j;
                            stringBuilder.append(Capitals[flag2]);
                            break;
                        }
                    } else {
                        if (word.charAt(i) == Capitals[j]) {
                            flag2 = j;
                            stringBuilder.append(cipher.get(flag2));
                            break;
                        }
                    }
                }
            }
            System.out.println("处理后的结果:"+stringBuilder.toString());
        }
    }

    //去掉字符串中重复元素和空格
    public static String Removal(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charWord = str.charAt(i);
            int firstIndexOf  = str.indexOf(charWord);
            int lastIndexOf = str.lastIndexOf(charWord);
            if(firstIndexOf == lastIndexOf || firstIndexOf == i) {
                stringBuilder.append(charWord);
            }
        }
        return stringBuilder.toString();
    }
}
