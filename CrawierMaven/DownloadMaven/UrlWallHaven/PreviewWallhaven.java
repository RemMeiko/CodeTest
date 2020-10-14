package DownloadMaven.UrlWallHaven;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

public class PreviewWallhaven extends JFrame{

    private JPanel contentPane;//通用容器,面板和画布组件的替代组件

    public static void main(String[] args) {
        PreviewWallhaven previewWallhaven = new PreviewWallhaven();
        previewWallhaven.setVisible(true);
    }

    public PreviewWallhaven() {
        setTitle("图片预览");
        setSize(650, 650);                       // 设置窗口大小
        setLocationRelativeTo(null); //                       // 设置窗体居中
        contentPane = new JPanel();                           // 内容面板
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));      // 设置布局
        setContentPane(contentPane);
        JPanel panel = new JPanel(new GridLayout(3, 3, 5, 10));  // 3行3列的表格布局
        panel.setBorder(new TitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, null, null));
        contentPane.add(panel, BorderLayout.CENTER);

        //顶部信息栏
        JPanel top = new JPanel(new FlowLayout());
        this.add(top,BorderLayout.NORTH);

        //label
        JLabel labelkey = new JLabel("请输入关键字:");
        labelkey.setFont(new Font("宋体",Font.PLAIN,20));
        //labelkey.setForeground(Color.PINK);
        top.add(labelkey);

        JTextField key =new JTextField(20);
        top.add(key);

        JButton ok = new JButton("确定");
        top.add(ok);

        //是否确认关闭
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int a = JOptionPane.showConfirmDialog(PreviewWallhaven.this, "确定关闭吗？", "温馨提示",
                        JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(UrlWallhaven.DISPOSE_ON_CLOSE); // 关闭
                }
                else {
                    setDefaultCloseOperation(UrlWallhaven.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        ok.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        //加载图片进行预览显示
                       Thread T1 =  new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                                }
                                try {
                                    if(key.getText() == null) {
                                        setDefaultCloseOperation(PreviewWallhaven.DO_NOTHING_ON_CLOSE);
                                    }
                                    else {
                                        panel.removeAll();
                                        String keyword = key.getText();
                                        List<String> listurls = Wallhaven.GetPageUrls(keyword,1);
                                        for (int i = 0; i < 9; i++) {
                                            //sleep(5000);
                                            String path = Wallhaven.GetEndUrl(listurls.get(i));
                                            System.out.println("Get Image from " + path);
                                            URL url = new URL(path);
                                            BufferedImage image = ImageIO.read(url);
                                            System.out.println("第"+(i+1)+"张图片正在Load image into frame...");
                                            ImageIcon imageIcon = new ImageIcon(image);
                                            imageIcon.setImage(imageIcon.getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT));
                                            JLabel label = new JLabel(imageIcon);
                                            label.setVisible(true);
                                            panel.add(label);
                                            panel.updateUI();
                                        }
                                        key.setText(keyword+"加载完毕");
                                    }
                                    System.out.println("显示完毕!");
                                } catch (Exception exp) {
                                    exp.printStackTrace();
                                }
                            }
                        });

                       T1.start();

   /*                 String path = "https://w.wallhaven.cc/full/96/wallhaven-96qy3w.jpg";
                    System.out.println("Get Image from " + path);
                    URL url = new URL(path);
                    BufferedImage image = ImageIO.read(url);
                    System.out.println("Load image into frame...");
                    ImageIcon imageIcon = new ImageIcon(image);
                    imageIcon.setImage(imageIcon.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT));
                    JLabel label = new JLabel(imageIcon);

                    //https://w.wallhaven.cc/full/96/wallhaven-96qy3w.jpg

                    String path2 = "https://w.wallhaven.cc/full/2e/wallhaven-2em38y.jpg";
                    System.out.println("Get Image from " + path2);
                    URL url2 = new URL(path2);
                    BufferedImage image2 = ImageIO.read(url2);
                    System.out.println("Load image into frame...");
                    ImageIcon imageIcon2 = new ImageIcon(image2);
                    imageIcon2.setImage(imageIcon2.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT));
                    JLabel label2 = new JLabel(imageIcon2);
*/
                  /*  panel.add(label);
                    panel.add(label2);*/
                    }
                });
            }
        });

    }
}

/*
public class TestURLImage {

    public static void main(String[] args) {
        new TestURLImage();
    }

    public TestURLImage() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                try {
                    String path = "https://w.wallhaven.cc/full/96/wallhaven-96qy3w.jpg";
                    System.out.println("Get Image from " + path);
                    URL url = new URL(path);
                    BufferedImage image = ImageIO.read(url);
                    System.out.println("Load image into frame...");
                    JLabel label = new JLabel(new ImageIcon(image));
                    JFrame f = new JFrame();
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.getContentPane().add(label);
                    f.pack();
                    f.setLocation(200, 200);
                    f.setVisible(true);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }

            }
        });
    }
}*/

