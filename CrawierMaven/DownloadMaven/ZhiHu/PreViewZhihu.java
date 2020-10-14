package DownloadMaven.ZhiHu;
import DownloadMaven.UrlWallHaven.UrlWallhaven;
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
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PreViewZhihu extends JFrame{

    private JComboBox<String> t1;
    private JPanel contentPane;//通用容器,面板和画布组件的替代组件

    public static void main(String[] args) {
        PreViewZhihu previewWallhaven = new PreViewZhihu();
        previewWallhaven.setVisible(true);
    }

    public PreViewZhihu() {
        setTitle("图片预览");
        setSize(650, 650);                       // 设置窗口大小
        setLocationRelativeTo(null);//                       // 设置窗体居中
        contentPane = new JPanel();                           // 内容面板\
        setDefaultCloseOperation(PreViewZhihu.DISPOSE_ON_CLOSE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));      // 设置布局
        setContentPane(contentPane);
        JPanel panel = new JPanel(new GridLayout(3, 3, 5, 10));  // 5行1列的表格布局
        panel.setBorder(new TitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, null, null));
        contentPane.add(panel, BorderLayout.CENTER);

        //顶部信息栏
        JPanel top = new JPanel(new FlowLayout());
        this.add(top,BorderLayout.NORTH);

        //顶部类型选择
        JLabel LabelType = new JLabel("类型:");
        LabelType.setFont(new Font("黑体", Font.BOLD, 15));
        LabelType.setForeground(Color.BLACK);
        top.add(LabelType);
        //复选框
        HashMap<String,String> map = new HashMap<>();
        map = Zhihu.GetMap();
        Set<String> set = map.keySet();
        t1 = new JComboBox<>();
        t1.setModel(new DefaultComboBoxModel<String>(set.toArray(new String[0])));
        //t1.setModel(new DefaultComboBoxModel<String>(new String[]{"1","2"}));
        top.add(t1);

        JPanel drop = new JPanel();
        panel.add(drop,BorderLayout.SOUTH);

        JButton ok = new JButton("开始加载");
        top.add(ok);

        //是否确认关闭
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int a = JOptionPane.showConfirmDialog(PreViewZhihu.this, "确定关闭吗？", "温馨提示",
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

                //预览显示图片
                Thread T1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        }
                    try {
                            panel.removeAll();
                            List<String> listurls = Chinese.obtain(t1.getSelectedItem().toString());
                            for (int i = 0; i < 9; i++) {
                                //sleep(5000);
                                String path = listurls.get(i);
                                System.out.println("Get Image from " + path);
                                URL url = new URL(path);
                                BufferedImage image = ImageIO.read(url);
                                System.out.println("第"+(i+1)+"张图片正在Load image into frame...");
                                ImageIcon imageIcon = new ImageIcon(image);
                                imageIcon.setImage(imageIcon.getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT));
                                JLabel label = new JLabel(imageIcon);
                                panel.add(label);
                                //更新界面进行刷新,实现每张图片能够实时添加
                                panel.updateUI();
                            }
                            System.out.println("显示完毕!");
                        } catch (Exception exp) {
                            exp.printStackTrace();
                        }
                    }
                });

                T1.start();

            }
        });

    }
}


