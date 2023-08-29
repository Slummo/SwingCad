package gui.dialogs;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class SimpleDialogCreator {
    public static void showInfoDialog(Component parent, String message) {
        showMessageDialog(parent, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorDialog(Component parent, String message) {
        showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean showConfirmDialog(Component parent) {
        int value = JOptionPane.showConfirmDialog(parent, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
        return value == JOptionPane.YES_OPTION;
    }

    public static boolean showConfirmDialog(Component parent, String message) {
        int value = JOptionPane.showConfirmDialog(parent, message, "Confirm", JOptionPane.YES_NO_OPTION);
        return value == JOptionPane.YES_OPTION;
    }
}
