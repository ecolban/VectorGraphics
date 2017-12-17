package org.jointheleague.ecolban.vectorgraphics.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestCloseSegment {

    private Point2D.Double p0;
    private Point2D.Double p1;
    private Point2D.Double p2;
    private java.awt.geom.Path2D.Double path;
    private CloseSegment closeSegment;

    @Before
    public void setup() {
        p0 = new Point2D.Double(1.0, 2.1);
        p1 = new Point2D.Double(3.0, 6.1);
        p2 = new Point2D.Double(6.0, 1.2);
        path = new Path2D.Double();
        path.moveTo(p0.getX(), p0.getY());
        path.quadTo(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        closeSegment = new CloseSegment(p0);
    }

    @Test
    public void testConstructor() {
        assertNotNull(closeSegment);
    }

    @Test
    public void testLength() {
        assertEquals(p2.distance(p0), closeSegment.length(p2), 1e-6);
    }

    @Test
    public void testAddTo() {
        assertEquals(p2, path.getCurrentPoint());
        assertEquals(2, TestUtils.getPathLength(path));
        closeSegment.addTo(path);
        assertEquals(3, TestUtils.getPathLength(path));
        assertEquals(p0, path.getCurrentPoint());
        double[] coords = new double[6];
        int type = TestUtils.getLastSegment(path, coords);
        assertEquals(PathIterator.SEG_CLOSE, type);
    }

    @Test
    public void testAddToWithTime() {
        double t0 = ThreadLocalRandom.current().nextDouble();
        assertEquals(p2, path.getCurrentPoint());
        assertEquals(2, TestUtils.getPathLength(path));
        closeSegment.addTo(path, t0);
        assertEquals(3, TestUtils.getPathLength(path));
        final Point2D pt = LineSegment.getPointOnSegment(p2, p0, t0);
        TestUtils.assertEqualPoints(pt, path.getCurrentPoint());
        double[] coords = new double[6];
        int type = TestUtils.getLastSegment(path, coords);
        assertEquals(PathIterator.SEG_LINETO, type);
        Point2D m0 = new Point2D.Double(coords[0], coords[1]);
        for (double t = 0.0; t <= 1.0; t += 0.01) {
            TestUtils.assertEqualPoints(
                    LineSegment.getPointOnSegment(p2, p0, t0 * t),
                    LineSegment.getPointOnSegment(p2, m0, t));
        }
    }
}
