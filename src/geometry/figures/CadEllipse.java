package geometry.figures;

import application.RecordsManager;
import geometry.CadElement;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CadEllipse extends Ellipse2D.Double implements CadElement {
    private String id;
    private String name;
    private Color color;
    private Dimension translation;
    private double rotation;
    private Dimension scale;
    private boolean draw;

    public CadEllipse(double x, double y, double w, double h) {
        super(x, y, w, h);
        id = RecordsManager.generateRandomId();
        name = "Ellipse";
        color = Color.black;
        translation = new Dimension();
        rotation = 0.0;
        scale = new Dimension(1, 1);
        draw = true;
    }

    public CadEllipse(String x, String y, String w, String h) {
        this(java.lang.Double.parseDouble(x), java.lang.Double.parseDouble(y), java.lang.Double.parseDouble(w), java.lang.Double.parseDouble(h));
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
