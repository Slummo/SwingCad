package gui.dialogs;

import application.ColorsManager;
import application.ExceptionManager;
import application.MainFrame;
import application.RecordsManager;
import geometry.CadElement;
import geometry.primitives.CadPoint;

import javax.swing.*;
import java.awt.*;

public class EditElementDialog extends EditableDialog {
    private final CadElement el;

    public EditElementDialog(Component parent, String title, CadElement element) {
        super(parent, title, true);
        setResizable(false);
        this.el = element;
        init();
    }

    @Override
    protected void setupPanel() {
        String[] data = el.getData();
        JTextField fId = new JTextField(data[0]);
        fId.setEditable(false);
        JTextField fName = new JTextField(data[1]);
        fName.setEditable(false);
        JComboBox<String> comboBoxColor = new JComboBox<>(new String[] {
                ColorsManager.getStringFromColor(ColorsManager.BLACK),
                ColorsManager.getStringFromColor(ColorsManager.WHITE),
                ColorsManager.getStringFromColor(ColorsManager.RED),
                ColorsManager.getStringFromColor(ColorsManager.GREEN),
                ColorsManager.getStringFromColor(ColorsManager.BLUE)
        });
        comboBoxColor.setSelectedItem(data[2]);
        JTextField fTrans = new JTextField(data[3]);
        JTextField fRot = new JTextField(data[4]);
        JTextField fScale = new JTextField(data[5]);
        JComboBox<String> comboBoxDraw = new JComboBox<>(new String[] {
                "true",
                "false",
        });
        comboBoxDraw.setSelectedItem(data[6]);
        JButton btnRemove = new JButton("Remove");
        btnRemove.addActionListener(e -> {
            setRecordAction(() -> {
                if(!SimpleDialogCreator.showConfirmDialog(null)) return;
                RecordsManager.removeRecord(el, true);
            });
            runRecordAction();
            setRecordAction(() -> RecordsManager.editRecord(el));
            dispose();
        });
        JButton btnGoto = new JButton("Go to");
        btnGoto.addActionListener(e -> {
            ExceptionManager.tryAndCatch(
                    this,
                    () -> {
                        CadElement element = RecordsManager.getRecordWithId(fId.getText());
                        Dimension newOffset = new Dimension();
                        if(element instanceof CadPoint p) {
                            newOffset = new Dimension((int) p.x, (int) p.y);
                        } else if(element instanceof Shape s) {
                            newOffset = new Dimension((int) s.getBounds().getCenterX(), (int) s.getBounds().getCenterY());
                        }
                        MainFrame.drawPanel.setOffset(newOffset);
                    },
                    this::cleanTextFields,
                    "Invalid input. Please enter valid numbers."
            );
        });
        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(e -> {
            comboBoxColor.setSelectedItem(data[2]);
            fTrans.setText(data[3]);
            fRot.setText(data[4]);
            fScale.setText(data[5]);
            comboBoxDraw.setSelectedItem(data[6]);
        });
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> {
            el.edit(
                    (String) comboBoxColor.getSelectedItem(),
                    fTrans.getText(),
                    fRot.getText(),
                    fScale.getText(),
                    (String) comboBoxDraw.getSelectedItem()
            );
            runRecordAction();
            dispose();
        });


        addComponent(new JLabel("Id"), 0, 0, false);
        addComponent(fId, 1, 0, false);
        addComponent(new JLabel("Name"), 2, 0, false);
        addComponent(fName, 3, 0, false);
        addComponent(new JLabel("Color"), 4, 0, false);
        addComponent(comboBoxColor, 5, 0, false);
        addComponent(new JLabel("Translation"), 0, 1, false);
        addComponent(fTrans, 1, 1, false);
        addComponent(new JLabel("Rotation"), 2, 1, false);
        addComponent(fRot, 3, 1, false);
        addComponent(new JLabel("Scale"), 4, 1, false);
        addComponent(fScale, 5, 1, false);
        addComponent(new JLabel("Draw"), 6, 1, false);
        addComponent(comboBoxDraw, 7, 1, false);
        addComponent(btnRemove, 0, 2, false);
        addComponent(btnGoto, 1, 2, false);
        addComponent(btnReset, 6, 2, false);
        addComponent(btnSave, 7, 2, false);
    }

    @Override
    protected void setupAction() {
        recordAction = () -> RecordsManager.editRecord(el);
    }
}
