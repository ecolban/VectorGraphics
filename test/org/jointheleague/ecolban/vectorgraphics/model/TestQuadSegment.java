package org.jointheleague.ecolban.vectorgraphics.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestQuadSegment {

    private Point2D.Double p0;
    private Point2D.Double p1;
    private Point2D.Double p2;
    private java.awt.geom.Path2D.Double path;
    private double[] coordinates;
    QuadSegment quadSegment;

    @Before
    public void setup() {
        p0 = new Point2D.Double(1.0, 2.1);
        p1 = new Point2D.Double(3.0, 6.1);
        p2 = new Point2D.Double(6.0, 1.2);
        path = new Path2D.Double();
        path.moveTo(p0.getX(), p0.getY());
        coordinates = new double[]{p1.getX(), p1.getY(), p2.getX(), p2.getY(), 0, 0};
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
        TestUtils.assertPathLength(1, path);
        quadSegment.addTo(path);
        TestUtils.assertPathLength(2, path);
        assertEquals(p2, path.getCurrentPoint());
        double[] coords = new double[6];
        int type = TestUtils.getLastSegment(path, coords);
        assertEquals(PathIterator.SEG_QUADTO, type);
        Point2D m0 = new Point2D.Double(coords[0], coords[1]);
        Point2D q0 = new Point2D.Double(coords[2], coords[3]);
        TestUtils.assertEqualPoints(m0, p1);
        TestUtils.assertEqualPoints(q0, p2);
    }

    @Test
    public void testAddToWithTime() {
        double t0 = 0.23;
        assertEquals(p0, path.getCurrentPoint());
        quadSegment.addTo(path, t0);
        TestUtils.assertPathLength(2, path);
        double u = 1 - t0;
        Point2D pt = Segment.affineCombo(
                new Point2D[]{p0, p1, p2},
                new double[]{u * u, 2 * u * t0, t0 * t0});
        TestUtils.assertEqualPoints(pt, path.getCurrentPoint());
        double[] coords = new double[6];
        int type = TestUtils.getLastSegment(path, coords);
        assertEquals(PathIterator.SEG_QUADTO, type);
        Point2D m0 = new Point2D.Double(coords[0], coords[1]);
        Point2D q0 = new Point2D.Double(coords[2], coords[3]);
        for (double t1 = 0.0; t1 <= 1.0; t1 += 0.1) {
            double u1 = 1 - t1;
            Point2D pt1 = Segment.affineCombo(
                    new Point2D[]{p0, m0, q0},
                    new double[]{u1 * u1, 2 * u1 * t1, t1 * t1});
            double t2 = t0 * t1;
            double u2 = 1 - t2;
            Point2D pt2 = Segment.affineCombo(
                    new Point2D[]{p0, p1, p2},
                    new double[]{u2 * u2, 2 * u2 * t2, t2 * t2});
            TestUtils.assertEqualPoints(pt1, pt2);
        }
    }
}
