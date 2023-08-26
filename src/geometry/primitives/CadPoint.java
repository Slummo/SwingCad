package geometry.primitives;

import application.RecordsManager;
import geometry.CadElement;

import java.awt.*;
import java.awt.geom.Point2D;

public class CadPoint extends Point2D.Double implements CadElement {
    private String id;
    private String name;
    private Color color;
    private Dimension translation;
    private double rotation;
    private Dimension scale;
    private boolean draw;

    public CadPoint(double x, double y) {
        super(x, y);
        id = RecordsManager.generateRandomId();
        name = "Point";
        color = Color.black;
        translation = new Dimension();
        rotation = 0.0;
        scale = new Dimension(1, 1);
        draw = true;
    }

    public CadPoint(String x, String y) {
        this(java.lang.Double.parseDouble(x), java.lang.Double.parseDouble(y));
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setId(String id) {
        this.id = id;
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
