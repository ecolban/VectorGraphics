package org.jointheleague.ecolban.vectorgraphics.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestCubicSegment {

    private static final double EPSILON = 1e-6;
    private Point2D p0;
    private Point2D p1;
    private Point2D p2;
    private Point2D p3;
    private Path2D.Double path;
    private CubicSegment cubicSegment;

    @Before
    public void setup() {
        p0 = new Point2D.Double(1.0, 2.1);
        p1 = new Point2D.Double(3.0, 6.1);
        p2 = new Point2D.Double(6.0, 1.2);
        p3 = new Point2D.Double(9.1, 1.0);
        path = new Path2D.Double();
        path.moveTo(p0.getX(), p0.getY());
        double[] coordinates = new double[]{p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY()};
        cubicSegment = new CubicSegment(coordinates);
        assertNotNull(cubicSegment);
    }

    @Test
    public void testConstructor() {
        assertNotNull(cubicSegment);
    }

    @Test
    public void testLength() {
        assertEquals(p0.distance(p1) + p1.distance(p2) + p2.distance(p3), cubicSegment.length(p0), EPSILON);
    }

    @Test
    public void testAddTo() {
        assertEquals(p0, path.getCurrentPoint());
        assertEquals(1, TestUtils.getPathLength(path));
        cubicSegment.addTo(path);
        assertEquals(2, TestUtils.getPathLength(path));
        assertEquals(p3, path.getCurrentPoint());
        double[] coords = new double[6];
        int type = TestUtils.getLastSegment(path, coords);
        assertEquals(PathIterator.SEG_CUBICTO, type);
        Point2D m0 = new Point2D.Double(coords[0], coords[1]);
        Point2D q0 = new Point2D.Double(coords[2], coords[3]);
        Point2D c0 = new Point2D.Double(coords[4], coords[5]);
        TestUtils.assertEqualPoints(p1, m0);
        TestUtils.assertEqualPoints(p2, q0);
        TestUtils.assertEqualPoints(p3, c0);
    }

    @Test
    public void testAddToWithTime() {
        double t0 = ThreadLocalRandom.current().nextDouble();
        assertEquals(p0, path.getCurrentPoint());
        assertEquals(1, TestUtils.getPathLength(path));
        cubicSegment.addTo(path, t0);
        assertEquals(2, TestUtils.getPathLength(path));
        TestUtils.assertEqualPoints(CubicSegment.getPointOnSegment(p0, p1, p2, p3, t0), path.getCurrentPoint());
        double[] coords = new double[6];
        int type = TestUtils.getLastSegment(path, coords);
        assertEquals(PathIterator.SEG_CUBICTO, type);
        final Point2D m0 = new Point2D.Double(coords[0], coords[1]);
        final Point2D q0 = new Point2D.Double(coords[2], coords[3]);
        final Point2D c0 = new Point2D.Double(coords[4], coords[5]);
        for (double t = 0.0; t <= 1.0; t += 0.01) {
            TestUtils.assertEqualPoints(
                    CubicSegment.getPointOnSegment(p0, p1, p2, p3, t0 * t),
                    CubicSegment.getPointOnSegment(p0, m0, q0, c0, t));
        }
    }

}
