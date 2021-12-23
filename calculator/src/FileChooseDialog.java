import java.awt.*;

/**
 * @author Lenovo
 * Create  2021-12-23 11:25
 * Describe:
 */
public class FileChooseDialog extends FileDialog {
    public FileChooseDialog(Frame parent) {
        super(parent);
        setVisible(true);
        setMultipleMode(false);
    }
}
