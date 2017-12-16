package org.jointheleague.ecolban.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import static org.junit.Assert.assertEquals;

public class TestUtils {

    private static final double EPSILON = 1e-6;

    public static void assertPathLength(int expectedLength, Path2D pathClone) {
        int len = 0;
        PathIterator pi = pathClone.getPathIterator(null);
        while (!pi.isDone()) {
            len++;
            pi.next();
        }
        assertEquals(expectedLength, len);
    }

    public static void assertEqualPoints(Point2D p1, Point2D p2) {
        assertEquals(p1.getX(), p2.getX(), EPSILON);
        assertEquals(p1.getY(), p2.getY(), 1e-6);
    }

    public static int getLastSegment(Path2D path, double[] coords) {
        int lastType = -1;
        PathIterator pi = path.getPathIterator(null);
        while (!pi.isDone()) {
            lastType = pi.currentSegment(coords);
            pi.next();
        }
        return lastType;
    }
}
