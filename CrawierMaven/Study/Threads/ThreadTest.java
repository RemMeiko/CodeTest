package Study.Threads;

public class ThreadTest {
    public static void main(String[] args) {
        System.out.println("main线程:"+Thread.currentThread());



        Thread mythreads2 = new Thread(new mythreads());
        mythreads2.setPriority(Thread.MAX_PRIORITY);
        mythreads2.start();

        Thread mythreads3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程三"+Thread.currentThread());
            }
        });
        mythreads3.start();

        Mythreads mythreads = new Mythreads();
        mythreads.start();
    }
}

class Mythreads extends Thread {
    @Override
    public void run() {
        //下面就是线程执行的代码,譬如:
        System.out.println("线程一"+Thread.currentThread());
    }
}

class mythreads implements Runnable {
    @Override
    public void run() {
        //下面是线程执行的代码,譬如:
        System.out.println("线程二"+Thread.currentThread());
    }
}


