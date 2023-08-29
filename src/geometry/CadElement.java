package geometry;

import application.ColorsManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

public interface CadElement extends Serializable {
    String getId();
    void setName(String name);
    String getName();
    void setColor(Color color);
    Color getColor();
    void setTranslation(Dimension translation);
    Dimension getTranslation();
    void setRotation(double rotation);
    double getRotation();
    void setScale(Dimension scale);
    void setDraw(boolean draw);
    boolean isDrawn();
    Dimension getScale();

    default void edit(Color color, Dimension translation, double rotation, Dimension scale, boolean draw) {
        setColor(color);
        setTranslation(translation);
        setRotation(rotation);
        setScale(scale);
        setDraw(draw);
    }

    default void edit(String color, String translation, String rotation, String scale, String draw) {
        edit(ColorsManager.getColorFromString(color), stringToDimension(translation), Double.parseDouble(rotation), stringToDimension(scale), Boolean.parseBoolean(draw));
    }

    private Dimension stringToDimension(String dimension) {
        String[] parts = dimension.split(",");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid dimension string format. Expected 'w, h'.");
        }

        try {
            int width = Integer.parseInt(parts[0].trim());
            int height = Integer.parseInt(parts[1].trim());

            if (width < 0 || height < 0) {
                throw new IllegalArgumentException("Width and height must be positive integers.");
            }

            return new Dimension(width, height);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid integer format for width or height.");
        }
    }

    default String dimensionToString(Dimension dimension) {
        return dimension.width + ", " + dimension.height;
    }

    default String[] getData() {
        return new String[] {
                getId(),
                getName(),
                ColorsManager.getStringFromColor(getColor()),
                dimensionToString(getTranslation()),
                "" + getRotation(),
                dimensionToString(getScale()),
                "" + isDrawn()
        };
    }

    default AffineTransform getTransform() {
        AffineTransform af = new AffineTransform();
        af.translate(getTranslation().getWidth(), getTranslation().getHeight());
        af.rotate(Math.toRadians(getRotation()));
        af.scale(getScale().getWidth(), getScale().getHeight());
        return af;
    }
}
