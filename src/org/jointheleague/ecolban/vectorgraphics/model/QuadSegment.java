package org.jointheleague.ecolban.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class QuadSegment implements Segment {
    private final Point2D p0;
    private final Point2D p1;
    private final Point2D p2;

    public QuadSegment(final Point2D p0, final double[] coordinates) {
        super();
        this.p0 = p0;
        this.p1 = new Point2D.Double(coordinates[0], coordinates[1]);
        this.p2 = new Point2D.Double(coordinates[2], coordinates[3]);
    }


    @Override
    public boolean consumesTime() {
        return true;
    }

    @Override
    public double size() {
        return p0.distance(p1) + p1.distance(p2);
    }

    @Override
    public Path2D addTo(Path2D path) {
        path.quadTo(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        return path;
    }

    @Override
    public Path2D addTo(Path2D path, double t) {
        double u = 1 - t;
        double tt = t * t;
        double tu = t * u;
        double uu = u * u;

        path.quadTo(
                u * p0.getX() + t * p1.getX(),
                u * p0.getY() + t * p1.getY(),
                uu * p0.getX() + 2 * tu * p1.getX() + tt * p2.getX(),
                uu * p0.getY() + 2 * tu * p1.getY() + tt * p2.getY());

        return path;
    }
}
