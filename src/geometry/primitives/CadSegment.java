package geometry.primitives;

import application.RecordsManager;
import geometry.CadElement;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class CadSegment extends Line2D.Double implements CadElement {
    private final String id;
    private String name;
    private Color color;
    private Dimension translation;
    private double rotation;
    private Dimension scale;
    private boolean draw;

    public CadSegment(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
        id = RecordsManager.generateRandomId();
        name = "Segment";
        color = Color.black;
        translation = new Dimension();
        rotation = 0.0;
        scale = new Dimension(1, 1);
        draw = true;
    }

    public CadSegment(Point2D p1, Point2D p2) {
        this(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public CadSegment(String x1, String y1, String x2, String y2) {
        this(java.lang.Double.parseDouble(x1), java.lang.Double.parseDouble(y1), java.lang.Double.parseDouble(x2), java.lang.Double.parseDouble(y2));
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
    public Dimension getScale() {
        return scale;
    }

    @Override
    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    @Override
    public boolean isDrawn() {
        return draw;
    }
}
