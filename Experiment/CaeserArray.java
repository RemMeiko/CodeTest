package Experiment;

import java.util.Scanner;

public class CaeserArray {
    public static void main(String[] args) {
        //Сд��ĸ
        char[] Capitals = new char[26];
        for (int i = 0; i < 26; i++) {
            Capitals[i] = (char)('a'+i);
        }
        // ��д��ĸ
        char[] Lowercases = new char[26];
        for (int i = 0; i < 26; i++) {
            Lowercases[i] = (char)('A'+i);
        }

        // ����:
        Scanner in = new Scanner(System.in);
        boolean flag = true;
        while(flag) {
            System.out.println("-----------------------------------------------------------------");
            System.out.println("1.���� 2.���� 3.�˳�");
            System.out.print("��ѡ������Ҫ���еĲ���:");
            int choice  = in.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("���ܺ�Ľ��:"+Encryption(Capitals,Lowercases));
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

    // ����
    public static void Decryption(char[] Capitals,char[] Lowercases) {
        // �����ʼ�ַ�������Կ
        Scanner in = new Scanner(System.in);
        System.out.println("~~~~~~�����ǽ��ܲ���~~~~~~~");
        System.out.print("��������Ҫ���ܵ��ַ���:");
        String a = in.nextLine();
        System.out.print("������Կ(���û��������0):");
        int input = in.nextInt();

        // �������ܴ������
        StringBuilder decryResuit;

        boolean inputFlag = true;
        System.out.println("���ܺ�Ľ��:");
        for(int key = 1;key < 27;key++) {
            if(input != 0) {
                key  = input;
                inputFlag = false;
            }
            decryResuit = new StringBuilder();
            for(int i = 0;i < a.length();i++) {
                //����Ӣ�Ĵ�Сд�������ַ���
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

    // ����
    public static StringBuilder Encryption(char[] Capitals,char[] Lowercases) {
        // ������λ����ַ�
        StringBuilder stringBuilder = new StringBuilder();
        Scanner in = new Scanner(System.in);
        // �����ʼ�ַ�������Կ
        System.out.println("~~~~~~�����Ǽ��ܲ���~~~~~~~");
        System.out.print("��������Ҫ���ܵ��ַ���:");
        String a = in.nextLine();
        System.out.print("������Կ:");
        int key = in.nextInt();

        // ����
        for(int i = 0;i < a.length();i++) {
            //����Ӣ�Ĵ�Сд�������ַ���
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
