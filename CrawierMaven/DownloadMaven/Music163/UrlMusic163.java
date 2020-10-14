package DownloadMaven.Music163;

import DownloadMaven.ZhiHu.Chinese;
import DownloadMaven.ZhiHu.UrlWindows;
import org.jsoup.nodes.Document;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;


public class UrlMusic163 extends JFrame {
    static JComboBox<String> t1;

    static JTextArea show = new JTextArea();//文本区域多行显示

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UrlMusic163 jf = new UrlMusic163();
                    jf.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UrlMusic163() {
        String name = "网易云音乐下载";
        setTitle(name);
        setSize(400, 400);
        setDefaultCloseOperation(UrlWindows.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(1,1,1,1));
        contentPane.setLayout(new BorderLayout(0, 0));      // 设置布局
        setContentPane(contentPane);
        JPanel panel=new JPanel(new GridLayout(5, 3,5,10));  // 5行1列的表格布局
        panel.setBorder(new TitledBorder(null,"",TitledBorder.LEADING ,TitledBorder.TOP,null,null));
        contentPane.add(panel,BorderLayout.CENTER);

        // 第一行面板
        JPanel panel_1=new JPanel();
        panel.add(panel_1);

        //歌单下载
        JLabel PlayList = new JLabel("输入歌单id:");
        PlayList.setFont(new Font("黑体",Font.BOLD,15));
        PlayList.setForeground(Color.BLACK);
        panel_1.add(PlayList);

        //歌单输入id
        JTextField PlayListText = new JTextField(15);
        panel_1.add(PlayListText);

        //下载的确认按钮
        JButton Confirm = new JButton("确认");
        panel_1.add(Confirm);


        //确认点击事件
        Confirm.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String SongID = PlayListText.getText();
                    GetPlaylist.Run(SongID);
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //是否确认关闭
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int a = JOptionPane.showConfirmDialog(UrlMusic163.this, "确定关闭吗？", "温馨提示",
                        JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(UrlWindows.DISPOSE_ON_CLOSE); // 关闭
                }
                else {
                    setDefaultCloseOperation(UrlWindows.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        Mythread thread = new Mythread();
        {
            Thread t = new Thread(thread);
            t.start();
        }

    }


    class MJTextField extends JTextField implements MouseListener {

        private JPopupMenu pop = null; // 弹出菜单
        private JMenuItem copy = null, paste = null, cut = null; // 三个功能菜单

        public MJTextField() {
            super();
            init();
        }

        public MJTextField(int columns) {
            super(columns);
            init();
        }

        public MJTextField(String text) {
            super(text);
            init();
        }

        public MJTextField(String text, int columns) {
            super(text, columns);
            init();
        }

        private void init() {
            this.addMouseListener(this);
            pop = new JPopupMenu();
            pop.add(copy = new JMenuItem("复制"));
            pop.add(paste = new JMenuItem("粘贴"));
            pop.add(cut = new JMenuItem("剪切"));
            copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
            paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
            cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
            copy.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    action(e);
                }
            });
            paste.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    action(e);
                }
            });
            cut.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    action(e);
                }
            });
            this.add(pop);
        }

        public void action(ActionEvent e) {
            String str = e.getActionCommand();
            if (str.equals(copy.getText())) { // 复制
                this.copy();
            } else if (str.equals(paste.getText())) { // 粘贴
                this.paste();
            } else if (str.equals(cut.getText())) { // 剪切
                this.cut();
            }
        }

        public JPopupMenu getPop() {
            return pop;
        }

        public void setPop(JPopupMenu pop) {
            this.pop = pop;
        }

        /**
         * 剪切板中是否有文本数据可供粘贴
         *
         * @return true为有文本数据
         */
        public boolean pastable() {
            boolean b = false;
            Clipboard clipboard = this.getToolkit().getSystemClipboard();
            Transferable content = clipboard.getContents(this);
            try {
                if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {
                    b = true;
                }
            } catch (Exception e) {
            }
            return b;
        }

        /**
         * 文本组件中是否具备复制的条件
         *
         * @return true为具备
         */
        public boolean copyable() {
            boolean b = false;
            int start = this.getSelectionStart();
            int end = this.getSelectionEnd();
            if (start != end)
                b = true;
            return b;
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                copy.setEnabled(copyable());
                paste.setEnabled(pastable());
                cut.setEnabled(copyable());
                pop.show(this, e.getX(), e.getY());
            }
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

        class Mythread implements Runnable {
        public boolean ba = false;
        @Override
        public void run() {
            int i = 0;
            while (true) {
                if (ba) {
                    String Mykeyword = Objects.requireNonNull(t1.getSelectedItem()).toString();  //获取当前复选框选中的值
                    //Chinese.xiazai(keyword);
                    //UrlWindows.show.append("正在下载...");
                    Chinese.GetKey();
                    List<String> Myurls = Chinese.obtain(Mykeyword);
                    Chinese.download(Myurls, Mykeyword);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public boolean isBa() {
            return ba;
        }

        public void setBa(boolean ba) {
            this.ba = ba;
        }
    }
}



