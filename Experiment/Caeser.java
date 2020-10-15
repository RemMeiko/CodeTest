package Experiment;

import java.util.Scanner;

public class Caeser {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("请输入目标字符串:");
        String words = in.nextLine();

        System.out.print("请输入密钥:");
        int key = in.nextInt();

        //加密
        StringBuilder Word = Encryption(words,key);

        String wot = Word.toString();
        System.out.println("加密后:"+Word);
        System.out.println("解密后:"+Decryption(wot,key));
    }

    // 加密
    public static StringBuilder Encryption(String words, int key) {
        int flag = 0;
        StringBuilder wordsLast = new StringBuilder();
        for (int i = 0; i < words.length(); i++) {
            char word = words.charAt(i);
            // 小写字母
            if (word >= 97 && word <= 122) {
                int Lastword = 'a' + (word - 97 + key) % 26;
                word = (char) Lastword;
                wordsLast.append(word);
            }
            // 大写字母
            else if (word >= 65 && word <= 90) {
                int Lastword = 'A' + (word - 65 + key) % 26;
                word = (char) Lastword;
                wordsLast.append(word);
            }
            // 其他字符
            else {
                if (word == 32) {
                    wordsLast.append(" ");
                }
                int[] Special = new int[words.length()];
                Special[flag] = i;
                flag++;
            }
        }
        return wordsLast;
    }

    //解密:
    public static StringBuilder Decryption(String Word, int key){
        // 解密
        int flag = 0;
        StringBuilder wordsLast = new StringBuilder();
        for (int i = 0; i < Word.length(); i++) {
            char word = Word.charAt(i);
            // 小写字母
            if (word >= 97 && word <= 122) {
                int Lastword = word -  key;
                word = (char) Lastword;
                wordsLast.append(word);
            }
            // 大写字母
            else if (word >= 65 && word <= 90) {
                int Lastword =word -  key;
                word = (char) Lastword;
                wordsLast.append(word);
            }
            // 其他字符
            else {
                if (word == 32) {
                    wordsLast.append(" ");
                }
                int[] Special = new int[Word.length()];
                Special[flag] = i;
                flag++;
            }
        }
        return wordsLast;
    }

}

