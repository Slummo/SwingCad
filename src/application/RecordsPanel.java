package application;

import javax.swing.*;

public class RecordsPanel extends JPanel {
    private final RecordsManager recordsManager;

    public RecordsPanel() {
        super();
        recordsManager = new RecordsManager();
        add(new JScrollPane(recordsManager.getTable()));
    }

    public RecordsManager getRecordsManager() {
        return recordsManager;
    }
}
