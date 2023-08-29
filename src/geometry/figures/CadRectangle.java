package geometry.figures;


import application.RecordsManager;
import geometry.CadElement;
import geometry.primitives.CadPoint;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class CadRectangle extends Rectangle2D.Double implements CadElement {
    private final String id;
    private String name;
    private Color color;
    private Dimension translation;
    private double rotation;
    private Dimension scale;
    private boolean draw;

    public CadRectangle(double x, double y, double w, double h) {
        super(x, y, w, h);
        id = RecordsManager.generateRandomId();
        name = "Rectangle";
        color = Color.black;
        translation = new Dimension();
        rotation = 0.0;
        scale = new Dimension(1, 1);
        draw = true;
    }

    public CadRectangle(String x, String y, String w, String h) {
        this(java.lang.Double.parseDouble(x), java.lang.Double.parseDouble(y), java.lang.Double.parseDouble(w), java.lang.Double.parseDouble(h));
    }

    public CadRectangle(CadPoint p1, CadPoint p2) {
        this(p1.x, p1.y, p1.distance(p2), p1.distance(p2));
    }

    public CadRectangle(CadPoint p1, CadPoint p2, CadPoint p3) {
        this(p1.x, p1.y, p1.distance(p2), p1.distance(p3));
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
