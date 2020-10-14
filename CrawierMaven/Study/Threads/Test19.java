package Study.Threads;

import org.apache.xalan.lib.ExsltStrings;

class Mythread extends Thread{
    @Override
    public void run() {
        try {
            /*for (int i = 0; i < 10; i++) {
                System.out.println(i);
                //(3000);
                if(this.isInterrupted()) {
                    throw new InterruptedException();
                }
            }*/

            System.out.println("begain");
            sleep(1);
            System.out.println("end");
/*            for (int j = 0; j < 10; j++) {
                System.out.println("j:"+j);
            }*/
        }catch (InterruptedException e) {
            System.out.println("run异常");
        }
    }
}

public class Test19 {
    public static void main(String[] args){
        try {
            Mythread t1 = new Mythread();
            t1.start();
            Thread.sleep(20);
            t1.interrupt();

        }catch (InterruptedException e) {
            System.out.println("进入了线程终止异常!");
            e.printStackTrace();
        }
    }
}
