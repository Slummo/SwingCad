package application;

import gui.dialogs.ViewRecordsDialog;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static RecordsPanel recordsPanel;
    public static DrawPanel drawPanel;
    public static Terminal terminal;
    public static ViewRecordsDialog viewRecordsDialog;

    public MainFrame(int width, int height) {
        super("SwingCad");
        setSize(width, height);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
        setVisible(true);
    }

    public void init() {
        recordsPanel = new RecordsPanel();
        viewRecordsDialog = new ViewRecordsDialog(recordsPanel);

        drawPanel = new DrawPanel();
        add(drawPanel, BorderLayout.CENTER);

        terminal = new Terminal(new Dimension(getWidth() - 20, 30), this);
        terminal.setFont(new Font("Helvetica", Font.PLAIN, 30));
        JScrollPane scrollPane = new JScrollPane(terminal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.SOUTH);

        setJMenuBar(new MenuBar(this, drawPanel));
    }

    public static void main(String[] args) {
        new MainFrame(800, 800);
    }
}
