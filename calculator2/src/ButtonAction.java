import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author mobeiCanyue
 * Create  2021-12-24 12:59
 * Describe:相应按钮事件
 */
public class ButtonAction implements ActionListener {

    CalculatorWindow calculatorWindow;

    public ButtonAction(CalculatorWindow calculatorWindow) {
        this.calculatorWindow = calculatorWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String tabValue = e.getActionCommand();
        System.out.print(tabValue + " ");
        DataHandle.handleValue(tabValue, calculatorWindow);
    }
}
