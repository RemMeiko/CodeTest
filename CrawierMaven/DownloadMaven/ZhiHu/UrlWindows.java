package DownloadMaven.ZhiHu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UrlWindows extends JFrame {

    static JComboBox<String> t1;

    //控制线程的标志位
    volatile boolean flag = true;

    //用来控制两个线程交替执行的锁
    static final Object object = new Object();

    static JTextArea show = new JTextArea();//文本区域多行显示

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UrlWindows jf = new UrlWindows();
                    jf.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UrlWindows() {
        String name = "图片下载";
        setTitle(name);
        setSize(400, 400);
        setDefaultCloseOperation(UrlWindows.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //顶部信息栏
        JPanel top = new JPanel(new BorderLayout());
        this.add(top,BorderLayout.NORTH);
            //顶部类型选择
        JLabel LabelType = new JLabel("请选择图片类型:");
        LabelType.setFont(new Font("黑体", Font.BOLD, 15));
        LabelType.setForeground(Color.BLACK);
        top.add(LabelType,BorderLayout.EAST);
            //复选框
        HashMap<String,String> map = new HashMap<>();
        map = Zhihu.GetMap();
        Set<String> set = map.keySet();
        t1 = new JComboBox<>();
        t1.setModel(new DefaultComboBoxModel<String>(set.toArray(new String[0])));
        top.add(t1,BorderLayout.WEST);

        //底部信息栏
        JPanel drop = new JPanel(new FlowLayout());
        JButton start = new JButton("开始");
        JButton stop = new JButton("暂停");
        drop.add(start);
       // top.add(stop);
        this.add(drop, BorderLayout.SOUTH);

        final JScrollPane sp = new JScrollPane();
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setViewportView(show);
        show.setEditable(false);
        this.add(sp, BorderLayout.CENTER);

        //开始
        start.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //创立线程进行下载进行IO操作
                /*Mythread thread = new Mythread();
                Thread t = new Thread(thread);
                t.start();*/

                //下载线程
                Thread T1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (object) {
                            String Mykeyword = Objects.requireNonNull(t1.getSelectedItem()).toString();  //获取当前复选框选中的值
                            Chinese.GetKey();
                            List<String> Myurls = Chinese.obtain(Mykeyword);

                            //下载图片
                            int cnt = 1;
                            for (String str : Myurls) {
                                Chinese.GetOnePiture(cnt, Mykeyword, str);
                                cnt++;
                                if (!flag) {
                                    break;
                                }
                                object.notify();
                                try {
                                    object.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },"T1");

                Thread T2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (object) {
                            String keyword = Objects.requireNonNull(t1.getSelectedItem()).toString();  //获取当前复选框选中的值
                            List<String> urls = Chinese.obtain(keyword);
                            show.append("该类型图片总共有" + urls.size() + "张." + "\r\n");
                            show.append("下面是所有图片的下载链接:" + "\r\n");
                            //展示所有图片的下载链接
                            for (String url : urls) {
                                show.append(url + "\r\n");
                            }
                            show.append("请稍等,正在下载中..." + "\r\n");

                            String Mykeyword = Objects.requireNonNull(t1.getSelectedItem()).toString();  //获取当前复选框选中的值
                            List<String> Myurls = Chinese.obtain(Mykeyword);

                            //下载图片
                            int cnt = 1;
                            for (String str : Myurls) {
                                show.append("第" + cnt + "张图片下载成功"+"\r\n");
                                show.append("请稍等,正在下载第" + (cnt + 1) + "张图片..."+"\r\n");
                                cnt++;
                                if (!flag) {
                                    break;
                                }
                                object.notify();
                                try {
                                    object.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },"T2");

                T1.start();
                T2.start();
            }
        });

        //暂停
        stop.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               // myThread.setBa(false);
            }
        });

        //是否确认关闭
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int a = JOptionPane.showConfirmDialog(UrlWindows.this, "确定关闭吗？", "温馨提示",
                        JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION) {
                    flag = false;
                    setDefaultCloseOperation(UrlWindows.DISPOSE_ON_CLOSE); // 关闭
                }
                else {
                    setDefaultCloseOperation(UrlWindows.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

/*    //通过将线程包装成一个方法进行调用 这是下载线程
    public void SetIO() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String Mykeyword = Objects.requireNonNull(t1.getSelectedItem()).toString();  //获取当前复选框选中的值
                Chinese.GetKey();
                List<String> Myurls = Chinese.obtain(Mykeyword);

                //下载图片
                int cnt = 1;
                for (String str : Myurls) {
                    Chinese.GetOnePiture(cnt,Mykeyword,str);
                }

                Chinese.download(Myurls,Mykeyword);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/
}

//通过创建继承Thread的类来创立线程
/*
//用来进行界面显示的线程
class Thread2 implements Runnable {
    JComboBox<String> t1 = UrlWindows.t1;
    JTextArea show = UrlWindows.show;
    @Override
    public void run() {
        String keyword = Objects.requireNonNull(t1.getSelectedItem()).toString();  //获取当前复选框选中的值
        List<String> urls = Chinese.obtain(keyword);
        show.append("该类型图片总共有" + urls.size() + "张." + "\r\n");
        show.append("下面是所有图片的下载链接:" + "\r\n");
        for (String url : urls) {
            show.append(url + "\r\n");
        }
        show.append("开始进行下载" + "\r\n");
        show.append("正在下载中..." + "\r\n");
    }
}

//进行IO操作的线程
class Mythread implements Runnable {
    JComboBox<String> t1 = UrlWindows.t1;
    @Override
    public void run() {
        String Mykeyword = Objects.requireNonNull(t1.getSelectedItem()).toString();  //获取当前复选框选中的值
        //Chinese.xiazai(keyword);
        //UrlWindows.show.append("正在下载...");
        Chinese.GetKey();
        List<String> Myurls = Chinese.obtain(Mykeyword);
        Chinese.download(Myurls,Mykeyword);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}*/
