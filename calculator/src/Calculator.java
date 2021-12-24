import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mobeicanyue
 * Create  2021-12-18 10:35
 * Describe:
 */

public class Calculator extends JFrame implements ActionListener {

    final JTextField[] text = new JTextField[4];

    StringBuilder s1 = new StringBuilder();//左
    String s2;//右
    StringBuilder s3 = new StringBuilder();//中

    final List resultList = new List(11, true);//列表选择框

    //"LEFT" "RIGHT" "MID" "RESULT"
    static String status = "LEFT";//初始化窗口默认位置

    public static void main(String[] args) {
        new Calculator();
    }

    public Calculator() {

        setTitle("计算器");
        setResizable(false);
        setBounds(100, 100, 700, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(8));

        JPanel topPanel = loadTopPanel();//加载顶部的那四个显示框,放至topPanel
        add(topPanel);

        add(Box.createVerticalStrut(8));

        final Box bottomBox = Box.createHorizontalBox();//除了顶部,下面的盒子,样式为水平
        bottomBox.add(Box.createHorizontalStrut(7));//左边的间距

        final Font font = new Font("等线", Font.BOLD, 16);

        final JButton[][] buttons = new JButton[4][5];//左边的若干按钮
        Box buttonBox = loadButtonBox(font, buttons);//加载左边一大块按钮
        bottomBox.add(buttonBox);

        bottomBox.add(Box.createHorizontalStrut(3)); //左右中间的间隔

        final Box rightBox = Box.createVerticalBox();//bottomBox的右边一大块
        //加入列表选择框
        rightBox.add(resultList);

        rightBox.add(Box.createVerticalStrut(10));//文本域距离下边三个盒子的距离

        final JButton[] buttons2 = new JButton[3];//右下的若干按钮
        Box buttonBox2 = loadButtonBox2(font, buttons2);//加载第二个承载按钮的盒子
        rightBox.add(buttonBox2);

        bottomBox.add(Box.createHorizontalStrut(2));
        bottomBox.add(rightBox);
        bottomBox.add(Box.createHorizontalStrut(7));
        add(bottomBox);
        setVisible(true);
    }

    public JPanel loadTopPanel() {
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

    public Box loadButtonBox(Font font, JButton[][] buttons) {
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
                buttons[row][col].addActionListener(this);
                buttonPanel.add(buttons[row][col]); // 将按钮添加到按钮面板中
            }
        }
        buttonBox.add(buttonPanel);
        buttonBox.add(Box.createVerticalStrut(5));//下面的边距
        return buttonBox;
    }

    public Box loadButtonBox2(Font font, JButton[] buttons2) {
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
            button.addActionListener(this);
        }
        buttonBox2.add(buttonPanel2);

        buttonBox2.add(Box.createVerticalStrut(5));//右盒子距离下边的宽度
        return buttonBox2;
    }

    public void changeValue(String tabValue, StringBuilder s, JTextField jTextField) {
        s.append(tabValue);
        jTextField.setText(s.toString());
    }

    public void cleanPanel(StringBuilder s1, StringBuilder s3, JTextField[] texts) {
        s1.delete(0, s1.length());
        s3.delete(0, s3.length());
        for (JTextField jTextField : texts) {
            jTextField.setText("");
        }
        status = "LEFT";
    }

    public float calculate(StringBuilder s1, String s2, StringBuilder s3, JTextField textField) {
        float result = 0;
        switch (s2) {
            case "/":
                result = Float.parseFloat(s1.toString()) / Float.parseFloat(s3.toString());
                textField.setText(String.valueOf(result));
                break;
            case "*":
                result = Float.parseFloat(s1.toString()) * Float.parseFloat(s3.toString());
                textField.setText(String.valueOf(result));
                break;
            case "+":
                result = Float.parseFloat(s1.toString()) + Float.parseFloat(s3.toString());
                textField.setText(String.valueOf(result));
                break;
            case "-":
                result = Float.parseFloat(s1.toString()) - Float.parseFloat(s3.toString());
                textField.setText(String.valueOf(result));
                break;
        }
        return result;
    }

    public void back(StringBuilder s, JTextField textField) {
        if (s.length() != 0) {
            s.delete(s.length() - 1, s.length());
            textField.setText(s.toString());
        }
    }

    public void addList(StringBuilder s1, String s2, StringBuilder s3, float result, List results) {
        String temp = s1.toString() + " " + s2 + " " + s3.toString() + " = " + result;
        results.add(temp);
    }

    public void positive_negative(StringBuilder sb, JTextField textField) {
        if (sb.toString().equals("0")) {//为0则不变正负
            return;
        }
        if (String.valueOf(sb.charAt(0)).equals("-")) {
            sb.deleteCharAt(0);
        } else {
            sb.insert(0, "-");
        }
        textField.setText(sb.toString());
    }

    public void saveList(List resultList) {
        Date date = new Date();//获取当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("'Date' yyyy.MM.dd 'Time' HH.mm.ss");
        String fileName = dateFormat.format(date) + ".txt";//文件名
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            String[] items = resultList.getItems();
            bw.write(dateFormat.format(date)+ "\n");
            for (String item : items) {
                bw.write(item + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewFile(List list) {
        String fileName = new FileChooseDialog(this).getFile();
        System.out.println(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))
        ) {
            list.removeAll();
            int len;
            char[] data = new char[20];
            StringBuilder temp = new StringBuilder();
            while ((len = br.read(data)) != -1) {
                temp.append(data, 0, len);
            }
            int head = 0;
            int index = temp.indexOf("\n");
            while (index != temp.length()) {
                String substring = temp.substring(head, index);
                System.out.println("substring = " + substring);
                list.add(substring);
                temp.delete(head, index + 1);
                index = temp.indexOf("\n");
                if (index == -1) {
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String tabValue = e.getActionCommand();
        System.out.print(tabValue + " ");
        switch (tabValue) {
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
                if ("RESULT".equals(status)) {
                    System.out.println(status);
                    cleanPanel(s1, s3, text); //先清除再做
                    changeValue(tabValue, s1, text[0]);
                    break;
                }
                if ("LEFT".equals(status)) {
                    System.out.println(status);
                    changeValue(tabValue, s1, text[0]);
                    break;
                }
                if ("MID".equals(status)) {//如果状态在中间就做
                    if (s2.equals("")) {
                        break;
                    }
                    status = "RIGHT";//更改状态,此时在右边数字框
                }
                if ("RIGHT".equals(status)) {
                    System.out.println(status);
                    changeValue(tabValue, s3, text[2]);
                    break;
                }
            case ".":
                if ("LEFT".equals(status)) {
                    if (s1.indexOf(".") != -1) {
                        break;
                    }
                    System.out.println(status);
                    changeValue(tabValue, s1, text[0]);
                    break;
                }
                if ("RIGHT".equals(status)) {
                    if (s3.indexOf(".") != -1) {
                        break;
                    }
                    System.out.println(status);
                    changeValue(tabValue, s3, text[2]);
                    break;
                }

            case "1/x":
                if ("LEFT".equals(status)) {
                    s1 = new StringBuilder(String.valueOf(1 / Float.parseFloat(s1.toString())));
                    text[0].setText(s1.toString());
                }
                if ("RIGHT".equals(status)) {
                    s3 = new StringBuilder(String.valueOf(1 / Float.parseFloat(s3.toString())));
                    text[2].setText(s3.toString());
                }
                break;
            case "退格":
                if ("RESULT".equals(status)) {
                    break;//结果出来了不能退格
                }
                if ("LEFT".equals(status)) {
                    back(s1, text[0]);
                    break;
                }
                if ("MID".equals(status)) {
                    s2 = "";
                    text[1].setText(s2);
                    break;
                }
                if ("RIGHT".equals(status)) {
                    back(s3, text[2]);
                    break;
                }

            case "/":
            case "*":
            case "-":
            case "+":
                if (s1.length() == 0 || status.equals("RESULT")) {
                    break;
                }
                text[1].setText(tabValue);//设置文本框显示
                s2 = tabValue; //设置运算符
                status = "MID";
                System.out.println(status);
                break;
            case "+/-":
                if (status.equals("LEFT")) {
                    positive_negative(s1, text[0]);
                }
                if (status.equals("RIGHT")) {
                    positive_negative(s3, text[2]);
                }
                break;
            case "=":
                if (!"RIGHT".equals(status)) {
                    break;
                }
                status = "RESULT";
                System.out.println(status);
                float result = calculate(s1, s2, s3, text[3]);
                addList(s1, s2, s3, result, resultList);
                break;

            case "C":
                cleanPanel(s1, s3, text);
                break;

            case "清除":
                resultList.removeAll();
                break;

            case "保存":
                saveList(resultList);
                break;

            case "查看":
                viewFile(resultList);
                break;
        }
    }
}
