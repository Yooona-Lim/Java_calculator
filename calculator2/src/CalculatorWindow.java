import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author mobeicanyue
 * Create  2021-12-18 10:35
 * Describe:计算器的窗口
 */

public class CalculatorWindow extends JFrame {
    JTextField[] text = new JTextField[4];

    List resultList = new List(11, false);//列表选择框

    //"LEFT" "RIGHT" "MID" "RESULT"
    String status = "LEFT";//初始化窗口默认位置,*******非常重要,看懂这个即可理解本程序的流程控制,通过改变它来决定文本框位置!

    ButtonAction buttonAction = new ButtonAction(this);
    KeyAction keyAction = new KeyAction(this);

    public void basicInit() { //初始化窗体的基本设置
        setTitle("create by mobeiCanyue                                       Calculator");
        setResizable(false);
        setBounds(200, 200, 700, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setIconImage(Toolkit.getDefaultToolkit().getImage(CalculatorWindow.class.getResource("Porsche.png")));
    }

    public CalculatorWindow() {
        basicInit();//初始化

        add(Box.createVerticalStrut(8));

        JPanel topPanel = loadTopPanel(text,keyAction);//加载顶部的那四个显示框,放至topPanel
        add(topPanel);

        add(Box.createVerticalStrut(8));

        final Box bottomBox = Box.createHorizontalBox();//除了顶部,下面的盒子,样式为水平
        bottomBox.add(Box.createHorizontalStrut(7));//左边的间距

        final Font font = new Font("等线", Font.BOLD, 16);

        final JButton[][] buttons = new JButton[4][5];//左边的若干按钮
        Box buttonBox = loadButtonBox(font, buttons, buttonAction,keyAction);//加载左边一大块按钮
        bottomBox.add(buttonBox);

        bottomBox.add(Box.createHorizontalStrut(3)); //左右中间的间隔

        final Box rightBox = Box.createVerticalBox();//bottomBox的右边一大块
        //加入列表选择框
        rightBox.add(resultList);

        rightBox.add(Box.createVerticalStrut(10));//文本域距离下边三个盒子的距离

        final JButton[] buttons2 = new JButton[3];//右下的若干按钮
        Box buttonBox2 = loadButtonBox2(font, buttons2, buttonAction);//加载第二个承载按钮的盒子
        rightBox.add(buttonBox2);

        bottomBox.add(Box.createHorizontalStrut(2));
        bottomBox.add(rightBox);
        bottomBox.add(Box.createHorizontalStrut(7));
        add(bottomBox);
        setVisible(true);
    }

    public JPanel loadTopPanel(JTextField[] text,KeyAction keyAction) {
        final JPanel topPanel = new JPanel();//顶部的四个文本框容器,JPanel默认流布局
        //使用的字体及大小
        final Font font = new Font("", Font.BOLD, 25);
        //上面的四个文本框
        text[0] = new JTextField(8);
        text[1] = new JTextField(5);
        text[2] = new JTextField(8);
        text[3] = new JTextField(13);

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        for (JTextField jTextField : text) {
            jTextField.setEditable(false);
            jTextField.setFont(font);
            jTextField.addKeyListener(keyAction);
        }
        text[0].setHorizontalAlignment(JTextField.TRAILING);
        text[1].setHorizontalAlignment(JTextField.CENTER);
        text[2].setHorizontalAlignment(JTextField.LEADING);
        text[3].setHorizontalAlignment(JTextField.CENTER);

        text[0].setPreferredSize(new Dimension(175, 9));
        text[1].setPreferredSize(new Dimension(50, 9));
        text[2].setPreferredSize(new Dimension(175, 9));
        text[3].setPreferredSize(new Dimension(175, 9));

        topPanel.add(Box.createHorizontalStrut(7));
        for (int i = 0; i < 3; i++) {
            topPanel.add(text[i]);
        }
        //因为面板要加间距,所以分开加进去
        topPanel.add(Box.createHorizontalStrut(8));
        topPanel.add(text[3]);
        topPanel.add(Box.createHorizontalStrut(5));
        return topPanel;
    }

    public Box loadButtonBox(Font font, JButton[][] buttons, ButtonAction buttonAction,KeyAction keyAction) {
        final Box buttonBox = Box.createVerticalBox();//bottomBox的左边一大块按钮

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 5, 5, 10));
        final String[][] names = {
                {"1", "2", "3", "/", "C"}, {"4", "5", "6", "*", "退格"},
                {"7", "8", "9", "-", "1/x"}, {"0", "+/-", ".", "+", "="}
        };
        for (int row = 0; row < names.length; row++) {
            for (int col = 0; col < names[0].length; col++) {
                buttons[row][col] = new JButton(names[row][col]);// 创建按钮
                buttons[row][col].setFont(font);
                buttons[row][col].setPreferredSize(new Dimension(65, 50));
                buttons[row][col].addActionListener(buttonAction);
                buttons[row][col].addKeyListener(keyAction);
                buttonPanel.add(buttons[row][col]); // 将按钮添加到按钮面板中
            }
        }
        buttonBox.add(buttonPanel);
        buttonBox.add(Box.createVerticalStrut(5));//下面的边距
        return buttonBox;
    }

    public Box loadButtonBox2(Font font, JButton[] buttons2, ButtonAction buttonAction) {
        final Box buttonBox2 = Box.createVerticalBox();//第二个承载按钮的盒子

        //final Font font = new Font("微软雅黑", Font.BOLD, 14);
        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new GridLayout(1, 3, 8, 8));

        buttons2[0] = new JButton("保存");
        buttons2[1] = new JButton("查看");
        buttons2[2] = new JButton("清除");
        for (JButton button : buttons2) {
            button.setPreferredSize(new Dimension(55, 43));
            buttonPanel2.add(button);
            button.setFont(font);
            button.addActionListener(buttonAction);
        }
        buttonBox2.add(buttonPanel2);

        buttonBox2.add(Box.createVerticalStrut(5));//右盒子距离下边的宽度
        return buttonBox2;
    }
}
