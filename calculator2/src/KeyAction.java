import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author mobeiCanyue
 * Create  2021-12-24 17:04
 * Describe: 响应键盘事件
 */
public class KeyAction implements KeyListener {
    CalculatorWindow calculatorWindow;

    public KeyAction(CalculatorWindow calculatorWindow) {
        this.calculatorWindow = calculatorWindow;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(KeyEvent.getKeyText(e.getKeyCode()));
        String tabValue;
        if (e.getKeyCode() == 8) {
            tabValue = "退格";
        } else if (e.getKeyCode() == 10) {
            tabValue = "=";
        } else if (e.getKeyCode() == 116) {
            tabValue = "C";
        } else if (e.getKeyCode() == 27) {
            tabValue = "清除";
        } else {
            tabValue = String.valueOf(e.getKeyChar());
            System.out.print(tabValue + " ");
        }
        DataHandle.handleValue(tabValue, calculatorWindow);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

