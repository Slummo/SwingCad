package geometry.primitives;

public class CadPolygon extends CadPolyline {
    public CadPolygon(CadPoint[] points, boolean close) {
        super(points);
        if(close) closePolygon();
        setName("Polygon");
    }

    public CadPolygon() {
        this(null, false);
    }

    public void closePolygon() {
        closePath();
    }
}
