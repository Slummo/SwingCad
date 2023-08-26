package gui.popups;

import javax.swing.*;

public class AxisPopupMenu extends JPopupMenu {
    private boolean showX;
    private boolean showY;
    private final JCheckBoxMenuItem x_checkBox;
    private final JCheckBoxMenuItem y_checkBox;

    public AxisPopupMenu(JPanel owner) {
        super();
        showX = showY = true;
        x_checkBox = new JCheckBoxMenuItem("Show X ax");
        y_checkBox = new JCheckBoxMenuItem("Show Y ax");
        x_checkBox.setSelected(showX);
        y_checkBox.setSelected(showY);

        x_checkBox.addActionListener(e -> {
            showX = x_checkBox.isSelected();
            owner.repaint();
        });

        y_checkBox.addActionListener(e -> {
            showY = y_checkBox.isSelected();
            owner.repaint();
        });

        add(x_checkBox);
        add(y_checkBox);
    }

    public boolean showX() {
        return showX;
    }

    public boolean showY() {
        return showY;
    }
}
