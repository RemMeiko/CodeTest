package Experiment;

import java.util.ArrayList;
import java.util.Scanner;

public class Displacement {
    public static void main(String[] args) {
        Password();
    }

    // ���ܽ��ܷ���
    public static void Password() {
        //Сд��ĸ
        char[] Capitals = new char[26];
        for (int i = 0; i < 26; i++) {
            Capitals[i] = (char) ('a' + i);
        }

        Scanner in = new Scanner(System.in);
        Scanner out = new Scanner(System.in);

        System.out.print("��������Կ����:");
        String str = in.nextLine();

        //ȡ���ո�
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
            System.out.println("~~~~~~~~~~~~~~~~~~~~~��λ�û������㷨~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("1.����  2.����  3.�˳�");
            System.out.print("���������ѡ��:");
            int choice = in.nextInt();

            if(choice == 3)
                break;

            System.out.print("������Ŀ���ַ���:");
            String word = out.nextLine();

            //����
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
            System.out.println("�����Ľ��:"+stringBuilder.toString());
        }
    }

    //ȥ���ַ������ظ�Ԫ�غͿո�
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
