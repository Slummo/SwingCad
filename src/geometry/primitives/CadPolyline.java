package geometry.primitives;

import application.MainFrame;
import application.RecordsManager;
import geometry.CadElement;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

public class CadPolyline extends Path2D.Double implements CadElement {
    private final String id;
    private String name;
    private Color color;
    private Dimension translation;
    private double rotation;
    private Dimension scale;
    private boolean draw;
    private boolean hasPoints;

    public CadPolyline(CadPoint[] points) {
        super();
        addPoints(points);
        id = RecordsManager.generateRandomId();
        name = "Polyline";
        color = Color.black;
        translation = new Dimension();
        rotation = 0.0;
        scale = new Dimension(1, 1);
        draw = true;
        hasPoints = false;
    }

    public CadPolyline() {
        this(null);
    }

    private void addPoints(CadPoint[] points) {
        if(points == null) return;
        for(CadPoint p : points) {
            if(points[0].equals(p)) moveTo(p.x, p.y);
            else lineTo(p.x, p.y);
        }
        hasPoints = true;
    }

    public void addPoint(CadPoint p, boolean move) {
        if(move) moveTo(p.x, p.y);
        else lineTo(p.x, p.y);
        MainFrame.drawPanel.repaint();
        hasPoints = true;
    }

    public void removePoint(CadPoint p) {
        Path2D.Double newPath = new Path2D.Double();

        PathIterator iterator = getPathIterator(null);
        while (!iterator.isDone()) {
            double[] cords = new double[2];
            int type = iterator.currentSegment(cords);

            if (type == PathIterator.SEG_MOVETO) {
                newPath.moveTo(cords[0], cords[1]);
            } else if (type == PathIterator.SEG_LINETO && (cords[0] != p.x || cords[1] != p.y)) {
                newPath.lineTo(cords[0], cords[1]);
            }

            iterator.next();
        }

        reset();
        append(newPath, false);

        MainFrame.drawPanel.repaint();
    }

    public boolean hasPoints() {
        return hasPoints;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setTranslation(Dimension translation) {
        this.translation = translation;
    }

    @Override
    public Dimension getTranslation() {
        return translation;
    }

    @Override
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    @Override
    public double getRotation() {
        return rotation;
    }

    @Override
    public void setScale(Dimension scale) {
        this.scale = scale;
    }

    @Override
    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    @Override
    public boolean isDrawn() {
        return draw;
    }

    @Override
    public Dimension getScale() {
        return scale;
    }
}
