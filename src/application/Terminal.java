package application;

import geometry.CadElement;
import geometry.figures.CadEllipse;
import geometry.figures.CadRectangle;
import geometry.primitives.CadPoint;
import geometry.primitives.CadPolygon;
import geometry.primitives.CadPolyline;
import geometry.primitives.CadSegment;
import gui.dialogs.EditElementDialog;
import gui.dialogs.SimpleDialogCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
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
        availableCommands.add("clear");
        availableCommands.add("save");
        availableCommands.add("open");
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
                    SimpleDialogCreator.showErrorDialog(mainFrame, "Too few arguments! add <shape> [coordinates]");
                    return;
                }

                switch (args[1]) {
                    case "point" -> {
                        if (args.length != 4) {
                            SimpleDialogCreator.showErrorDialog(mainFrame, "Too many or too few arguments! add point <x> <y>");
                            return;
                        }

                        RecordsManager.addRecord(new CadPoint(args[2], args[3]), true);
                    }
                    case "segment" -> {
                        if (args.length != 6) {
                            SimpleDialogCreator.showErrorDialog(mainFrame, "Too many or too few arguments! add segment <x1> <y1> <x2> <y2>");
                            return;
                        }

                        RecordsManager.addRecord(new CadSegment(args[2], args[3], args[4], args[5]), true);
                    }
                    case "polyline" -> {
                        if (args.length < 4) {
                            SimpleDialogCreator.showErrorDialog(mainFrame, "Too few arguments! add polyline <x1> <y1>...");
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
                            SimpleDialogCreator.showErrorDialog(mainFrame, "Too few arguments! add polygon <x1> <y1> <x2> <y2> <x3> <y3>...");
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
                            SimpleDialogCreator.showErrorDialog(mainFrame, "Too many or too few arguments! add rectangle <upperLeftCornerX> <upperLeftCornerY> <width> <height>");
                            return;
                        }

                        RecordsManager.addRecord(new CadRectangle(args[2], args[3], args[4], args[5]), true);
                    }
                    case "ellipse" -> {
                        if (args.length != 6) {
                            SimpleDialogCreator.showErrorDialog(mainFrame, "Too many or too few arguments! add ellipse <upperLeftCornerX> <upperLeftCornerY> <width> <height>");
                            return;
                        }

                        RecordsManager.addRecord(new CadEllipse(args[2], args[3], args[4], args[5]), true);
                    }
                    default -> SimpleDialogCreator.showErrorDialog(mainFrame, "Not a valid shape");
                }
            }
            case "save" -> RecordsManager.saveRecordsToFile();
            case "open" -> {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                int value = fileChooser.showOpenDialog(mainFrame);
                File f = null;
                if(value == JFileChooser.APPROVE_OPTION)
                    f = fileChooser.getSelectedFile();

                ArrayList<CadElement> elements = (ArrayList<CadElement>) FileManager.deserializeObjectFromFile(f);
                RecordsManager.addRecords(elements);
            }
            case "edit" -> {
                if(args.length != 2) {
                    SimpleDialogCreator.showErrorDialog(mainFrame, "Too many or too few arguments! edit <index>");
                    return;
                }

                new EditElementDialog(mainFrame, "Edit", RecordsManager.getRecordWithId(args[1]));
            }
            case "remove" -> {
                if(args.length != 2) {
                    SimpleDialogCreator.showErrorDialog(mainFrame, "Too many or too few arguments! remove <index>");
                    return;
                }
                if(!SimpleDialogCreator.showConfirmDialog(null)) return;
                RecordsManager.removeRecord(args[1]);
            }
            case "clear" -> RecordsManager.clearRecords();
            case "help" -> SimpleDialogCreator.showInfoDialog(mainFrame, "Available commands");
            default -> SimpleDialogCreator.showErrorDialog(mainFrame, "Not a valid command");
        }
    }
}
