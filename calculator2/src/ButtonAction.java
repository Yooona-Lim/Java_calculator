import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mobeiCanyue
 * Create  2021-12-24 12:59
 * Describe:
 */
public class ButtonAction implements ActionListener {
    
    Calculator calculator;
    public ButtonAction(Calculator calculator) {
        this.calculator = calculator;
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
        calculator.status = "LEFT";
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
        String fileName = new FileChooseDialog(calculator).getFile();
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
                if ("RESULT".equals(calculator.status)) {
                    System.out.println(calculator.status);
                    cleanPanel(calculator.s1, calculator.s3, calculator.text); //先清除再做
                    changeValue(tabValue, calculator.s1, calculator.text[0]);
                    break;
                }
                if ("LEFT".equals(calculator.status)) {
                    System.out.println(calculator.status);
                    changeValue(tabValue, calculator.s1, calculator.text[0]);
                    break;
                }
                if ("MID".equals(calculator.status)) {//如果状态在中间就做
                    if (calculator.s2.equals("")) {
                        break;
                    }
                    calculator.status = "RIGHT";//更改状态,此时在右边数字框
                }
                if ("RIGHT".equals(calculator.status)) {
                    System.out.println(calculator.status);
                    changeValue(tabValue, calculator.s3, calculator.text[2]);
                    break;
                }
            case ".":
                if ("LEFT".equals(calculator.status)) {
                    if (calculator.s1.indexOf(".") != -1) {
                        break;
                    }
                    System.out.println(calculator.status);
                    changeValue(tabValue, calculator.s1, calculator.text[0]);
                    break;
                }
                if ("RIGHT".equals(calculator.status)) {
                    if (calculator.s3.indexOf(".") != -1) {
                        break;
                    }
                    System.out.println(calculator.status);
                    changeValue(tabValue, calculator.s3, calculator.text[2]);
                    break;
                }

            case "1/x":
                if ("LEFT".equals(calculator.status)) {
                    calculator.s1 = new StringBuilder(String.valueOf(1 / Float.parseFloat(calculator.s1.toString())));
                    calculator.text[0].setText(calculator.s1.toString());
                }
                if ("RIGHT".equals(calculator.status)) {
                    calculator.s3 = new StringBuilder(String.valueOf(1 / Float.parseFloat(calculator.s3.toString())));
                    calculator.text[2].setText(calculator.s3.toString());
                }
                break;
            case "退格":
                if ("RESULT".equals(calculator.status)) {
                    break;//结果出来了不能退格
                }
                if ("LEFT".equals(calculator.status)) {
                    back(calculator.s1, calculator.text[0]);
                    break;
                }
                if ("MID".equals(calculator.status)) {
                    calculator.s2 = "";
                    calculator.text[1].setText(calculator.s2);
                    break;
                }
                if ("RIGHT".equals(calculator.status)) {
                    back(calculator.s3, calculator.text[2]);
                    break;
                }

            case "/":
            case "*":
            case "-":
            case "+":
                if (calculator.s1.length() == 0 || calculator.status.equals("RESULT")) {
                    break;
                }
                calculator.text[1].setText(tabValue);//设置文本框显示
                calculator.s2 = tabValue; //设置运算符
                calculator.status = "MID";
                System.out.println(calculator.status);
                break;
            case "+/-":
                if (calculator.status.equals("LEFT")) {
                    positive_negative(calculator.s1, calculator.text[0]);
                }
                if (calculator.status.equals("RIGHT")) {
                    positive_negative(calculator.s3, calculator.text[2]);
                }
                break;
            case "=":
                if (!"RIGHT".equals(calculator.status)) {
                    break;
                }
                calculator.status = "RESULT";
                System.out.println(calculator.status);
                float result = calculate(calculator.s1, calculator.s2, calculator.s3, calculator.text[3]);
                addList(calculator.s1, calculator.s2, calculator.s3, result, calculator.resultList);
                break;

            case "C":
                cleanPanel(calculator.s1, calculator.s3, calculator.text);
                break;

            case "清除":
                calculator.resultList.removeAll();
                break;

            case "保存":
                saveList(calculator.resultList);
                break;

            case "查看":
                viewFile(calculator.resultList);
                break;
        }
    }
}
