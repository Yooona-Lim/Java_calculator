import java.awt.*;

/**
 * @author mobeicanyue
 * Create  2021-12-23 11:25
 * Describe: 文件对话框
 */
public class FileChooseDialog extends FileDialog {
    public FileChooseDialog(Frame parent) {
        super(parent);
        setVisible(true);
        setMultipleMode(false);
    }
}
