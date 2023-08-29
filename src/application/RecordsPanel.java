package application;

import javax.swing.*;

public class RecordsPanel extends JPanel {
    public RecordsPanel() {
        super();
        RecordsManager recordsManager = new RecordsManager();
        add(new JScrollPane(recordsManager.getTable()));
    }
}
