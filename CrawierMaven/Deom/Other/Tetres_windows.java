package Deom.Other;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * 主要实现功能：实现一个学生选课的图形界面
 */
public class Tetres_windows extends JFrame {

    // 定义组件
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;//通用容器,面板和画布组件的替代组件
    private JTextField tfName,tfNum,allInfo;//单行文本域
    private JRadioButton rb1,rb2;//单选按钮
    private JCheckBox cb1,cb2,cb3;//能显示文本和图片的复选框
    private JComboBox<String > t1,t2,t3;//带下拉列表的文本框

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    Tetres_windows frame=new Tetres_windows();     // 创建一个窗口
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
    public Tetres_windows(){
        setTitle("选课ing......");                           // 标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // 设置关闭时推出JVM
        setSize(500,400);                       // 设置窗口大小
        setLocationRelativeTo(null);                        // 设置窗体居中
        contentPane=new JPanel();                           // 内容面板
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));      // 设置布局
        setContentPane(contentPane);
        JPanel panel=new JPanel(new GridLayout(5, 1,5,10));  // 5行1列的表格布局
        panel.setBorder(new TitledBorder(null,"",TitledBorder.LEADING ,TitledBorder.TOP,null,null));
        contentPane.add(panel,BorderLayout.CENTER);          // 给panel添加边框

  /*      JPanel panel3 = new JPanel();
        panel.add(panel3);
        JLabel label2 = new JLabel("第二个小面板");
        panel.add(label2);*/

        // 第一行
        JPanel panel_1=new JPanel();
        panel.add(panel_1);
        JLabel label=new JLabel("姓名");
        panel_1.add(label);
        tfName=new JTextField();
        panel_1.add(tfName);
        tfName.setColumns(10);
        JLabel label_2=new JLabel("学号");
        panel_1.add(label_2);
        tfNum=new JTextField();
        tfNum.setColumns(10);
        panel_1.add(tfNum);
        rb1=new JRadioButton("男");
        panel_1.add(rb1);
        rb1.setSelected(true);                               // 设置单选按钮中，默认选中的按钮
        rb2=new JRadioButton("女");
        panel_1.add(rb2);
        ButtonGroup bts=new ButtonGroup();                   // 单选按钮需要加入到同一个ButonGroup中才能生效
        bts.add(rb1);
        bts.add(rb2);
        // 第二行
        JPanel panel_2=new JPanel();
        panel.add(panel_2);
        cb1=new JCheckBox("高等数学");
        panel_2.add(cb1);
        t1=new JComboBox<String >();
        t1.setModel(new DefaultComboBoxModel<String>(new String[]{"林老师","赵老师","孙老师"}));
        panel_2.add(t1);
        // 第三行
        JPanel panel_3=new JPanel();
        panel.add(panel_3);
        cb2=new JCheckBox("世界经济");
        panel_3.add(cb2);
        t2=new JComboBox<String >();
        t2.setModel(new DefaultComboBoxModel<String >(new String[]{"张老师","刘老师"}));
        panel_3.add(t2);
        //第四行
        JPanel panel_4=new JPanel();
        panel.add(panel_4);
        cb3=new JCheckBox("音乐赏析");
        panel_4.add(cb3);
        t3=new JComboBox<String >();
        t3.setModel(new DefaultComboBoxModel<String>(new String[]{"王老师","周老师"}));
        panel_4.add(t3);
        // 第五行
        JPanel panel_5=new JPanel();
        panel.add(panel_5);
        JButton jbOk=new JButton("确定");
        panel_5.add(jbOk);
        JButton jbRest=new JButton("重填");
        panel_5.add(jbRest);
        // 添加选课信息
        JPanel panelSouth=new JPanel();
        contentPane.add(panelSouth,BorderLayout.SOUTH);
        JLabel labe=new JLabel("选课信息");
        labe.setHorizontalAlignment(SwingConstants.LEFT);
        panelSouth.add(labe);
        allInfo=new JTextField();
        allInfo.setColumns(30);
        panelSouth.add(allInfo);
        //添加标题
        JPanel panelNorth=new JPanel();
        contentPane.add(panelNorth,BorderLayout.NORTH);
        JLabel labelTitle=new JLabel("学生选课系统");
        labelTitle.setForeground(Color.DARK_GRAY);
        labelTitle.setFont(new Font("宋体", Font.BOLD, 20));
        panelNorth.add(labelTitle);

        // 给确定按钮添加事件处理代码
        jbOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder info=new StringBuilder();
                String name=tfName.getText();
                String num=tfNum.getText();
                String sex;
                if(rb1.isSelected()){
                    sex="男";
                }else {
                    sex="女";
                }
                info.append(name+num+sex);
                if(cb1.isSelected()){
                    String c=cb1.getText();
                    String t=t1.getSelectedItem().toString();
                    info.append(":").append(c).append(t);
                }
                if(cb2.isSelected()){
                    String c=cb2.getText();
                    String t=t2.getSelectedItem().toString();
                    info.append(",").append(c).append(t);
                }
                if(cb3.isSelected()){
                    String c=cb3.getText();
                    String t=t3.getSelectedItem().toString();
                    info.append(",").append(c).append(t);
                }
                allInfo.setText(info.toString());                     // 把学生信息和选课信息放到文本框中
            }
        });

        // 给重填按钮设置事件处理代码
        jbRest.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                tfName.setText("");
                tfNum.setText("");
                rb1.setSelected(true);
                cb1.setSelected(false);
                t1.setSelectedIndex(0);
                cb2.setSelected(false);
                t2.setSelectedIndex(0);
                allInfo.setText("");
            }
        });
    }

}