import com.melloware.jintellitype.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame=new JFrame("截屏软件");
        jFrame.setLayout(null);
        JPanel jPanel=new JPanel(null);
        //jPanel.setBackground(Color.GRAY);
        jPanel.setBounds(0,0,400,400);
        JTextArea jTextArea=new JTextArea();
        jTextArea.append("\n");
        jTextArea.append("          使用说明\n");
        jTextArea.append("\n");
        jTextArea.append("  开启截屏：快捷键Ctrl+Alt+Q\n");
        jTextArea.append("  取消截屏：快捷键Esc\n");
        jTextArea.append("  退出程序：快捷键Ctrl+Alt+W\n");
        jTextArea.append("  图片地址：桌面Screen目录");
        jTextArea.setBounds(55,30,270,180);
        jTextArea.setFont(new Font("宋体",Font.PLAIN,18));
        jTextArea.setEnabled(false);
        jTextArea.setDisabledTextColor(Color.BLACK);//字体颜色
        jTextArea.setBorder(BorderFactory.createLoweredBevelBorder());
        jPanel.add(jTextArea);
        JButton j1=new JButton("启动");
        j1.setFont(new Font("宋体",Font.PLAIN,20));
        j1.setBounds(65,250,100,50);
        j1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(false);
                JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL+JIntellitype.MOD_ALT , (int) 'Q');//alt+shift+Q为快捷键
                JIntellitype.getInstance().registerHotKey(2, JIntellitype.MOD_CONTROL+JIntellitype.MOD_ALT , (int) 'W');
                JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
                    @Override
                    public void onHotKey(int i) {
                        if(i==1){
                            JIntellitype.getInstance().unregisterHotKey(1);
                            new Screen().getWholeScreenShot();
                            JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL+JIntellitype.MOD_ALT , (int) 'Q');
                        }else if(i==2){
                            JIntellitype.getInstance().unregisterHotKey(1);
                            JIntellitype.getInstance().unregisterHotKey(2);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            JOptionPane.showMessageDialog(null,"  程序已成功退出","提示",JOptionPane.PLAIN_MESSAGE);
                            System.exit(0);
                        }
                    }
                });
            }
        });
        jPanel.add(j1);
        JButton j2=new JButton("退出");
        j2.setFont(new Font("宋体",Font.PLAIN,20));
        j2.setBounds(215,250,100,50);
        j2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(false);
                System.exit(0);
            }
        });
        jPanel.add(j2);
        jFrame.add(jPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setBounds(((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-200, ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-200,400,400);
        jFrame.setVisible(true);
    }
}
