import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mobeiCanyue
 * Create  2021-12-24 19:25
 * Describe: 用于处理数据的类,因为按钮事件和键盘事件的处理高度重合,所以分离出来简化代码
 */
public class DataHandle {
    static StringBuilder s1 = new StringBuilder();//左
    static String s2;//右
    static StringBuilder s3 = new StringBuilder();//中

    //"LEFT" "RIGHT" "MID" "RESULT"
    static String status = "LEFT";//初始化窗口默认位置,*******非常重要,看懂这个即可理解本程序的流程控制,通过改变它来决定文本框位置!

    public static void changeValue(String tabValue, StringBuilder s, JTextField jTextField) {
        s.append(tabValue);
        jTextField.setText(s.toString());
    }

    public static void cleanPanel(StringBuilder s1, StringBuilder s3, JTextField[] texts) {
        s1.delete(0, s1.length());
        s3.delete(0, s3.length());
        for (JTextField jTextField : texts) {
            jTextField.setText("");
        }
        status = "LEFT";
    }

    public static float calculate(StringBuilder s1, String s2, StringBuilder s3, JTextField textField) {
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

    public static void back(StringBuilder s, JTextField textField) {
        if (s.length() != 0) {
            s.delete(s.length() - 1, s.length());
            textField.setText(s.toString());
        }
    }

    public static void addList(StringBuilder s1, String s2, StringBuilder s3, float result, List results) {
        String temp = s1.toString() + " " + s2 + " " + s3.toString() + " = " + result;
        results.add(temp);
    }

    public static void positive_negative(StringBuilder sb, JTextField textField) {
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

    public static void saveList(List list) {
        Date date = new Date();//获取当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("'Date' yyyy.MM.dd 'Time' HH.mm.ss");
        String fileName = dateFormat.format(date) + ".txt";//文件名
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            String[] items = list.getItems();
            bw.write(dateFormat.format(date) + "\n");
            for (String item : items) {
                bw.write(item + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void viewFile(CalculatorWindow calculatorWindow, List list) {

        String fileName = new FileChooseDialog(calculatorWindow).getFile();
        if (fileName == null) {
            System.out.println("未选择文件");
            return;
        }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleValue(String tabValue, CalculatorWindow calculatorWindow) {
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
                    cleanPanel(s1, s3, calculatorWindow.text); //先清除再做
                    changeValue(tabValue, s1, calculatorWindow.text[0]);
                    break;
                }
                if ("LEFT".equals(status)) {
                    System.out.println(status);
                    changeValue(tabValue, s1, calculatorWindow.text[0]);
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
                    changeValue(tabValue, s3, calculatorWindow.text[2]);
                    break;
                }
            case ".":
                if ("LEFT".equals(status)) {
                    if (s1.indexOf(".") != -1) {
                        break;
                    }
                    System.out.println(status);
                    changeValue(tabValue, s1, calculatorWindow.text[0]);
                    break;
                }
                if ("RIGHT".equals(status)) {
                    if (s3.indexOf(".") != -1) {
                        break;
                    }
                    System.out.println(status);
                    changeValue(tabValue, s3, calculatorWindow.text[2]);
                    break;
                }

            case "sqrt":
                if ("LEFT".equals(status)) {
                    if (s1.toString().equals("")) {
                        break;
                    }
                    s1 = new StringBuilder(String.valueOf(Math.sqrt(Float.parseFloat(s1.toString()))));
                    calculatorWindow.text[0].setText(s1.toString());
                }
                if ("RIGHT".equals(status)) {
                    if (s3.toString().equals("")) {
                        break;
                    }
                    s3 = new StringBuilder(String.valueOf(Math.sqrt(Float.parseFloat(s3.toString()))));
                    calculatorWindow.text[2].setText(s3.toString());
                }
                break;
            case "退格":
                if ("RESULT".equals(status)) {
                    break;//结果出来了不能退格
                }
                if ("LEFT".equals(status)) {
                    back(s1, calculatorWindow.text[0]);
                    break;
                }
                if ("MID".equals(status)) {
                    if (s2.equals("")) {//如果中间为空就跑到前面去
                        status = "LEFT";
                        back(s1, calculatorWindow.text[0]);
                        break;
                    }
                    s2 = "";
                    calculatorWindow.text[1].setText(s2);
                    break;
                }
                if ("RIGHT".equals(status)) {
                    if (s3.toString().equals("")) {//如果后面为空就跑到中间去
                        status = "MID";
                        s2 = "";
                        calculatorWindow.text[1].setText(s2);
                    }
                    back(s3, calculatorWindow.text[2]);
                    break;
                }

            case "/":
            case "*":
            case "-":
            case "+":
                if (s1.length() == 0 || status.equals("RESULT")) {
                    break;
                }
                calculatorWindow.text[1].setText(tabValue);//设置文本框显示
                s2 = tabValue; //设置运算符
                status = "MID";
                System.out.println(status);
                break;
            case "+/-":
                if (status.equals("LEFT")) {
                    positive_negative(s1, calculatorWindow.text[0]);
                }
                if (status.equals("RIGHT")) {
                    positive_negative(s3, calculatorWindow.text[2]);
                }
                break;
            case "=":
                if (!"RIGHT".equals(status)) {
                    break;
                }
                status = "RESULT";
                System.out.println(status);
                float result = calculate(s1, s2, s3, calculatorWindow.text[3]);
                addList(s1, s2, s3, result, calculatorWindow.resultList);
                break;

            case "C":
                cleanPanel(s1, s3, calculatorWindow.text);
                break;

            case "清除":
                calculatorWindow.resultList.removeAll();
                break;

            case "保存":
                if (calculatorWindow.resultList.getItemCount() == 0) {
                    JOptionPane.showMessageDialog(calculatorWindow, "列表为空! ! ! ", "列表为空", JOptionPane.ERROR_MESSAGE);
                } else {
                    saveList(calculatorWindow.resultList);
                    JOptionPane.showMessageDialog(calculatorWindow, "保存成功", "保存成功", JOptionPane.INFORMATION_MESSAGE);
                }
                break;

            case "查看":
                viewFile(calculatorWindow, calculatorWindow.resultList);
                break;
        }
    }
}
