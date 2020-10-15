package Experiment;


public class Gcd {
/*    public static void main(String[] args) {
        int g = 1;
        Random ran  = new Random();
        while(g != 100) {
            int a = ran.nextInt();
            int b = ran.nextInt();

            System.out.println("a:"+a+",b:"+b);
            int re = a %  b;
            while(re != 0) {
                a  = b;
                b  = re;
                re = a % b;
            }
            System.out.println(b);
        }
    }*/

    public static void main(String[] args) {
        int a  = 2;
        double g = Math.sqrt(a);
        System.out.println(g);
        System.out.println(a);
    }
}
