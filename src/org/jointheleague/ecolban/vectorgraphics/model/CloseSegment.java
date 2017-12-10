package org.jointheleague.ecolban.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class CloseSegment implements Segment {

    private final Point2D p0;
    private final Point2D p1;


    public CloseSegment(final Point2D p0, final Point2D p1) {
        super();
        this.p0 = p0;
        this.p1 = p1;
    }

    @Override
    public boolean consumesTime() {
        return true;
    }

    @Override
    public double getSize() {
        return p0.distance(p1);
    }

    @Override
    public Path2D addTo(Path2D path) {
        path.closePath();
        return path;
    }

    public Path2D addTo(Path2D path, double t) {
        double u = 1 - t;
        path.lineTo(
                u * p0.getX() + t * p1.getX(),
                u * p0.getY() + t * p1.getY());
        return path;
    }
}
