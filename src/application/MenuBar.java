package application;


import geometry.CadElement;
import gui.dialogs.AddElementDialog;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class MenuBar extends JMenuBar {
    public MenuBar(MainFrame mainFrame) {
        //FILE
        JMenu file = new JMenu("File");
        JMenuItem f_save = new JMenuItem("Save");
        f_save.addActionListener(e -> {
            FileManager.serializeObjToFile(
                    RecordsManager.getList(),
                    new File("serialized/records.ser")
            );
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "Records saved!",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
        JMenuItem f_open = new JMenuItem("Open");
        f_open.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
            int value = fileChooser.showOpenDialog(mainFrame);
            File f = null;
            if(value == JFileChooser.APPROVE_OPTION)
                f = fileChooser.getSelectedFile();

            ArrayList<CadElement> elements = (ArrayList<CadElement>) FileManager.deserializeObjectFromFile(f);
            RecordsManager.addRecords(elements);
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "Records added!",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
        //TODO png, jpeg, pdf, gcode x stampanti 3D

        //Export as
        JMenu f_exportAs = new JMenu("Export as");
        JMenuItem png = new JMenuItem("png");
        png.addActionListener(e -> {
            FileManager.saveImageFromPanel(
                    MainFrame.drawPanel,
                    ".png"
            );
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "View saved as png!",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
        JMenuItem jpeg = new JMenuItem("jpeg");
        jpeg.addActionListener(e -> {
            FileManager.saveImageFromPanel(
                    MainFrame.drawPanel,
                    ".jpeg"
            );
            JOptionPane.showMessageDialog(
                    mainFrame,
                    "View saved as jpeg!",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        //TODO
        JMenuItem pdf = new JMenuItem("pdf");
        JMenuItem gcode = new JMenuItem("gcode");
        f_exportAs.add(png);
        f_exportAs.add(jpeg);
        f_exportAs.add(pdf);
        f_exportAs.add(gcode);

        file.add(f_save);
        file.add(f_open);
        file.add(new JSeparator());
        file.add(f_exportAs);

        //TOOLS
        JMenu tools = new JMenu("Tools");

        //Add element
        JMenu t_addElement = new JMenu("Add element");

        //Point
        JMenuItem point = new JMenuItem("Add point");
        point.addActionListener(e -> new AddElementDialog(mainFrame, "Add new point", true, AddElementDialog.POINT));
        t_addElement.add(point);

        //Segment
        JMenuItem segment = new JMenuItem("Add segment");
        segment.addActionListener(e -> new AddElementDialog(mainFrame, "Add new segment", true, AddElementDialog.SEGMENT));
        t_addElement.add(segment);

        //Polyline
        JMenuItem polyline = new JMenuItem("Add polyline");
        polyline.addActionListener(e -> new AddElementDialog(mainFrame, "Add new polyline", true, AddElementDialog.POLYLINE));
        t_addElement.add(polyline);

        //Polygon
        JMenuItem polygon = new JMenuItem("Add polygon");
        polygon.addActionListener(e -> new AddElementDialog(mainFrame, "Add new polygon", true, AddElementDialog.POLYGON));
        t_addElement.add(polygon);

        //Rectangle
        JMenuItem t_rectangle = new JMenuItem("Add rectangle");
        t_rectangle.addActionListener(e -> new AddElementDialog(mainFrame, "Add new rectangle", true, AddElementDialog.RECTANGLE));
        t_addElement.add(t_rectangle);

        //Ellipse
        JMenuItem t_ellipse = new JMenuItem("Add ellipse");
        t_ellipse.addActionListener(e -> new AddElementDialog(mainFrame, "Add new ellipse", true, AddElementDialog.ELLIPSE));
        t_addElement.add(t_ellipse);

        tools.add(t_addElement);

        //RECORDS
        JMenu records = new JMenu("Records");

        JMenuItem r_viewRecords = new JMenuItem("View records");
        r_viewRecords.addActionListener(e -> MainFrame.viewRecordsDialog.setVisible(true));
        records.add(r_viewRecords);

        add(file);
        add(tools);
        add(records);
    }
}
