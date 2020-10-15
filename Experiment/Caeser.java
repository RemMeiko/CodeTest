package Experiment;

import java.util.Scanner;

public class Caeser {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("������Ŀ���ַ���:");
        String words = in.nextLine();

        System.out.print("��������Կ:");
        int key = in.nextInt();

        //����
        StringBuilder Word = Encryption(words,key);

        String wot = Word.toString();
        System.out.println("���ܺ�:"+Word);
        System.out.println("���ܺ�:"+Decryption(wot,key));
    }

    // ����
    public static StringBuilder Encryption(String words, int key) {
        int flag = 0;
        StringBuilder wordsLast = new StringBuilder();
        for (int i = 0; i < words.length(); i++) {
            char word = words.charAt(i);
            // Сд��ĸ
            if (word >= 97 && word <= 122) {
                int Lastword = 'a' + (word - 97 + key) % 26;
                word = (char) Lastword;
                wordsLast.append(word);
            }
            // ��д��ĸ
            else if (word >= 65 && word <= 90) {
                int Lastword = 'A' + (word - 65 + key) % 26;
                word = (char) Lastword;
                wordsLast.append(word);
            }
            // �����ַ�
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

    //����:
    public static StringBuilder Decryption(String Word, int key){
        // ����
        int flag = 0;
        StringBuilder wordsLast = new StringBuilder();
        for (int i = 0; i < Word.length(); i++) {
            char word = Word.charAt(i);
            // Сд��ĸ
            if (word >= 97 && word <= 122) {
                int Lastword = word -  key;
                word = (char) Lastword;
                wordsLast.append(word);
            }
            // ��д��ĸ
            else if (word >= 65 && word <= 90) {
                int Lastword =word -  key;
                word = (char) Lastword;
                wordsLast.append(word);
            }
            // �����ַ�
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

