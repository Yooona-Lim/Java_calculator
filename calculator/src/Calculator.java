import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {

    JPanel topPanel = new JPanel();//顶部的四个文本框容器,JPanel默认流布局
    Box bottomBox = Box.createHorizontalBox();//除了顶部,下面的盒子,样式为水平

    Box buttonBox = Box.createVerticalBox();//bottomBox的左边一大块按钮
    JButton[][] buttons = new JButton[4][5];//左边的若干按钮

    Box rightBox = Box.createVerticalBox();//bottomBox的右边一大块
    TextArea textArea = new TextArea(9, 10);//文本域

    JButton[] buttons2 = new JButton[3];//右下的若干按钮
    Box buttonBox2 = Box.createVerticalBox();//第二个承载按钮的盒子

    JTextField[] text = new JTextField[4];

    StringBuilder s = new StringBuilder();
    public static void main(String[] args) {
        new Calculator();
    }

    public Calculator() {
        setTitle("计算器");
        setResizable(false);
        setBounds(100, 100, 550, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(8));

        loadTopPanel();//加载顶部的那四个显示框,放至topPanel
        add(topPanel);

        add(Box.createVerticalStrut(8));

        bottomBox.add(Box.createHorizontalStrut(7));//左边的间距

        loadButtonBox();//加载左边一大块按钮
        bottomBox.add(buttonBox);

        bottomBox.add(Box.createHorizontalStrut(3)); //左右中间的间隔

        rightBox.add(textArea);

        rightBox.add(Box.createVerticalStrut(4));//文本域距离下边三个盒子的距离

        loadButtonBox2();//加载第二个承载按钮的盒子
        rightBox.add(buttonBox2);

        bottomBox.add(Box.createHorizontalStrut(2));
        bottomBox.add(rightBox);
        bottomBox.add(Box.createHorizontalStrut(7));
        add(bottomBox);
        setVisible(true);
    }

    public void loadTopPanel() {
        Font font = new Font("JetBrains Mono", Font.BOLD,25);
        //上面的四个文本框
        text[0] = new JTextField(8);
        text[1] = new JTextField(4);
        text[2] = new JTextField(8);
        text[3] = new JTextField(14);

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        for (JTextField jTextField : text) {
            jTextField.setEditable(false);
        }

        text[0].setPreferredSize(new Dimension(175, 60));
        text[1].setPreferredSize(new Dimension(50, 60));
        text[2].setPreferredSize(new Dimension(175, 60));
        text[3].setPreferredSize(new Dimension(175, 60));

        text[0].setHorizontalAlignment(JTextField.TRAILING);
        text[0].setFont(font);
        text[0].addActionListener(this);

        topPanel.add(Box.createHorizontalStrut(7));
        for (int i = 0; i < 3; i++) {
            topPanel.add(text[i]);
        }
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(text[3]);
        topPanel.add(Box.createHorizontalStrut(5));
    }

    public void loadButtonBox() {
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 5, 5, 10));

        //前面为数字,后面为运算
        String[][] names = {{"1", "2", "3", "4", "5"}, {"6", "7", "8", "9", "0"},
                {"/", "C", "*", "退格", "-"}, {"1/x", "+/-", ".", "+", "="}};

        for (int row = 0; row < names.length; row++) {
            for (int col = 0; col < names[0].length; col++) {
                buttons[row][col] = new JButton(names[row][col]);// 创建按钮
                buttons[row][col].setPreferredSize(new Dimension(65, 50));
                buttons[row][col].addActionListener(this);
                buttonPanel.add(buttons[row][col]); // 将按钮添加到按钮面板中
            }
        }
        buttonBox.add(buttonPanel);
        buttonBox.add(Box.createVerticalStrut(5));//下面的边距
    }

    public void loadButtonBox2() {
        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new GridLayout(1, 3, 8, 8));

        buttons2[0] = new JButton("保存");
        buttons2[1] = new JButton("查看");
        buttons2[2] = new JButton("清除");
        for (JButton button : buttons2) {
            button.setPreferredSize(new Dimension(65, 43));
            buttonPanel2.add(button);
        }
        buttonBox2.add(buttonPanel2);

        buttonBox2.add(Box.createVerticalStrut(5));//右盒子距离下边的宽度
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String number = e.getActionCommand();
        switch (number) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                s.append(number);
                text[0].setText(s.toString());
                break;
        }
    }
}
