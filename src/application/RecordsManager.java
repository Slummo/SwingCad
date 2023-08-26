package application;

import geometry.CadElement;
import gui.dialogs.EditElementDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;

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
                    new EditElementDialog(null, "Edit record", getElementWithId(id));
                }
            }
        });
    }

    public static CadElement getElementWithId(String id) {
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
            JOptionPane.showMessageDialog(null, "Record already exists!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        list.add(record);
        model.addRow(record.getData());
        MainFrame.drawPanel.repaint();
        if(showDialog)
            JOptionPane.showMessageDialog(null, "Record added!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void addRecords(ArrayList<CadElement> elements) {
        for(CadElement el : elements) {
            addRecord(el, false);
        }
    }

    public static void editRecord(CadElement newRecord) {
        int index = getIndexFromId(newRecord.getId());
        list.set(index, newRecord);
        model.removeRow(index);
        model.insertRow(index, newRecord.getData());
        MainFrame.drawPanel.repaint();
        JOptionPane.showMessageDialog(null, "Record edited!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void removeRecord(CadElement record) {
        if(!list.contains(record)) {
            JOptionPane.showMessageDialog(null, "Record doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        list.remove(record);
        model.setRowCount(0);
        for (CadElement r : list) {
            model.addRow(r.getData());
        }
        MainFrame.drawPanel.repaint();
        JOptionPane.showMessageDialog(null, "Record removed!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void removeRecord(String id) {
        id = id.toUpperCase();
        for(CadElement c : list) {
            if(c.getId().equals(id)) {
                removeRecord(c);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Record doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
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
