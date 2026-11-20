package remix.ui.widget;

import remix.ui.account.AltManagerScreen;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

public class ModalTopJFileChooser1 extends JFileChooser {
    AltManagerScreen altManagerScreen;

    @Override
    protected JDialog createDialog(Component component) throws HeadlessException {
        JDialog jdialog = super.createDialog(component);
        jdialog.setModal(true);
        jdialog.setAlwaysOnTop(true);
        return jdialog;
    }

    public ModalTopJFileChooser1(AltManagerScreen altManagerScreenValue, File file1) {
        super(file1);
        this.altManagerScreen = altManagerScreenValue;
    }
}
