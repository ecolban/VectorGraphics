package org.jointheleague.ecolban.vectorgraphics.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestQuadSegment {

    private Point2D.Double p0;
    private Point2D.Double p1;
    private Point2D.Double p2;
    private java.awt.geom.Path2D.Double path;
    private QuadSegment quadSegment;

    @Before
    public void setup() {
        p0 = new Point2D.Double(1.0, 2.1);
        p1 = new Point2D.Double(3.0, 6.1);
        p2 = new Point2D.Double(6.0, 1.2);
        path = new Path2D.Double();
        path.moveTo(p0.getX(), p0.getY());
        double[] coordinates = new double[]{p1.getX(), p1.getY(), p2.getX(), p2.getY(), 0, 0};
        quadSegment = new QuadSegment(coordinates);
        assertNotNull(quadSegment);
    }

    @Test
    public void testConstructor() {
        assertNotNull(quadSegment);
    }

    @Test
    public void testLength() {
        assertEquals(p0.distance(p1) + p1.distance(p2), quadSegment.length(p0), 1e-6);
    }

    @Test
    public void testAddTo() {
        assertEquals(p0, path.getCurrentPoint());
        assertEquals(1, TestUtils.getPathLength(path));quadSegment.addTo(path);
        assertEquals(2, TestUtils.getPathLength(path));assertEquals(p2, path.getCurrentPoint());
        double[] coords = new double[6];
        int type = TestUtils.getLastSegment(path, coords);
        assertEquals(PathIterator.SEG_QUADTO, type);
        Point2D m0 = new Point2D.Double(coords[0], coords[1]);
        Point2D q0 = new Point2D.Double(coords[2], coords[3]);
        TestUtils.assertEqualPoints(p1, m0);
        TestUtils.assertEqualPoints(p2, q0);
    }

    @Test
    public void testAddToWithTime() {
        double t0 = ThreadLocalRandom.current().nextDouble();
        assertEquals(p0, path.getCurrentPoint());
        assertEquals(1, TestUtils.getPathLength(path));quadSegment.addTo(path, t0);
        assertEquals(2, TestUtils.getPathLength(path));Point2D pt = QuadSegment.getPointOnSegment(p0, p1, p2, t0);
        TestUtils.assertEqualPoints(pt, path.getCurrentPoint());
        double[] coords = new double[6];
        int type = TestUtils.getLastSegment(path, coords);
        assertEquals(PathIterator.SEG_QUADTO, type);
        Point2D m0 = new Point2D.Double(coords[0], coords[1]);
        Point2D q0 = new Point2D.Double(coords[2], coords[3]);
        for (double t = 0.0; t <= 1.0; t += 0.01) {
            TestUtils.assertEqualPoints(
                    QuadSegment.getPointOnSegment(p0, p1, p2, t0 * t),
                    QuadSegment.getPointOnSegment(p0, m0, q0, t));
        }
    }

}
