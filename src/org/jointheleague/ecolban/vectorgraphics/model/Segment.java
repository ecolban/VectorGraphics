package org.jointheleague.ecolban.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public interface Segment {

    Path2D addTo(Path2D path);

    Path2D addTo(Path2D path, double t);

    boolean consumesTime();

    double length(Point2D p0);
}
