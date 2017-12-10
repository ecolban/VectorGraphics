package org.jointheleague.ecolban.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class CubicSegment implements Segment {
    private final Point2D p0;
    private final Point2D p1;
    private final Point2D p2;
    private final Point2D p3;

    public CubicSegment(final Point2D p0, final double[] coordinates) {
        super();
        this.p0 = p0;
        this.p1 = new Point2D.Double(coordinates[0], coordinates[1]);
        this.p2 = new Point2D.Double(coordinates[2], coordinates[3]);
        this.p3 = new Point2D.Double(coordinates[4], coordinates[5]);
    }

    @Override
    public boolean consumesTime() {
        return true;
    }

    @Override
    public double size() {
        return p0.distance(p1) + p1.distance(p2) + p2.distance(p3);
    }

    @Override
    public Path2D addTo(Path2D path) {
        path.curveTo(
                p1.getX(), p1.getY(),
                p2.getX(), p2.getY(),
                p3.getX(), p3.getY());
        return path;
    }

    @Override
    public Path2D addTo(Path2D path, double t) {
        double u = 1 - t;
        double tt = t * t;
        double tu = t * u;
        double uu = u * u;
        double ttt = tt * t;
        double ttu = tt * u;
        double tuu = tu * u;
        double uuu = uu * u;

        path.curveTo(
                u * p0.getX() + t * p1.getX(),
                u * p0.getY() + t * p1.getY(),
                uu * p0.getX() + 2 * tu * p1.getX() + tt * p2.getX(),
                uu * p0.getY() + 2 * tu * p1.getY() + tt * p2.getY(),
                uuu * p0.getX() + 3 * tuu * p1.getX() + 3 * ttu * p2.getX() + ttt * p3.getX(),
                uuu * p0.getY() + 3 * tuu * p1.getY() + 3 * ttu * p2.getY() + ttt * p3.getY());

        return path;
    }
}
