package application;

import geometry.figures.CadEllipse;
import geometry.figures.CadRectangle;
import geometry.primitives.CadPoint;
import geometry.primitives.CadPolygon;
import geometry.primitives.CadPolyline;
import geometry.primitives.CadSegment;
import gui.dialogs.EditElementDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Terminal extends JTextField {
    private final MainFrame mainFrame;
    private ArrayList<String> availableCommands;

    public Terminal(Dimension size, MainFrame mainFrame) {
        super();
        this.mainFrame = mainFrame;
        setPreferredSize(size);
        init();
    }

    private void init() {
        availableCommands = new ArrayList<>();
        availableCommands.add("add");
        availableCommands.add("edit");
        availableCommands.add("remove");
        availableCommands.add("help");

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Prevent the "Enter" key from creating a new line
                    e.consume();
                    runCommand(getText());
                    setText("");
                }
            }
        });
    }

    public void runCommand(String input) {
        input = input.toLowerCase();
        String[] args = input.split(" ");

        String command = args[0];

        switch(command) {
            case "add" -> {
                if(args.length == 1) {
                    JOptionPane.showMessageDialog(
                            mainFrame,
                            "Too few arguments! add <shape> [coordinates]",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                switch (args[1]) {
                    case "point" -> {
                        if (args.length != 4) {
                            JOptionPane.showMessageDialog(
                                    mainFrame,
                                    "Too many or too few arguments! add point <x> <y>",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        RecordsManager.addRecord(new CadPoint(args[2], args[3]), true);
                    }
                    case "segment" -> {
                        if (args.length != 6) {
                            JOptionPane.showMessageDialog(
                                    mainFrame,
                                    "Too many or too few arguments! add segment <x1> <y1> <x2> <y2>",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        RecordsManager.addRecord(new CadSegment(args[2], args[3], args[4], args[5]), true);
                    }
                    case "polyline" -> {
                        if (args.length < 4) {
                            JOptionPane.showMessageDialog(
                                    mainFrame,
                                    "Too few arguments! add polyline <x1> <y1>...",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        ArrayList<CadPoint> arr = new ArrayList<>();
                        for(int i = 2; i < args.length - 1; i += 2) {
                            arr.add(new CadPoint(args[i], args[i + 1]));
                        }

                        RecordsManager.addRecord(new CadPolyline(arr.toArray(new CadPoint[0])),true);
                    }
                    case "polygon" -> {
                        //Not even a triangle
                        if(args.length < 8) {
                            JOptionPane.showMessageDialog(
                                    mainFrame,
                                    "Too few arguments! add polygon <x1> <y1> <x2> <y2> <x3> <y3>...",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        ArrayList<CadPoint> arr = new ArrayList<>();
                        for(int i = 2; i < args.length - 1; i += 2) {
                            arr.add(new CadPoint(args[i], args[i + 1]));
                        }

                        RecordsManager.addRecord(new CadPolygon(arr.toArray(new CadPoint[0]), true), true);
                    }
                    case "rectangle" -> {
                        if (args.length != 6) {
                            JOptionPane.showMessageDialog(
                                    mainFrame,
                                    "Too many or too few arguments! add rectangle <upperLeftCornerX> <upperLeftCornerY> <width> <height>",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        RecordsManager.addRecord(new CadRectangle(args[2], args[3], args[4], args[5]), true);
                    }
                    case "ellipse" -> {
                        if (args.length != 6) {
                            JOptionPane.showMessageDialog(
                                    mainFrame,
                                    "Too many or too few arguments! add ellipse <upperLeftCornerX> <upperLeftCornerY> <width> <height>",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        RecordsManager.addRecord(new CadEllipse(args[2], args[3], args[4], args[5]), true);
                    }
                    default -> JOptionPane.showMessageDialog(
                            mainFrame,
                            "Not a valid shape",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            case "help" -> JOptionPane.showMessageDialog(
                    mainFrame,
                    availableCommands,
                    "Available commands",
                    JOptionPane.INFORMATION_MESSAGE
            );
            case "edit" -> {
                if(args.length != 2) {
                    JOptionPane.showMessageDialog(
                            mainFrame,
                            "Too many or too few arguments! remove <index>",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                new EditElementDialog(mainFrame, "Edit", RecordsManager.getElementWithId(args[1]));
            }
            case "remove" -> {
                if(args.length != 2) {
                    JOptionPane.showMessageDialog(
                            mainFrame,
                            "Too many or too few arguments! remove <index>",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                RecordsManager.removeRecord(args[1]);
            }
            default -> JOptionPane.showMessageDialog(
                    null,
                    "Not a valid command",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
