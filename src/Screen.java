import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screen {
    private String svaeDirPath;//文件目录路径
    private final String format;//图片格式
    private int x_start, y_start, x_end, y_end;
    private boolean isRectageVisual = false;
    private final Date nowTime;//当前时间
    private final SimpleDateFormat ft;//时间格式
    private final int screenWidth;
    private final int screenHeight;

    Screen(){
        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        svaeDirPath = desktopPath + "/Screen";
        File dir = new File(svaeDirPath);
        if (!dir.exists())
            dir.mkdir();
        format = "png";
        nowTime = new Date();
        ft = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screensize.getWidth();
        screenHeight = (int) screensize.getHeight();
    }
    public void getWholeScreenShot() {
        BufferedImage bufferedImage;
        try {
            Robot robot = new Robot();
            bufferedImage = robot.createScreenCapture(new Rectangle(0, 0, screenWidth, screenHeight));
            String absolutePath = svaeDirPath + "/" + ft.format(nowTime) + "." + format;
            File saveFile = new File(absolutePath);
            ImageIO.write(bufferedImage, format, saveFile);
            setVisual(absolutePath, bufferedImage);
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }
    private void setVisual(String absolutePath, BufferedImage bufferedImage) {
        ImageIcon icon = new ImageIcon(absolutePath);
        Image image = icon.getImage();
        JFrame jframe = new JFrame();
        JLabel jLabel = new JLabel(icon);
        JPanel jPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.drawImage(image, 0, 0, null);
                g2.setStroke(new BasicStroke(2.0f));
                g2.setColor(Color.BLUE);
                if (isRectageVisual) {
                    if(x_start>x_end&&y_start<y_end){
                        g.drawRect(x_end, y_start, x_start - x_end, y_end - y_start);
                    }else if (x_start>x_end&&y_start>y_end) {
                        g.drawRect(x_end, y_end, x_start - x_end, y_start - y_end);
                    }else if (x_start<x_end&&y_start>y_end){
                        g.drawRect(x_start,y_end,x_end-x_start,y_start-y_end);
                    }else{
                        g.drawRect(x_start, y_start, x_end - x_start, y_end - y_start);
                    }
                }
            }
        };
        jframe.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                x_start = e.getX();
                y_start = e.getY();
                x_end = e.getX();
                y_end = e.getY();
                isRectageVisual = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                x_end = e.getX();
                y_end = e.getY();
                isRectageVisual = true;
                BufferedImage cutedImage=null;
                if(x_start>x_end&&y_start<y_end){
                    cutedImage = bufferedImage.getSubimage(x_end, y_start, x_start - x_end, y_end - y_start);
                }else if(x_start>x_end&&y_start>y_end) {
                    cutedImage = bufferedImage.getSubimage(x_end, y_end, x_start - x_end, y_start - y_end);
                }else if (x_start<x_end&&y_start>y_end){
                    cutedImage=bufferedImage.getSubimage(x_start,y_end,x_end-x_start,y_start-y_end);
                }else {
                    cutedImage = bufferedImage.getSubimage(x_start, y_start, x_end - x_start, y_end - y_start);
                }
                String absolutePath = svaeDirPath + "/" + ft.format(nowTime) + "." + format;
                File saveFile = new File(absolutePath);
                try {
                    ImageIO.write(cutedImage, format, saveFile);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                jframe.setVisible(false);
                JOptionPane.showMessageDialog(null,"截图成功!","提示",JOptionPane.PLAIN_MESSAGE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        jframe.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    jframe.setVisible(false);
                }
            }
        });
        jframe.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x_end = e.getX();
                y_end = e.getY();
                jPanel.repaint();
                jPanel.paintImmediately(new Rectangle());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
        jLabel.setOpaque(false);
        jframe.add(jLabel);
        jframe.getContentPane().add(jPanel);
        jframe.setResizable(false);
        jframe.setSize(screenWidth, screenHeight);
        jframe.setUndecorated(true);
        jframe.setCursor(Cursor.CROSSHAIR_CURSOR);
        jframe.setVisible(true);
    }
}
