package Experiment;

import java.util.Scanner;

public class CaeserArray {
    public static void main(String[] args) {
        //小写字母
        char[] Capitals = new char[26];
        for (int i = 0; i < 26; i++) {
            Capitals[i] = (char)('a'+i);
        }
        // 大写字母
        char[] Lowercases = new char[26];
        for (int i = 0; i < 26; i++) {
            Lowercases[i] = (char)('A'+i);
        }

        // 测试:
        Scanner in = new Scanner(System.in);
        boolean flag = true;
        while(flag) {
            System.out.println("-----------------------------------------------------------------");
            System.out.println("1.加密 2.解密 3.退出");
            System.out.print("请选择你想要进行的操作:");
            int choice  = in.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("加密后的结果:"+Encryption(Capitals,Lowercases));
                    break;
                case 2:
                    Decryption(Capitals,Lowercases);
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    break;
            }
        }
    }

    // 解密
    public static void Decryption(char[] Capitals,char[] Lowercases) {
        // 输入初始字符串和密钥
        Scanner in = new Scanner(System.in);
        System.out.println("~~~~~~下面是解密操作~~~~~~~");
        System.out.print("请输入需要解密的字符串:");
        String a = in.nextLine();
        System.out.print("输入密钥(如果没有则输入0):");
        int input = in.nextInt();

        // 用来接受处理后结果
        StringBuilder decryResuit;

        boolean inputFlag = true;
        System.out.println("解密后的结果:");
        for(int key = 1;key < 27;key++) {
            if(input != 0) {
                key  = input;
                inputFlag = false;
            }
            decryResuit = new StringBuilder();
            for(int i = 0;i < a.length();i++) {
                //除了英文大小写的其他字符串
                if(a.charAt(i) < 65 | a.charAt(i) > 122 | (a.charAt(i) > 90 & a.charAt(0) < 97)) {
                    decryResuit.append(a.charAt(i));
                }
                for(int j = 0;j < Capitals.length;j++) {
                    if (Capitals[j] == a.charAt(i)) {
                        int f = (j+26-key)%26;
                        decryResuit.append(Capitals[f]);
                        break;
                    }
                }
                for(int j = 0;j < Lowercases.length;j++) {
                    if(Lowercases[j] == a.charAt(i)) {
                        decryResuit.append(Lowercases[(j+26-key)%26]);
                        break;
                    }
                }
            }
            System.out.println(decryResuit);
            if(!inputFlag) {
                break;
            }
        }
    }

    // 加密
    public static StringBuilder Encryption(char[] Capitals,char[] Lowercases) {
        // 接受移位后的字符
        StringBuilder stringBuilder = new StringBuilder();
        Scanner in = new Scanner(System.in);
        // 输入初始字符串和密钥
        System.out.println("~~~~~~下面是加密操作~~~~~~~");
        System.out.print("请输入需要加密的字符串:");
        String a = in.nextLine();
        System.out.print("输入密钥:");
        int key = in.nextInt();

        // 加密
        for(int i = 0;i < a.length();i++) {
            //除了英文大小写的其他字符串
            if(a.charAt(i) < 65 | a.charAt(i) > 122 | (a.charAt(i) > 90 & a.charAt(0) < 97)) {
                stringBuilder.append(a.charAt(i));
                //System.out.println(stringBuilder.toString());
            }
            for(int j = 0;j < Capitals.length;j++) {
                if (Capitals[j] == a.charAt(i)) {
                    int f = (j + key) % 26;
                    stringBuilder.append(Capitals[f]);
                    //System.out.println(stringBuilder.toString());
                    break;
                }
            }
            for(int j = 0;j < Lowercases.length;j++) {
                if(Lowercases[j] == a.charAt(i)) {
                    int f  = (j+key)%26;
                    stringBuilder.append(Lowercases[f]);
                    //System.out.println(stringBuilder.toString());
                    break;
                }
            }
        }
        return stringBuilder;
    }
}
