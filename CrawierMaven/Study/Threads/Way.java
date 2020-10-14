package Study.Threads;



public class Way {
    public static void main(String[] args) {
        Ways co = new Ways();
        Thread t = new Thread(co);
        System.out.println("main begin t isAlive = " + t.isAlive());
        t.setName("A");
        t.start();
        System.out.println("main end t isAlive = " + t.isAlive());
    }

    public static class Ways extends Thread {
        public Ways() {
            System.out.println("countOperate------begin");
            System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
            System.out.println("Thread.currentThread().isAlive() = " + Thread.currentThread().isAlive());
            System.out.println("this.getName() = " + this.getName());
            System.out.println("this.isAlive() = " + this.isAlive());
            System.out.println("countOperate-------end");
        }

        @Override
        public void run() {
            System.out.println("run-----begin");
            System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
            System.out.println("Thread.currentThread().isAlive() = " + Thread.currentThread().isAlive());
            System.out.println("this.getName() = " + this.getName());
            System.out.println("this.isAlive()" + this.isAlive());
            System.out.println("run-----end");
        }
    }
}

