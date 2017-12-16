package org.jointheleague.ecolban.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public interface Segment {

    Path2D addTo(Path2D path);

    Path2D addTo(Path2D path, double t);

    double length(Point2D p0);

    static Point2D affineCombo(Point2D[] points, double[] coeff) {
        if (points == null || coeff == null || points.length != coeff.length) {
            throw new IllegalArgumentException();
        }
        double x = 0.0, y = 0.0;
        for (int i = 0; i < points.length; i++) {
            x += points[i].getX() * coeff[i];
            y += points[i].getY() * coeff[i];
        }
        return new Point2D.Double(x, y);
    }
}
