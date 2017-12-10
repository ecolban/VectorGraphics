package org.jointheleague.ecolban.vectorgraphics.model;

import java.awt.geom.Path2D;

public interface Segment {

    Path2D addTo(Path2D path);

    Path2D addTo(Path2D path, double t);

    boolean consumesTime();

    double size();
}
