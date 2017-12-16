package org.jointheleague.ecolban.vectorgraphics.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestLineSegment {

    private Double p0;
    private Double p1;
    private java.awt.geom.Path2D.Double path;
    private LineSegment lineSegment;

    @Before
    public void setup() {
        p0 = new Point2D.Double(1.0, 2.1);
        p1 = new Point2D.Double(3.0, 6.1);
        path = new Path2D.Double();
        path.moveTo(p0.getX(), p0.getY());
        double[] coordinates = new double[]{p1.getX(), p1.getY(), 0, 0, 0, 0};
        lineSegment = new LineSegment(coordinates);
        assertNotNull(lineSegment);
    }

    @Test
    public void testLength() {
        assertEquals(p0.distance(p1), lineSegment.length(p0), 1e-6);
    }

    @Test
    public void testAddTo() {
        assertEquals(p0, path.getCurrentPoint());
        lineSegment.addTo(path);
        TestUtils.assertPathLength(2, path);
        assertEquals(p1, path.getCurrentPoint());
        double[] coords = new double[6];
        int type = TestUtils.getLastSegment(path, coords);
        assertEquals(PathIterator.SEG_LINETO, type);
        Point2D m0 = new Point2D.Double(coords[0], coords[1]);
        TestUtils.assertEqualPoints(p1, m0);
    }

    @Test
    public void testAddToWithTime() {
        double t0 = ThreadLocalRandom.current().nextDouble();
        assertEquals(p0, path.getCurrentPoint());
        TestUtils.assertPathLength(1, path);
        lineSegment.addTo(path, t0);
        TestUtils.assertPathLength(2, path);
        final Point2D pt = LineSegment.getPointOnSegment(p0, p1, t0);
        TestUtils.assertEqualPoints(pt, path.getCurrentPoint());
        double[] coords = new double[6];
        int type = TestUtils.getLastSegment(path, coords);
        assertEquals(PathIterator.SEG_LINETO, type);
        Point2D m0 = new Point2D.Double(coords[0], coords[1]);
        for (double t = 0.0; t <= 1.0; t += 0.01) {
            TestUtils.assertEqualPoints(
                    LineSegment.getPointOnSegment(p0, p1, t0 * t),
                    LineSegment.getPointOnSegment(p0, m0, t));
        }
    }

}
