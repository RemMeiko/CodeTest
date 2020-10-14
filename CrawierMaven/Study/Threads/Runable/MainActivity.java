/*
package Study.Threads.Runable;

public class MainActivity{
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        //2.创建MyRunnable实例
        MyRunnable runnable=new MyRunnable();
        //3.创建Thread对象
        //4.将MyRunnable放入Thread实例中
        Thread thread=new Thread(runnable);
        //5.通过线程对象操作线程(运行、停止)
        thread.start();

    }
    //1.实现runnable接口并重写run方法
    class MyRunnable implements Runnable{

        @Override
        public void run() {

        }
    }
}*/
