package org.jointheleague.ecolban.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class LineSegment implements Segment {
    private final Point2D p1;

    public LineSegment(final double[] coordinates) {
        super();
        this.p1 = new Point2D.Double(coordinates[0], coordinates[1]);
    }

    @Override
    public double length(Point2D p0) {
        return p0.distance(p1);
    }

    @Override
    public Path2D addTo(Path2D path) {
        path.lineTo(p1.getX(), p1.getY());
        return path;
    }

    @Override
    public Path2D addTo(Path2D path, double t) {
        final Point2D p0 = path.getCurrentPoint();
        Point2D pt = getPointOnSegment(p0, p1, t);
        path.lineTo(pt.getX(), pt.getY());
        return path;
    }

    public static Point2D getPointOnSegment(Point2D p0, Point2D p1, double t) {
        return Segment.affineCombo(new Point2D[]{p0, p1}, new double[]{1 - t, t});
    }

}
