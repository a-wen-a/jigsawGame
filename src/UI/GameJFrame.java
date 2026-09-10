package UI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    // 游戏的主界面
    // 和游戏相关的逻辑都写在这个类中
    public GameJFrame() {
        // 初始化界面
        initJFrame();

        // 初始化菜单
        initJMenuBar();

        // 初始化并打乱数据

        initData(twoDeminArr);

        // 初始化图片
        initImage(twoDeminArr, picturesPath);

        this.setVisible(true);
    }

    // 操作步数计数器
    int count = 0;

    // 定义判断胜利的数组
    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}};

    // 随机一张图片
    Random r = new Random();

    int animalPicture = 8;
    int sportPicture = 10;
    String animalPath = "image/animal/animal"+ (r.nextInt(animalPicture) + 1) +"/";
    String sportPath = "image/sport/sport" + (r.nextInt(sportPicture) + 1) + "/";
    // 开始先在动物和运动中选任选一张图片
    String picturesPath = animalPath;

    // 和图片对应的数组
    int[][] twoDeminArr = new int[4][4];

    // 空白所在的位置
    int x = 0;
    int y = 0;

    // 菜单条目
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem animalPictureItem = new JMenuItem("动物");
    JMenuItem sportPictureItem = new JMenuItem("运动");

    private void initData(int[][] twoDeminArr) {
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8,
                9, 10, 11, 12, 13, 14, 15};
        Random r = new Random();
        // 定义二维数组方便放入

        for (int i = 0; i < 16; i++) {
            int index = r.nextInt(16);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }
        // 将一维数组中的数据添加到二维数组中
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tempArr[count] == 0) {
                    x = i;
                    y = j;
                }
                twoDeminArr[i][j] = tempArr[count];


                count++;
            }
        }
    }

    private void initImage(int[][] twoDeminArr, String picturePath) {

        // 删除已经出现的所有图片
        this.getContentPane().removeAll();

        // 判断是否胜利，是则显示胜利图片
        if (victory()) {
            JLabel winJLabel = new JLabel(new ImageIcon("F:\\Programing_Java\\jigsawGame\\image\\win.png"));
            winJLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winJLabel);
        }

        // 显示计数器
        JLabel stepJLabel = new JLabel("步数：" + count);
        stepJLabel.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepJLabel);

        int number = 1;
        // 外层循环换列
        for (int i = 0; i < 4; i++) {
            // 内层循环添加每行中的图片
            for (int j = 0; j < 4; j++) {
                // 推荐写相对路径
                // 绝对路径的写法
                // ImageIcon icon = new ImageIcon("F:\\Programing_Java\\jigsawGame\\image\\animal\\animal4\\"+ twoDeminArr[i][j] +".jpg");
                ImageIcon icon = new ImageIcon(picturePath + twoDeminArr[i][j] + ".jpg");
                JLabel jLabel = new JLabel(icon);
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                //this.add(jLabel);
                // 给图片添加边框
                jLabel.setBorder(new BevelBorder(0));
                this.getContentPane().add(jLabel);
                number++;
            }
        }

        // 先加载的图片在上方，后加载的图片在下方
        // 设置背景图片
        JLabel background = new JLabel(new ImageIcon("F:\\Programing_Java\\jigsawGame\\image\\background.png"));
        background.setBounds(40, 40, 508, 560);

        // 将背景图片添加到容器中
        this.getContentPane().add(background);

        // 刷新图片
        this.getContentPane().repaint();

    }

    private void initJFrame() {
        // 设置界面大小
        this.setSize(603, 680);
        // 设置标题
        this.setTitle("拼图单机版 v1.0");
        // 设置一直在顶部
        this.setAlwaysOnTop(true);
        // 设置在中间打开
        this.setLocationRelativeTo(null);
        // 设置开关
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 取消默认的居中位置，只有取消了才会按照XY轴的方式添加组件
        this.setLayout(null);

        // 添加键盘监听事件
        this.addKeyListener(this);

    }

    private void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();

        JMenu jMenu = new JMenu("菜单");

        JMenu changePictureItem = new JMenu("更换图片");

        jMenu.add(changePictureItem);
        jMenu.add(replayItem);
        jMenu.add(reLoginItem);
        jMenu.add(closeItem);

        changePictureItem.add(animalPictureItem);
        changePictureItem.add(sportPictureItem);

        // 给条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        animalPictureItem.addActionListener(this);
        sportPictureItem.addActionListener(this);

        jMenuBar.add(jMenu);

        // 给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (victory()) {
            return;
        }
        int keyCode = e.getKeyCode();
        if (keyCode == 65) {
            // 按a获取完整图片
            // 先把界面中所有的图片删除干净
            this.getContentPane().removeAll();
            // 加载完整图片
            JLabel all = new JLabel(new ImageIcon(animalPath + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);
            // 加载背景图片
            JLabel background = new JLabel(new ImageIcon("F:\\Programing_Java\\jigsawGame\\image\\background.png"));
            background.setBounds(40, 40, 508, 560);

            // 将背景图片添加到容器中
            this.getContentPane().add(background);

            // 刷新界面
            this.getContentPane().repaint();
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (victory()) {
            return;
        }
        int keyCode = e.getKeyCode();
        if (keyCode == 37) {
            count++;
            System.out.println("向左移动");
            if (y < 3 && y >= 0) {
                twoDeminArr[x][y] = twoDeminArr[x][y + 1];
                twoDeminArr[x][y + 1] = 0;
                y++;
                initImage(twoDeminArr, picturesPath);
            }
        } else if (keyCode == 38) {
            count++;
            System.out.println("向上移动");
            if (x < 3 && x >= 0) {
                twoDeminArr[x][y] = twoDeminArr[x + 1][y];
                twoDeminArr[x + 1][y] = 0;
                x++;
                initImage(twoDeminArr, picturesPath);
            }
        } else if (keyCode == 39) {
            count++;
            System.out.println("向右移动");
            if (y <= 3 && y > 0) {
                twoDeminArr[x][y] = twoDeminArr[x][y - 1];
                twoDeminArr[x][y - 1] = 0;
                y--;
                initImage(twoDeminArr, picturesPath);
            }
        } else if (keyCode == 40) {
            count++;
            System.out.println("向下移动");
            if (x <= 3 && x > 0) {
                twoDeminArr[x][y] = twoDeminArr[x - 1][y];
                twoDeminArr[x - 1][y] = 0;
                x--;
                initImage(twoDeminArr, picturesPath);
            }
        } else if (keyCode == 65) {
            initImage(twoDeminArr, picturesPath);
        } else if (keyCode == 87) {
            int[][] end = {
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0}
            };
            twoDeminArr = end;
            initImage(twoDeminArr, picturesPath);
        }
    }

    public boolean victory() {
        // 循环遍历两个数组并进行比较
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (win[i][j] != twoDeminArr[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == replayItem) {
            System.out.println("重新游戏");
            // 先将计数器清零
            count = 0;
            // 重新初始化数据
            initData(twoDeminArr);
            // 重新加载图片
            initImage(twoDeminArr, picturesPath);
        } else if (source == reLoginItem) {
            System.out.println("重新登录");
            // 关闭当前界面
            this.setVisible(false);
            // 打开登录界面
            new LogInJFrame();
        } else if (source == closeItem) {
            System.out.println("关闭游戏");
            System.exit(0);
        } else if(source == animalPictureItem) {
            // 先重置一下animalPath的路径
            animalPath = "image/animal/animal"+ (r.nextInt(animalPicture) + 1) +"/";
            picturesPath = animalPath;
            // 再将该路径给初始化图片界面
            initData(twoDeminArr);
            initImage(twoDeminArr, picturesPath);
        } else if(source == sportPictureItem) {
            // 先重置一下sportPath的路径
            sportPath = "image/sport/sport" + (r.nextInt(sportPicture) + 1) + "/";
            picturesPath = sportPath;
            // 再以该路径初始化界面
            initData(twoDeminArr);
            initImage(twoDeminArr, picturesPath);
        }
    }
}
