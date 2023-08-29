package application;

import geometry.CadElement;
import gui.dialogs.EditElementDialog;
import gui.dialogs.SimpleDialogCreator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.UUID;

public class RecordsManager {
    private static ArrayList<CadElement> list;
    private final JTable table;
    private static DefaultTableModel model;

    private static final int ID_LENGTH = 6;
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXY0123456789";
    private static final SecureRandom random = new SecureRandom();

    public RecordsManager() {
        super();
        list = new ArrayList<>();
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Id");
        model.addColumn("Name");
        model.addColumn("Color");
        model.addColumn("Translation");
        model.addColumn("Rotation");
        model.addColumn("Scale");
        model.addColumn("Draw");

        table = new JTable(model);
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (e.getClickCount() == 2) {
                    String id = (String) model.getValueAt(row, 0);
                    new EditElementDialog(null, "Edit record", getRecordWithId(id));
                }
            }
        });
    }

    public static CadElement getRecordWithId(String id) {
        id = id.toUpperCase();
        for(CadElement element : list) {
            if(element.getId().equals(id))
                return element;
        }
        return null;
    }

    public static String generateRandomId() {
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int randomIndex = random.nextInt(ALPHANUMERIC.length());
            char randomChar = ALPHANUMERIC.charAt(randomIndex);
            sb.append(randomChar);
        }

        String randId = sb.toString();

        for(CadElement el : list) {
            if(el.getId().equals(randId)) {
                randId = generateRandomId();
                break;
            }
        }

        return randId;
    }

    public static void addRecord(CadElement record, boolean showDialog) {
        if(list.contains(record)) {
            SimpleDialogCreator.showInfoDialog(null, "Record already exists!");
            return;
        }
        list.add(record);
        model.addRow(record.getData());
        MainFrame.drawPanel.repaint();
        if(showDialog)
            SimpleDialogCreator.showInfoDialog(null, "Record added!");
    }

    public static void addRecords(ArrayList<CadElement> elements) {
        for(CadElement el : elements) {
            addRecord(el, false);
        }

        SimpleDialogCreator.showInfoDialog(null, "Records added!");
    }

    public static void editRecord(CadElement newRecord) {
        if(!SimpleDialogCreator.showConfirmDialog(null)) return;

        int index = getIndexFromId(newRecord.getId());
        list.set(index, newRecord);
        model.removeRow(index);
        model.insertRow(index, newRecord.getData());
        MainFrame.drawPanel.repaint();
        SimpleDialogCreator.showInfoDialog(null, "Record edited!");
    }

    public static void removeRecord(CadElement record, boolean showDialog) {
        if(!list.contains(record)) {
            SimpleDialogCreator.showErrorDialog(null, "Record doesn't exist!");
            return;
        }

        list.remove(record);
        model.setRowCount(0);
        for (CadElement r : list) {
            model.addRow(r.getData());
        }
        MainFrame.drawPanel.repaint();
        if(showDialog)
            SimpleDialogCreator.showInfoDialog(null, "Record removed!");
    }

    public static void removeRecord(String id) {
        id = id.toUpperCase();
        for(CadElement c : list) {
            if(c.getId().equals(id)) {
                removeRecord(c, true);
                return;
            }
        }
        SimpleDialogCreator.showErrorDialog(null, "Record doesn't exist!");
    }

    public static void clearRecords() {
        if (list.isEmpty()) {
            SimpleDialogCreator.showInfoDialog(null, "No records to clear!");
            return;
        }

        if (!SimpleDialogCreator.showConfirmDialog(null)) return;

        while (!list.isEmpty()) {
            CadElement c = list.get(0);
            removeRecord(c, false);
        }

        SimpleDialogCreator.showInfoDialog(null, "Records cleared!");
    }

    public static void saveRecordsToFile() {
        if(list.isEmpty()) {
            SimpleDialogCreator.showInfoDialog(null, "No records to save!");
            return;
        }
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setSelectedFile(FileManager.getCustomFile(null, "serialized", "ser"));
        int value = fileChooser.showSaveDialog(null);
        if(value == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            FileManager.serializeObjToFile(
                    RecordsManager.getList(),
                    f
            );
        }



        SimpleDialogCreator.showInfoDialog(null, "Records saved!");
    }

    private static int getIndexFromId(String id) {
        id = id.toUpperCase();
        for(CadElement element : list) {
            if(element.getId().equals(id))
                return list.indexOf(element);
        }
        return -1;
    }

    public static ArrayList<CadElement> getList() {
        return list;
    }

    public JTable getTable() {
        return table;
    }
}
