package Experiment;

import java.util.Scanner;

public class RSABase {
    // 加密和解密方法-->参数不同,结果不同
    // a:需要加密或者需要解密的字段 b:加密/解密指数 c: RSA算法的模数
    public static int candp(int a,int b,int c) {
        int r = 1;
        while(b != 0) {
            r = r*a;
            r = r%c;
            b--;
        }
        return r;
    }

    // 求得a,b两个数的最大公因数-->同时可以借此判断两个数是否互质
    public static int coPrime(int a,int b) {
        int re = a % b;
        while(re != 0) {
            a = b;
            b = re;
            re = a %b;
        }
        return b;
    }

    //判断一个数是不是素数 时间复杂度O(n/2)
  /*  public static boolean isPrimes(int n) {
        if(n < 2) return false;
        if(n == 2) return true;
        if(n % 2 == 0) return false;
        for(int i = 3;i < n;i += 2) {
            if(n %  i == 0) return false;
        }
        return true;
    }*/
    //判断一个数是不是素数 时间复杂度O(Math.sqrt(n/2))
    // 定理: 如果n不是素数, 则n有满足1< d<=sqrt(n)的一个因子d.
    public static boolean isPrimes(int n) {
        if(n < 2) return false;
        if(n == 2) return true;
        if(n % 2 == 0) return false;
        for(int i = 3;i * i< n;i += 2) {
            if(n %  i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int p,q,E,d,m,N,t,r;
        Scanner in = new Scanner(System.in);
        // 选择两个素数
        System.out.print("请输入素数p:");
        p = in.nextInt();
        while(!isPrimes(p)) {
            System.out.printf("对不起,%d不是素数,请输入素数p:",p);
            p = in.nextInt();
        }
        System.out.print("请输入素数q:");
        q = in.nextInt();
        while(!isPrimes(q)) {
            System.out.printf("对不起,%d不是素数,请输入素数q:",q);
            q = in.nextInt();
        }

        N = p*q;// 计算RSA算法的模数
        t  = (p-1)*(q-1);// &(n)

        // 确定E的值-->加密指数
        System.out.printf("请输入E(尽量大于1小于%d):",(q-1)*(p-1));
        E = in.nextInt();
        while(true) {
            int C = coPrime(t,E);
            if(C == 1) {
                break;
            }
            System.out.print("你输入的E不符合规定,请重新输入:");
            E = in.nextInt();
        }

        // 解密指数
        d = 1;
        while((d*E)%t != 1) {
            d++;
        }
        System.out.println("解密指数是:"+d);

        while(true) {
            int flag = 1;

            // 加密
            System.out.print("请输入你要加密的密文:");
            m = in.nextInt();
            r = candp(m,E,N);
            System.out.println("加密后的结果是:"+r);

            // 解密
            System.out.print("请输入你要解密的密文:");
            int y = in.nextInt();
            int u = candp(y,d,N);
            System.out.println("解密后的结果是:"+u);

            System.out.println("是否退出: 1-退出 2-继续");
            System.out.print("请选择:");
            flag = in.nextInt();
            if(flag == 1)
                break;
        }
    }
}
