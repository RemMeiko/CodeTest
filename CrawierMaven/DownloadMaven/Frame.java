package DownloadMaven;

import DownloadMaven.Music163.UrlMusic163;
import DownloadMaven.ZhiHu.PreViewZhihu;
import DownloadMaven.UrlWallHaven.PreviewWallhaven;
import DownloadMaven.UrlWallHaven.UrlWallhaven;
import DownloadMaven.ZhiHu.UrlWindows;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
/**
 * 主要实现功能：实现一个学生选课的图形界面
 */
public class Frame extends JFrame {
    //重写Jpanel
    private URL url  = getClass().getClassLoader().getResource("wallhaven壁纸\\rem\\1-1.jpg");
    String ImageUrl = "https://pic4.zhimg.com/v2-9ae5ef3e0a4c12c49899f53c83e66ca0_r.jpg?source=1940ef5c";
    // 定义组件
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;//通用容器,面板和画布组件的替代组件
    private JTextField tfName,tfNum,allInfo;//单行文本域
    private JRadioButton rb1,rb2;//单选按钮
    private JCheckBox cb1,cb2,cb3;//能显示文本和图片的复选框

    public static void Begain() {
        // TODO Auto-generated method stub
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    Frame frame=new Frame();     // 创建一个窗口
                    frame.setVisible(true);                                    // 让该窗口实例可见
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
//      CourseSelectionFrame frame=new CourseSelectionFrame();
//      frame.setVisible(true);
    }

    /**
     * 窗口属性的设置，内部组件的初始化
     */
    public Frame() {
        setTitle("图片下载");                           // 标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // 设置关闭时推出JVM
        setSize(600, 400);                       // 设置窗口大小
        setLocationRelativeTo(null); //                       // 设置窗体居中
        contentPane = new JPanel();                           // 内容面板
        contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
        contentPane.setLayout(new BorderLayout(0, 0));      // 设置布局
        setContentPane(contentPane);
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 10));  // 5行1列的表格布局
        panel.setBorder(new TitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, null, null));
        contentPane.add(panel, BorderLayout.CENTER);          // 给panel添加边框

        //第一个面板
        MyJPanel panel_1 = new MyJPanel(new BorderLayout());
        //panel_1.setLayout(null);//设置空布局,即绝对布局
        panel.add(panel_1);
        JPanel drop1 = new JPanel(new FlowLayout());
        drop1.setBackground(null);
        drop1.setOpaque(false);//背景设置透明
        panel_1.add(drop1,BorderLayout.SOUTH);

        JLabel labelUrl = new JLabel("知乎壁纸");
        //labelUrl.setBounds(10,10,5,10);
        //labelUrl.setHorizontalAlignment(SwingConstants.CENTER);
        labelUrl.setFont(new Font("宋体", Font.PLAIN, 20));
        labelUrl.setForeground(Color.PINK);
        panel_1.add(labelUrl,BorderLayout.NORTH);

        JButton start = new JButton("下载");
        drop1.add(start);

        JButton preview = new JButton("预览");
        drop1.add(preview);

        //第二个面板
        MyJPanel panel_2 = new MyJPanel(new BorderLayout());
        panel_2.setBackground(Color.darkGray);
        panel.add(panel_2);
        JLabel labelBiYing = new JLabel("Wallhaven壁纸");
        labelBiYing.setFont(new Font("宋体", Font.PLAIN, 20));
        labelBiYing.setForeground(Color.PINK);
        panel_2.add(labelBiYing,BorderLayout.NORTH);

        JPanel drop2 = new JPanel(new FlowLayout());
        drop2.setBackground(null);
        drop2.setOpaque(false);//背景设置透明
        panel_2.add(drop2,BorderLayout.SOUTH);
        JButton start2 = new JButton("下载");
        drop2.add(start2);

        JButton Preview = new JButton("预览");
        drop2.add(Preview);

        //第三个面板网易云音乐
        MyJPanel panel_3 = new MyJPanel(new BorderLayout());
        panel_3.setBackground(Color.darkGray);
        panel.add(panel_3);
        JLabel labelMusic163 = new JLabel("网易云音乐");
        labelMusic163.setFont(new Font("宋体", Font.PLAIN, 20));
        labelMusic163.setForeground(Color.PINK);
        panel_3.add(labelMusic163,BorderLayout.NORTH);

        //下载按钮
        JPanel drop3 = new JPanel(new FlowLayout());
        drop3.setBackground(null);
        drop3.setOpaque(false);//背景设置透明
        panel_3.add(drop3,BorderLayout.SOUTH);
        JButton start3 = new JButton("下载");
        drop3.add(start3);

        JButton Preview3 = new JButton("预览");
        drop3.add(Preview3);

        //第四个面板QQ音乐
        MyJPanel panel_4 = new MyJPanel(new BorderLayout());
        panel_4.setBackground(Color.darkGray);
        panel.add(panel_4);
        JLabel labelQqMusic = new JLabel("还没想好放啥");
        labelQqMusic.setFont(new Font("宋体", Font.PLAIN, 20));
        labelQqMusic.setForeground(Color.PINK);
        panel_4.add(labelQqMusic,BorderLayout.NORTH);

        JPanel drop4 = new JPanel(new FlowLayout());
        drop4.setBackground(null);
        drop4.setOpaque(false);//背景设置透明
        panel_4.add(drop4,BorderLayout.SOUTH);
        JButton start4 = new JButton("未定");
        drop4.add(start4);

        JButton Preview4 = new JButton("未定");
        drop4.add(Preview4);

     /*   MyJPanel jPanel_3 = new MyJPanel();
        panel.add(jPanel_3);


        MyJPanel jPanel_4 = new MyJPanel();
        panel.add(jPanel_4);

        MyJPanel jPanel_5 = new MyJPanel();
        panel.add(jPanel_5);*/

        //知乎开始下载按钮
        start.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UrlWindows jf=new UrlWindows();     // 创建一个窗口
                jf.setVisible(true);// 让该窗口实例可见
            }
        });

        //Wallhaven开始下载按钮
        start2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UrlWallhaven jf=new UrlWallhaven();     // 创建一个窗口
                jf.setVisible(true);// 让该窗口实例可见
            }
        });

        //网易云开始下载按钮
        start3.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UrlMusic163 jf = new UrlMusic163();
                jf.setVisible(true);
            }
        });

        //必应图片预览
        preview.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PreViewZhihu preViewZhihu = new PreViewZhihu();
                preViewZhihu.setVisible(true);
            }
        });



        //Wallhaven网站的图片
        Preview.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PreviewWallhaven previewWallhaven = new PreviewWallhaven();
                previewWallhaven.setVisible(true);
            }
        });
    }

    //暂停按钮


    //继承JPanel类设置背景图片
    class MyJPanel extends JPanel {
        private static final long serialVersionUID = -8251916094895167058L;

        public static final String TILED = "Tiled";

        private Image image = (Image) new ImageIcon("E:\\workspace-file\\Intellij IDEA\\Again\\CrawierMaven\\src\\main\\resourcs\\wallhaven壁纸\\starry+sky\\1-11.jpg").getImage();

        public MyJPanel(LayoutManager layout, boolean isDoubleBuffered) {
            this.setLayout(layout);
            this.setDoubleBuffered(isDoubleBuffered);
            //this.setUIProperty("opaque", Boolean.TRUE);
            this.updateUI();
        }

        public MyJPanel(LayoutManager layout) {
            this(layout, true);
        }

        public MyJPanel(boolean isDoubleBuffered) {
            this(new FlowLayout(), isDoubleBuffered);
        }

        public MyJPanel() {
            this(true);
        }

        // 固定背景图片，允许这个JPanel可以在图片上添加其他组件
        protected void paintComponent(Graphics g) {
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        }

    }
}

