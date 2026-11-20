package remix.ui.widget;

import remix.ui.account.AltListModalPanel;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

public class ModalTopJFileChooser2 extends JFileChooser {
    AltListModalPanel altListModalPanel;

    @Override
    protected JDialog createDialog(Component component) throws HeadlessException {
        JDialog jdialog = super.createDialog(component);
        jdialog.setModal(true);
        jdialog.setAlwaysOnTop(true);
        return jdialog;
    }

    public ModalTopJFileChooser2(AltListModalPanel altListModalPanelValue, File file1) {
        super(file1);
        this.altListModalPanel = altListModalPanelValue;
    }
}
