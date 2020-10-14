package Study.Passwd;

public class RSA {
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

    public static int jiami (int m) {
        int p, q, E, d, N, t, r;
        p = 91;
        q = 79;

        N = p * q;// 计算RSA算法的模数
        E = 53;
        // 加密
        r = candp(m, E, N);
        return r;
    }

    public static  int jiemi(int m) {
        int p, q, E, d, N, t, r;
        p = 91;
        q = 79;

        N = p * q;// 计算RSA算法的模数
        // 解密指数
        d = 989;
        // 加密
        r = candp(m, d, N);
        return r;
    }

    public static void main(String[] args) {
        System.out.println(jiemi(30));
    }
}
