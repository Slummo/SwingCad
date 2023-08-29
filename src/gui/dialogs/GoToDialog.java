package gui.dialogs;

import application.ExceptionManager;
import application.MainFrame;
import application.RecordsManager;
import geometry.CadElement;
import geometry.primitives.CadPoint;

import javax.swing.*;
import java.awt.*;

public class GoToDialog extends EditableDialog {
    private final int code;

    public final static int COORDINATES = 0;
    public final static int ELEMENT = 1;

    public GoToDialog(Component parent, String title, boolean initialVisibility, int code) {
        super(parent, title, initialVisibility);
        setResizable(false);
        this.code = code;
        init();
    }

    @Override
    protected void setupPanel() {
        switch(code) {
            case COORDINATES -> setupForCoordinates();
            case ELEMENT -> setupForElement();
        }
    }

    @Override
    protected void setupAction() {}

    private void setupForCoordinates() {
        JTextField fX = new JTextField(10);
        JTextField fY = new JTextField(10);
        fY.addKeyListener(createEnterKeyListener());
        addAction = () -> {
            ExceptionManager.tryAndCatch(
                    this,
                    () -> MainFrame.drawPanel.setOffset(new Dimension(
                            Integer.parseInt(fX.getText()),
                            Integer.parseInt(fY.getText())
                    )),
                    this::cleanTextFields,
                    "Invalid input. Please enter valid numbers."
            );
        };
        JButton btnGo = new JButton("Go");
        btnGo.addActionListener(e -> runAddAction());

        addComponent(new JLabel("X"), 0, 0, false);
        addComponent(fX, 1, 0, false);
        addComponent(new JLabel("Y"), 2, 0, false);
        addComponent(fY, 3, 0, false);
        addComponent(btnGo, 0, 1, true);
    }

    private void setupForElement() {
        JTextField fId = new JTextField(10);
        fId.addKeyListener(createEnterKeyListener());
        addAction = () -> {
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
        };
        JButton btnGo = new JButton("Go");
        btnGo.addActionListener(e -> runAddAction());

        addComponent(new JLabel("Id"), 0, 0, false);
        addComponent(fId, 1, 0, false);
        addComponent(btnGo, 0, 1, true);
    }
}
