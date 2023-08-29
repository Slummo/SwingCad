package application;

import geometry.CadElement;
import geometry.figures.CadEllipse;
import geometry.figures.CadRectangle;
import geometry.primitives.CadPoint;
import gui.popups.AxisPopupMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class DrawPanel extends JPanel {
    private final int GRID_SIZE = 20;
    private Point clickPosition;
    private final Dimension offset;
    private final AxisPopupMenu axisPopupMenu;
    private CadElement previewElement;

    public DrawPanel() {
        super();
        clickPosition = new Point();
        offset = new Dimension();
        axisPopupMenu = new AxisPopupMenu(this);
        previewElement = null;
        addListeners();
    }

    private void addListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    axisPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                } else clickPosition = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - clickPosition.x;
                int dy = e.getY() - clickPosition.y;
                offset.width += dx;
                offset.height += dy;
                clickPosition = e.getPoint();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Translate the graphics context to center the origin
        g2.translate(width / 2.0 + offset.width, height / 2.0 + offset.height);

        // Draw grid with offset
        g2.setColor(Color.LIGHT_GRAY);
        for (int i = -width - offset.width; i < width - offset.width; i += GRID_SIZE)
            g2.drawLine(i, -height - offset.height, i, height - offset.height);
        for (int i = -height - offset.height; i < height - offset.height; i += GRID_SIZE)
            g2.drawLine(-width - offset.width, i, width - offset.width, i);

        // Draw axis
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2.0f));
        if (axisPopupMenu.showX())
            g2.drawLine(-width - offset.width, 0, width - offset.width, 0);
        if (axisPopupMenu.showY())
            g2.drawLine(0, -height - offset.height, 0, height - offset.height);

        AffineTransform savedTransform = g2.getTransform();

        // Draw preview element
        if(previewElement != null) {
            Rectangle bounds = ((Shape) previewElement).getBounds();
            int transX = (int) (bounds.getWidth() / 2.0);
            int transY = (int) (bounds.getHeight() / 2.0);
            g2.translate(-transX, -transY);
            g2.setColor(previewElement.getColor());
            g2.draw((Shape) previewElement);
            g2.setTransform(savedTransform);
        }

        // Draw elements
        ArrayList<CadElement> records = RecordsManager.getList();

        for (CadElement rec : records) {
            if (!rec.isDrawn()) continue;

            g2.setColor(rec.getColor());
            g2.transform(rec.getTransform());

            if (rec instanceof CadPoint p) {
                int size = 8;
                int startX = (int) (p.x - size / 2);
                int startY = (int) (p.y - size / 2);
                g2.fillOval(startX, startY, size, size);
            } else if(rec instanceof CadRectangle || rec instanceof CadEllipse) {
                Rectangle bounds = ((Shape) rec).getBounds();
                int transX = (int) (bounds.getWidth() / 2.0);
                int transY = (int) (bounds.getHeight() / 2.0);
                g2.translate(-transX, -transY);
            }

            if (rec instanceof Shape shape)
                g2.draw(shape);

            g2.setTransform(savedTransform);
        }

        g2.setTransform(new AffineTransform());
    }

    public Dimension getOffset() {
        return new Dimension((int) (getWidth() / 2.0 + offset.width), (int) (getHeight() / 2.0 + offset.height));
    }

    public void setOffset(Dimension offset) {
        this.offset.width = offset.width;
        this.offset.height = offset.height;
        revalidate();
        repaint();
    }

    public void setPreviewElement(CadElement element) {
        previewElement = element;
        revalidate();
        repaint();
    }
}
