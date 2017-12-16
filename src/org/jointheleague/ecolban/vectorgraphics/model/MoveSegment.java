package org.jointheleague.ecolban.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class MoveSegment implements Segment {

    private final Point2D p1;

    public MoveSegment(final double[] coordinates) {
        p1 = new Point2D.Double(coordinates[0], coordinates[1]);
    }

    @Override
    public double length(Point2D p0) {
        return 0.0;
    }

    @Override
    public Path2D addTo(Path2D path) {
        path.moveTo(p1.getX(), p1.getY());
        return path;
    }

    @Override
    public Path2D addTo(Path2D path, double t) {
        path.moveTo(p1.getX(), p1.getY());
        return path;
    }
}
