import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {

    final JPanel topPanel = new JPanel();//顶部的四个文本框容器,JPanel默认流布局
    final Box bottomBox = Box.createHorizontalBox();//除了顶部,下面的盒子,样式为水平

    final Box buttonBox = Box.createVerticalBox();//bottomBox的左边一大块按钮
    final JButton[][] buttons = new JButton[4][5];//左边的若干按钮

    final Box rightBox = Box.createVerticalBox();//bottomBox的右边一大块
    final TextArea textArea = new TextArea(8, 10);//文本域

    final JButton[] buttons2 = new JButton[3];//右下的若干按钮
    final Box buttonBox2 = Box.createVerticalBox();//第二个承载按钮的盒子

    final JTextField[] text = new JTextField[4];

    StringBuilder s1 = new StringBuilder();//左
    String s2;//右
    StringBuilder s3 = new StringBuilder();//中

    String status = "LEFT";//初始化窗口默认位置

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

        rightBox.add(Box.createVerticalStrut(12));//文本域距离下边三个盒子的距离

        loadButtonBox2();//加载第二个承载按钮的盒子
        rightBox.add(buttonBox2);

        bottomBox.add(Box.createHorizontalStrut(2));
        bottomBox.add(rightBox);
        bottomBox.add(Box.createHorizontalStrut(7));
        add(bottomBox);
        setVisible(true);
    }

    public void loadTopPanel() {
        //使用的字体及大小
        final Font font = new Font("", Font.BOLD, 25);
        //上面的四个文本框
        text[0] = new JTextField(8);
        text[1] = new JTextField(4);
        text[2] = new JTextField(8);
        text[3] = new JTextField(14);

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        for (JTextField jTextField : text) {
            jTextField.setEditable(false);
            jTextField.setFont(font);
        }
        text[0].setHorizontalAlignment(JTextField.TRAILING);
        text[1].setHorizontalAlignment(JTextField.CENTER);
        text[2].setHorizontalAlignment(JTextField.LEADING);
        text[3].setHorizontalAlignment(JTextField.CENTER);

        text[0].setPreferredSize(new Dimension(175, 60));
        text[1].setPreferredSize(new Dimension(50, 60));
        text[2].setPreferredSize(new Dimension(175, 60));
        text[3].setPreferredSize(new Dimension(175, 60));

        topPanel.add(Box.createHorizontalStrut(7));
        for (int i = 0; i < 3; i++) {
            topPanel.add(text[i]);
        }
        //因为面板要加间距,所以分开加进去
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(text[3]);
        topPanel.add(Box.createHorizontalStrut(5));
    }

    public void loadButtonBox() {
        final Font font = new Font("微软雅黑", Font.BOLD, 12);
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
                buttons[row][col].addActionListener(this);
                buttonPanel.add(buttons[row][col]); // 将按钮添加到按钮面板中
            }
        }
        buttonBox.add(buttonPanel);
        buttonBox.add(Box.createVerticalStrut(5));//下面的边距
    }

    public void loadButtonBox2() {
        final Font font = new Font("微软雅黑", Font.BOLD, 12);
        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new GridLayout(1, 3, 8, 8));

        buttons2[0] = new JButton("保存");
        buttons2[1] = new JButton("查看");
        buttons2[2] = new JButton("清除");
        for (JButton button : buttons2) {
            button.setPreferredSize(new Dimension(65, 43));
            buttonPanel2.add(button);
            button.setFont(font);
        }
        buttonBox2.add(buttonPanel2);

        buttonBox2.add(Box.createVerticalStrut(5));//右盒子距离下边的宽度
    }

    public void cleanPanel() {
        s1.delete(0, s1.length());
        s3.delete(0, s3.length());
        text[0].setText("");
        text[1].setText("");
        text[2].setText("");
        text[3].setText("");
        status = "LEFT";
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String number = e.getActionCommand();
        System.out.println(number+" "+status);
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
            case ".":
                if ("CLEAN".equals(status)) {
                    cleanPanel(); //先清除再做
                    s1.append(number);
                    text[0].setText(s1.toString());
                    break;
                }
                if ("LEFT".equals(status)) {
                    s1.append(number);
                    text[0].setText(s1.toString());
                    break;
                }
                if ("MID".equals(status)) {//如果状态在中间就做
                    status = "RIGHT";//更改状态,此时在右边数字框
                }
                if ("RIGHT".equals(status)) {{
                    s3.append(number);
                    text[2].setText(s3.toString());
                    break;
                }}
            case "退格":
                //System.out.println(status);
                if ("CLEAN".equals(status)) {
                    break;
                }
                if ("LEFT".equals(status)) {
                    if (!s1.toString().equals("")){
                        s1.delete(s1.length() - 1, s1.length());
                        text[0].setText(s1.toString());
                    }
                    break;
                }
                if ("MID".equals(status)) {
                    s2 = "";
                    text[1].setText(s2);
                    break;
                }
                if ("RIGHT".equals(status)) {
                    if (!s3.toString().equals("")){
                        s3.delete(s3.length() - 1, s3.length());
                        text[2].setText(s3.toString());
                    }
                    break;
                }

            case "/":
            case "*":
            case "-":
            case "+":
                text[1].setText(number);//设置文本框显示
                s2 = number; //设置运算符
                status = "MID";
                break;

            case "=":
                status = "CLEAN";
                float result;
                switch (s2) {
                    case "/":
                        result = Float.parseFloat(s1.toString()) / Float.parseFloat(s3.toString());
                        text[3].setText(String.valueOf(result));
                        break;
                    case "*":
                        result = Float.parseFloat(s1.toString()) * Float.parseFloat(s3.toString());
                        text[3].setText(String.valueOf(result));
                        break;
                    case "+":
                        result = Float.parseFloat(s1.toString()) + Float.parseFloat(s3.toString());
                        text[3].setText(String.valueOf(result));
                        break;
                    case "-":
                        result = Float.parseFloat(s1.toString()) - Float.parseFloat(s3.toString());
                        text[3].setText(String.valueOf(result));
                        break;
                }
                break;

            case "C":
                cleanPanel();
                break;
        }
    }
}
