package gui.dialogs;

import application.ExceptionManager;
import application.MainFrame;
import application.RecordsManager;
import geometry.CadElement;
import geometry.figures.CadEllipse;
import geometry.figures.CadRectangle;
import geometry.primitives.CadPoint;
import geometry.primitives.CadPolygon;
import geometry.primitives.CadPolyline;
import geometry.primitives.CadSegment;

import javax.swing.*;
import java.awt.*;

public class AddElementDialog extends EditableDialog {
    private final int code;
    private CadElement el;
    private CadPoint lastPoint;

    public static final int POINT = 0;
    public static final int SEGMENT = 1;
    public static final int POLYLINE = 2;
    public static final int POLYGON = 3;
    public static final int RECTANGLE = 4;
    public static final int ELLIPSE = 5;

    public AddElementDialog(Component parent, String title, boolean initialVisibility, int code) {
        super(parent, title, initialVisibility);
        setResizable(false);
        this.code = code;
        init();
    }

    @Override
    protected void setupPanel() {
        switch(code) {
            case POINT -> setForPoint();
            case SEGMENT -> setForSegment();
            case POLYLINE -> setForPolyline();
            case POLYGON -> setForPolygon();
            case RECTANGLE -> setForRectangle();
            case ELLIPSE -> setForEllipse();
        }
    }

    @Override
    protected void setupAction() {
        recordAction = () -> RecordsManager.addRecord(el, true);
    }

    private void setForPoint() {
        JTextField fX = new JTextField(10);
        JTextField fY = new JTextField(10);

        addAction = () -> {
            ExceptionManager.tryAndCatch(
                    this,
                    () -> {
                        el = new CadPoint(fX.getText(), fY.getText());
                        runRecordAction();
                    },
                    this::cleanTextFields,
                    "Invalid input. Please enter valid numbers."
            );
        };
        fY.addKeyListener(createEnterKeyListener());
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> runAddAction());
        addComponent(new JLabel("X"), 0, 0, false);
        addComponent(fX, 1, 0, false);
        addComponent(new JLabel("Y"), 2, 0, false);
        addComponent(fY, 3, 0, false);
        addComponent(btnAdd, 0, 1, true);
    }

    private void setForSegment() {
        JTextField fX1 = new JTextField(10);
        JTextField fY1 = new JTextField(10);
        JTextField fX2 = new JTextField(10);
        JTextField fY2 = new JTextField(10);
        addAction = () -> {
            ExceptionManager.tryAndCatch(
                    this,
                    () -> {
                        el = new CadSegment(fX1.getText(), fY1.getText(), fX2.getText(), fY2.getText());
                        runRecordAction();
                    },
                    this::cleanTextFields,
                    "Invalid input. Please enter valid numbers."
            );
        };
        fY2.addKeyListener(createEnterKeyListener());
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> runAddAction());

        addComponent(new JLabel("X1"), 0, 0, false);
        addComponent(fX1, 1, 0, false);
        addComponent(new JLabel("Y1"), 2, 0, false);
        addComponent(fY1, 3, 0, false);
        addComponent(new JLabel("X2"), 0, 1, false);
        addComponent(fX2, 1, 1, false);
        addComponent(new JLabel("Y2"), 2, 1, false);
        addComponent(fY2, 3, 1, false);
        addComponent(btnAdd, 0, 2, true);
    }

    private void setForPolyline() {
        JButton btnAddPoints = new JButton("Add points");
        JButton btnUndo = new JButton("Undo");
        setLastPoint(null, btnUndo);
        btnUndo.addActionListener(e -> {
            ((CadPolyline) el).removePoint(lastPoint);
            setLastPoint(null, btnUndo);
            SimpleDialogCreator.showInfoDialog(null, "Last point removed");
        });
        btnAddPoints.addActionListener(e -> {
            AddElementDialog d = new AddElementDialog(this, "Add points", true, AddElementDialog.POINT);
            el = new CadPolyline();
            d.setRecordAction(() -> {
                setLastPoint((CadPoint) d.getElement(), btnUndo);

                if(!((CadPolyline) el).hasPoints()) {
                    ((CadPolyline) el).addPoint(lastPoint, true);
                    runRecordAction();
                }
                ((CadPolyline) el).addPoint(lastPoint, false);
                SimpleDialogCreator.showInfoDialog(null, "Point added!");
            });
        });

        addComponent(btnAddPoints, 0, 0, false);
        addComponent(btnUndo, 1, 0, false);
    }

    private void setForPolygon() {
        AddElementDialog d = new AddElementDialog(this, "Add points", false, AddElementDialog.POINT);
        JButton btnAddPoints = new JButton("Add points");
        JButton btnUndo = new JButton("Undo");
        setLastPoint(null, btnUndo);
        btnUndo.addActionListener(e -> {
            ((CadPolygon) el).removePoint(lastPoint);
            setLastPoint(null, btnUndo);
            SimpleDialogCreator.showInfoDialog(null, "Last point removed!");
        });
        btnAddPoints.addActionListener(e -> {
            d.setVisible(true);
            el = new CadPolygon();
            d.setRecordAction(() -> {
                setLastPoint((CadPoint) d.getElement(), btnUndo);

                if(!((CadPolygon) el).hasPoints()) {
                    ((CadPolygon) el).addPoint(lastPoint, true);
                    runRecordAction();
                }
                ((CadPolygon) el).addPoint(lastPoint, false);
                SimpleDialogCreator.showInfoDialog(null, "Point added!");
            });
        });
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            ((CadPolygon) el).closePolygon();
            dispose();
            d.dispose();
            MainFrame.drawPanel.repaint();
        });

        addComponent(btnAddPoints, 0, 0, false);
        addComponent(btnUndo, 1, 0, false);
        addComponent(btnClose, 0, 1, true);
    }

    private void setForRectangle() {
        JTextField fX = new JTextField(10);
        JTextField fY = new JTextField(10);
        JTextField fW = new JTextField(10);
        JTextField fH = new JTextField(10);
        addAction = () -> {
            ExceptionManager.tryAndCatch(
                    this,
                    () -> {
                        el = new CadRectangle(fX.getText(), fY.getText(), fW.getText(), fH.getText());
                        runRecordAction();
                    },
                    this::cleanTextFields,
                    "Invalid input. Please enter valid numbers."
            );
        };
        fH.addKeyListener(createEnterKeyListener());
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> runAddAction());

        addComponent(new JLabel("X"), 0, 0, false);
        addComponent(fX, 1, 0, false);
        addComponent(new JLabel("Y"), 2, 0, false);
        addComponent(fY, 3, 0, false);
        addComponent(new JLabel("W"), 0, 1, false);
        addComponent(fW, 1, 1, false);
        addComponent(new JLabel("H"), 2, 1, false);
        addComponent(fH, 3, 1, false);
        addComponent(btnAdd, 0, 2, true);
    }

    private void setForEllipse() {
        JTextField fX = new JTextField(10);
        JTextField fY = new JTextField(10);
        JTextField fW = new JTextField(10);
        JTextField fH = new JTextField(10);
        addAction = () -> {
            ExceptionManager.tryAndCatch(
                    this,
                    () -> {
                        el = new CadEllipse(fX.getText(), fY.getText(), fW.getText(), fH.getText());
                        runRecordAction();
                    },
                    this::cleanTextFields,
                    "Invalid input. Please enter valid numbers."
            );
        };
        fH.addKeyListener(createEnterKeyListener());
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> runAddAction());

        addComponent(new JLabel("X"), 0, 0, false);
        addComponent(fX, 1, 0, false);
        addComponent(new JLabel("Y"), 2, 0, false);
        addComponent(fY, 3, 0, false);
        addComponent(new JLabel("W"), 0, 1, false);
        addComponent(fW, 1, 1, false);
        addComponent(new JLabel("H"), 2, 1, false);
        addComponent(fH, 3, 1, false);
        addComponent(btnAdd, 0, 2, true);
    }

    private void setLastPoint(CadPoint point, JButton btnUndo) {
        lastPoint = point;
        btnUndo.setEnabled(lastPoint != null);
        repaint();
        revalidate();
    }

    public CadElement getElement() {
        return el;
    }
}
