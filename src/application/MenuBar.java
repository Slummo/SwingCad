package application;


import geometry.CadElement;
import gui.dialogs.AddElementDialog;
import gui.dialogs.GoToDialog;
import gui.dialogs.SimpleDialogCreator;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class MenuBar extends JMenuBar {
    private final ListenersManager listenersManager;

    public MenuBar(MainFrame mainFrame, DrawPanel panel) {
        listenersManager = new ListenersManager(panel);

        //FILE
        JMenu file = new JMenu("File");
        JMenuItem f_save = new JMenuItem("Save");
        f_save.addActionListener(e -> RecordsManager.saveRecordsToFile());
        JMenuItem f_open = new JMenuItem("Open");
        f_open.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
            int value = fileChooser.showOpenDialog(mainFrame);
            File f = null;
            if(value == JFileChooser.APPROVE_OPTION)
                f = fileChooser.getSelectedFile();

            ArrayList<CadElement> elements = (ArrayList<CadElement>) FileManager.deserializeObjectFromFile(f);
            RecordsManager.addRecords(elements);
        });
        //TODO png, jpeg, pdf, gcode x stampanti 3D

        //Export as
        JMenu f_exportAs = new JMenu("Export as");
        JMenuItem png = new JMenuItem("png");
        png.addActionListener(e -> {
            FileManager.saveImageFromPanel(
                    MainFrame.drawPanel,
                    "png"
            );
            SimpleDialogCreator.showInfoDialog(null, "View saved as png!");
        });
        JMenuItem jpeg = new JMenuItem("jpeg");
        jpeg.addActionListener(e -> {
            FileManager.saveImageFromPanel(
                    MainFrame.drawPanel,
                    "jpeg"
            );
            SimpleDialogCreator.showInfoDialog(null, "View saved as jpeg!");
        });

        //TODO
        JMenuItem pdf = new JMenuItem("pdf");
        JMenuItem gcode = new JMenuItem("gcode");
        f_exportAs.add(png);
        f_exportAs.add(jpeg);
        f_exportAs.add(pdf);
        f_exportAs.add(gcode);

        //Exit
        JMenuItem f_exit = new JMenuItem("Exit");
        f_exit.addActionListener(e -> {
            if(SimpleDialogCreator.showConfirmDialog(mainFrame, "Save?")) RecordsManager.saveRecordsToFile();
            System.exit(0);
        });

        file.add(f_save);
        file.add(f_open);
        file.add(new JSeparator());
        file.add(f_exportAs);
        file.add(new JSeparator());
        file.add(f_exit);

        //TOOLS
        JMenu tools = new JMenu("Tools");

        //Add element
        JMenu t_addElement = new JMenu("Add element");

        JMenu typing = new JMenu("Dialog");

        //Point
        JMenuItem point = new JMenuItem("Add point");
        point.addActionListener(e -> new AddElementDialog(mainFrame, "Add new point", true, AddElementDialog.POINT));
        typing.add(point);

        //Segment
        JMenuItem segment = new JMenuItem("Add segment");
        segment.addActionListener(e -> new AddElementDialog(mainFrame, "Add new segment", true, AddElementDialog.SEGMENT));
        typing.add(segment);

        //Polyline
        JMenuItem polyline = new JMenuItem("Add polyline");
        polyline.addActionListener(e -> new AddElementDialog(mainFrame, "Add new polyline", true, AddElementDialog.POLYLINE));
        typing.add(polyline);

        //Polygon
        JMenuItem polygon = new JMenuItem("Add polygon");
        polygon.addActionListener(e -> new AddElementDialog(mainFrame, "Add new polygon", true, AddElementDialog.POLYGON));
        typing.add(polygon);

        //Rectangle
        JMenuItem rectangle = new JMenuItem("Add rectangle");
        rectangle.addActionListener(e -> new AddElementDialog(mainFrame, "Add new rectangle", true, AddElementDialog.RECTANGLE));
        typing.add(rectangle);

        //Ellipse
        JMenuItem ellipse = new JMenuItem("Add ellipse");
        ellipse.addActionListener(e -> new AddElementDialog(mainFrame, "Add new ellipse", true, AddElementDialog.ELLIPSE));
        typing.add(ellipse);

        JMenu mouse = new JMenu("Mouse clicks");

        //Point
        JMenuItem point2 = new JMenuItem("Add point");
        point2.addActionListener(e -> listenersManager.addListenersFor(AddElementDialog.POINT));
        mouse.add(point2);

        //Segment
        JMenuItem segment2 = new JMenuItem("Add segment");
        segment2.addActionListener(e -> listenersManager.addListenersFor(AddElementDialog.SEGMENT));
        mouse.add(segment2);

        //Polyline
        JMenuItem polyline2 = new JMenuItem("Add polyline");
        polyline2.addActionListener(e -> listenersManager.addListenersFor(AddElementDialog.POLYLINE));
        mouse.add(polyline2);

        //Polygon
        JMenuItem polygon2 = new JMenuItem("Add polygon");
        polygon2.addActionListener(e -> listenersManager.addListenersFor(AddElementDialog.POLYGON));
        mouse.add(polygon2);

        //Rectangle
        JMenuItem rectangle2 = new JMenuItem("Add rectangle");
        rectangle2.addActionListener(e -> listenersManager.addListenersFor(AddElementDialog.RECTANGLE));
        mouse.add(rectangle2);

        //Ellipse
        JMenuItem ellipse2 = new JMenuItem("Add ellipse");
        ellipse2.addActionListener(e -> listenersManager.addListenersFor(AddElementDialog.ELLIPSE));
        mouse.add(ellipse2);

        t_addElement.add(typing);
        t_addElement.add(mouse);

        JMenuItem t_goto = new JMenu("Go to");
        JMenuItem goCoords = new JMenuItem("Coordinates");
        goCoords.addActionListener(e -> new GoToDialog(mainFrame, "Go to", true, GoToDialog.COORDINATES));
        JMenuItem goElement = new JMenuItem("Element");
        goElement.addActionListener(e -> new GoToDialog(mainFrame, "Go to", true, GoToDialog.ELEMENT));

        t_goto.add(goCoords);
        t_goto.add(goElement);

        tools.add(t_addElement);
        tools.add(new JSeparator());
        tools.add(t_goto);

        //RECORDS
        JMenu records = new JMenu("Records");

        JMenuItem r_viewRecords = new JMenuItem("View records");
        r_viewRecords.addActionListener(e -> MainFrame.viewRecordsDialog.setVisible(true));
        records.add(r_viewRecords);

        JMenuItem r_clearRecords = new JMenuItem("Clear records");
        r_clearRecords.addActionListener(e -> RecordsManager.clearRecords());
        records.add(r_clearRecords);

        add(file);
        add(tools);
        add(records);
    }
}
