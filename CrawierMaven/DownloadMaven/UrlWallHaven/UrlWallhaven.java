package DownloadMaven.UrlWallHaven;

import DownloadMaven.Frame;
import DownloadMaven.ZhiHu.UrlWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;


public class UrlWallhaven extends JFrame {

    //使用flag标志位对线程进行控制
    volatile  boolean flag = true;

    static final Object object = new Object();

    Frame frameBy = new Frame();
    static JTextField key;
    static JTextArea show = new JTextArea();//文本区域多行显示

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UrlWallhaven BiYing = new UrlWallhaven();
                    BiYing.setVisible(true);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UrlWallhaven() {
        String name = "图片下载";
        setTitle(name);
        setSize(400, 400);
        setDefaultCloseOperation(UrlWindows.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //顶部信息栏
        JPanel top = new JPanel(new FlowLayout());
        this.add(top,BorderLayout.NORTH);

        //label
        JLabel labelkey = new JLabel("请输入关键字:");
        labelkey.setFont(new Font("宋体",Font.PLAIN,20));
        //labelkey.setForeground(Color.PINK);
        top.add(labelkey);

        key =new JTextField(20);
        top.add(key);

        final JScrollPane sp = new JScrollPane();
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setViewportView(show);
        show.setEditable(false);
        this.add(sp, BorderLayout.CENTER);

        //底部信息栏
        //顶部信息栏
        JPanel drop = new JPanel(new FlowLayout());
        this.add(drop,BorderLayout.SOUTH);

        JButton start = new JButton("开始下载");
        drop.add(start);


        //下载线程
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    //得到图片搜索关键字
                    String keyword = UrlWallhaven.key.getText();
                    keyword = keyword.replace(" ","+");

                    try {
                        //进行下载
                        int PageNum = Wallhaven.GetPage(keyword);
                        for (int i = 1; i < PageNum; i++) {
                            List<String> urlStrs = Wallhaven.GetPageUrls(keyword, i);
                            int cnt = 1;
                            for(String str : urlStrs) {
                                //调用下载图片的方法
                                Wallhaven.LastDown(str,keyword,i,cnt);
                                cnt++;
                                if(Thread.interrupted()) {
                                    break;
                                }
                                Thread.currentThread().getId();
                                object.notify();
                                object.wait();
                            }
                        }
                    } catch (IOException | InterruptedException e) {
                        System.out.println("下载线程已经结束下载");
                    }
                }
            }
        },"t1");


        //界面显示线程
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    //得到图片搜索关键字
                    String keyword = UrlWallhaven.key.getText();
                    keyword = keyword.replace(" ","+");
                    try {
                        //进行下载
                        int PageNum = Wallhaven.GetPage(keyword);
                        for (int i = 1; i < PageNum; i++) {
                            List<String> urlStrs = Wallhaven.GetPageUrls(keyword,i);

                            //循环请求在urlStrs中获取的url
                            int cnt = 1;
                            for(String str : urlStrs) {
                                show.append("第"+i + "-" + cnt + "张图片"+"图片下载完毕" + "\r\n");
                                show.append("正在下载下一张图片..." + "\r\n");
                                cnt++;
                                Thread.currentThread().getId();
                                //使用flag标志位对线程进行控制
                                if(Thread.interrupted()) {
                                    break;
                                }
                                object.notify();
                                object.wait();
                            }
                        }
                    } catch (IOException | InterruptedException e) {
                        System.out.println("界面显示线程已经结束下载");
                    }
                }
            }
        },"t2");

        //开始
        start.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                show.append("注意：<该网站图片下载根据网速而定，可能会很慢>" + "\r\n");
                show.append("请稍等,正在下载图片中..."+"\r\n");

                t1.start();
                t2.start();
            }
        });


        //是否确认关闭
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int a = JOptionPane.showConfirmDialog(UrlWallhaven.this, "确定关闭吗？", "温馨提示",
                        JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION) {
                    //设置线程中断
                    t1.interrupt();
                    t2.interrupt();
                    setDefaultCloseOperation(UrlWallhaven.DISPOSE_ON_CLOSE); // 隐藏窗体,并释放窗口
                }
                else {
                    setDefaultCloseOperation(UrlWallhaven.DO_NOTHING_ON_CLOSE);//对窗口不做任何处理
                }
            }
        });
    }
}

/*class MyThread implements Runnable {
    public boolean ba = false;
    @Override
    public void run() {
        int i =0;
        while(true) {
            if(ba) {
                Download();
            } try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void Download() {
        try {
            String keyword = UrlWallhaven.key.getText();
            keyword = keyword.replace(" ","+");
            Wallhaven.download(keyword);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isBa() {
        return ba;
    }

    public void setBa(boolean ba) {
        this.ba = ba;
    }
}*/



