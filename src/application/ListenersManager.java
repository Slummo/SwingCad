package application;

import geometry.CadElement;
import geometry.figures.CadEllipse;
import geometry.figures.CadRectangle;
import geometry.primitives.CadPoint;
import geometry.primitives.CadPolygon;
import geometry.primitives.CadPolyline;
import geometry.primitives.CadSegment;
import gui.dialogs.AddElementDialog;
import gui.dialogs.SimpleDialogCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ListenersManager {
    private final DrawPanel panel;
    private int nRecordsAdded;
    private CadPoint firstPoint;
    private CadPoint secondPoint;
    private CadPolyline lastPolyline;
    private CadElement previewElement;

    private final MouseAdapter pointAdapter;
    private final MouseAdapter segmentAdapter;
    private final MouseAdapter polylineAdapter;
    private final MouseAdapter polygonAdapter;
    private final MouseAdapter rectangleAdapter;
    private final MouseAdapter ellipseAdapter;

    private MouseAdapter addedAdapter;

    private final KeyAdapter enterAdapter;

    public ListenersManager(DrawPanel panel) {
        this.panel = panel;
        nRecordsAdded = 0;

        pointAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                firstPoint = new CadPoint(e.getPoint(), panel.getOffset());
                RecordsManager.addRecord(firstPoint, false);
                nRecordsAdded++;
            }
        };

        segmentAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(firstPoint != null) {
                    RecordsManager.addRecord(new CadSegment(firstPoint, new CadPoint(e.getPoint(), panel.getOffset())), false);
                    nRecordsAdded++;
                    firstPoint = null;
                } else firstPoint = new CadPoint(e.getPoint(), panel.getOffset());
            }
        };

        polylineAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(lastPolyline != null) {
                    lastPolyline.addPoint(new CadPoint(e.getPoint(), panel.getOffset()), false);
                }
                else if(firstPoint != null) {
                    lastPolyline = new CadPolyline(new CadPoint[] {firstPoint, new CadPoint(e.getPoint(), panel.getOffset())});
                    RecordsManager.addRecord(lastPolyline, false);
                    firstPoint = null;
                } else firstPoint = new CadPoint(e.getPoint(), panel.getOffset());
            }
        };

        polygonAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(lastPolyline != null) {
                    lastPolyline.addPoint(new CadPoint(e.getPoint(), panel.getOffset()), false);
                }
                else if(firstPoint != null) {
                    lastPolyline = new CadPolygon(new CadPoint[] {firstPoint, new CadPoint(e.getPoint(), panel.getOffset())}, false);
                    RecordsManager.addRecord(lastPolyline, false);
                    firstPoint = null;
                } else firstPoint = new CadPoint(e.getPoint(), panel.getOffset());
            }
        };

        rectangleAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (firstPoint == null) {
                    firstPoint = new CadPoint(e.getPoint(), panel.getOffset());
                } else if (secondPoint == null) {
                    secondPoint = new CadPoint(e.getPoint(), panel.getOffset());
                } else {
                    CadRectangle r = new CadRectangle(firstPoint, secondPoint, new CadPoint(e.getPoint(), panel.getOffset()));
                    RecordsManager.addRecord(r, false);
                    nRecordsAdded++;
                    firstPoint = null;
                    secondPoint = null;
                    previewElement = null;
                    panel.setPreviewElement(null);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if(firstPoint != null && secondPoint == null) {
                    previewElement = new CadRectangle(firstPoint, new CadPoint(e.getPoint(), panel.getOffset()));
                    panel.setPreviewElement(previewElement);
                } else if (firstPoint != null) {
                    previewElement = new CadRectangle(firstPoint, secondPoint, new CadPoint(e.getPoint(), panel.getOffset()));
                    panel.setPreviewElement(previewElement);
                }
            }
        };

        ellipseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (firstPoint == null) {
                    firstPoint = new CadPoint(e.getPoint(), panel.getOffset());
                } else if (secondPoint == null) {
                    secondPoint = new CadPoint(e.getPoint(), panel.getOffset());
                } else {
                    CadEllipse ellipse = new CadEllipse(firstPoint, secondPoint, new CadPoint(e.getPoint(), panel.getOffset()));
                    RecordsManager.addRecord(ellipse, false);
                    nRecordsAdded++;
                    firstPoint = null;
                    secondPoint = null;
                    previewElement = null;
                    panel.setPreviewElement(null);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if(firstPoint != null && secondPoint == null) {
                    previewElement = new CadEllipse(firstPoint, new CadPoint(e.getPoint(), panel.getOffset()));
                    panel.setPreviewElement(previewElement);
                } else if (firstPoint != null) {
                    previewElement = new CadEllipse(firstPoint, secondPoint, new CadPoint(e.getPoint(), panel.getOffset()));
                    panel.setPreviewElement(previewElement);
                }
            }
        };

        enterAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(lastPolyline != null) {
                    if(lastPolyline instanceof CadPolygon c) {
                        c.closePolygon();
                    }
                    lastPolyline = null;
                    SimpleDialogCreator.showInfoDialog(null, "Added record");
                    return;
                }
                SimpleDialogCreator.showInfoDialog(null, "Added " + nRecordsAdded + " record(s)!");

                firstPoint = null;
                nRecordsAdded = 0;
                MainFrame.terminal.requestFocus();
                removeEnterAdapter();
                removeAddedAdapter();
            }
        };
    }

    public void addListenersFor(int code) {
        switch(code) {
            case AddElementDialog.POINT -> addPointListeners();
            case AddElementDialog.SEGMENT -> addSegmentListeners();
            case AddElementDialog.POLYLINE -> addPolylineListeners();
            case AddElementDialog.POLYGON -> addPolygonListeners();
            case AddElementDialog.RECTANGLE -> addRectangleListeners();
            case AddElementDialog.ELLIPSE -> addEllipseListeners();
        }
    }

    private void addPointListeners() {
        addedAdapter = pointAdapter;
        panel.addMouseListener(pointAdapter);
        panel.requestFocus();
        addEnterAdapter();
    }

    private void addSegmentListeners() {
        addedAdapter = segmentAdapter;
        panel.addMouseListener(segmentAdapter);
        panel.requestFocus();
        addEnterAdapter();
    }

    private void addPolylineListeners() {
        addedAdapter = polylineAdapter;
        panel.addMouseListener(polylineAdapter);
        panel.requestFocus();
        addEnterAdapter();
    }

    private void addPolygonListeners() {
        addedAdapter = polygonAdapter;
        panel.addMouseListener(polygonAdapter);
        panel.requestFocus();
        addEnterAdapter();
    }

    private void addRectangleListeners() {
        addedAdapter = rectangleAdapter;
        panel.addMouseListener(rectangleAdapter);
        panel.addMouseMotionListener(rectangleAdapter);
        panel.requestFocus();
        addEnterAdapter();
    }

    private void addEllipseListeners() {
        addedAdapter = ellipseAdapter;
        panel.addMouseListener(ellipseAdapter);
        panel.addMouseMotionListener(ellipseAdapter);
        panel.requestFocus();
        addEnterAdapter();
    }

    private void removeAddedAdapter() {
        panel.removeMouseListener(addedAdapter);
        panel.removeMouseMotionListener(addedAdapter);
    }

    private void addEnterAdapter() {
        panel.addKeyListener(enterAdapter);
    }

    private void removeEnterAdapter() {
        panel.removeKeyListener(enterAdapter);
    }
}
