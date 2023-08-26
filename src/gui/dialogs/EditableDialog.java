package gui.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class EditableDialog extends JDialog {
    protected GridBagConstraints c;
    protected JPanel panel;
    protected Runnable recordAction;
    protected Runnable addAction;
    private final boolean initialVisibility;

    public EditableDialog(Component parent, String title, boolean initialVisibility) {
        super();
        setLocationRelativeTo(parent);
        setTitle(title);
        this.initialVisibility = initialVisibility;

        c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        panel = new JPanel(new GridBagLayout());
    }

    protected KeyListener createEnterKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Prevent the "Enter" key from creating a new line
                    e.consume();
                    addAction.run();
                }
            }
        };
    }

    protected void addComponent(Component comp, int col, int row, boolean center) {
        c.gridx = col;
        c.gridy = row;
        c.fill = GridBagConstraints.BOTH;
        if(center) {
            c.gridx = 0;
            c.gridwidth = GridBagConstraints.REMAINDER;
            c.anchor = GridBagConstraints.CENTER;
        }

        panel.add(comp, c);
        revalidate();
        repaint();
    }

    protected void cleanTextFields() {
        Component[] comps = panel.getComponents();
        for(Component c : comps)
            if(c instanceof JTextField t)
                t.setText("");
        revalidate();
        repaint();
    }

    protected void runRecordAction() {
        recordAction.run();
        cleanTextFields();
    }

    protected void runAddAction() {
        addAction.run();
    }

    protected void init() {
        setupPanel();
        add(panel);
        setupAction();

        setVisible(initialVisibility);
        pack();
    }

    protected abstract void setupPanel();
    protected abstract void setupAction();

    public void setRecordAction(Runnable recordAction) {
        this.recordAction = recordAction;
    }
}
