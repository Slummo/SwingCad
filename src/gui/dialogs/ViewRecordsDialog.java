package gui.dialogs;

import javax.swing.*;

public class ViewRecordsDialog extends JDialog {
    public ViewRecordsDialog(JPanel panel) {
        super();
        setTitle("Records");
        add(panel);
        setLocationRelativeTo(panel.getParent());
        pack();
    }
}
