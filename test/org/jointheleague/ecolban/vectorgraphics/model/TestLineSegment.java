package org.jointheleague.ecolban.vectorgraphics.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestLineSegment {

    private Double p0;
    private Double p1;
    private java.awt.geom.Path2D.Double path;
    private double[] coordinates;
    private LineSegment lineSegment;

    @Before
    public void setup() {
        p0 = new Point2D.Double(1.0, 2.1);
        p1 = new Point2D.Double(3.0, 6.1);
        path = new Path2D.Double();
        path.moveTo(p0.getX(), p0.getY());
        coordinates = new double[]{p1.getX(), p1.getY(), 0, 0, 0, 0};
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
        TestUtils.assertEqualPoints(m0, p1);
    }

    @Test
    public void testAddToWithTime() {
        double t0 = 0.23;
        assertEquals(p0, path.getCurrentPoint());
        lineSegment.addTo(path, t0);
        TestUtils.assertPathLength(2, path);
        double u = 1 - t0;
        Point2D pt = Segment.affineCombo(new Point2D[]{p0, p1}, new double[]{u, t0});
        TestUtils.assertEqualPoints(pt, path.getCurrentPoint());
        double[] coords = new double[6];
        int type = TestUtils.getLastSegment(path, coords);
        assertEquals(PathIterator.SEG_LINETO, type);
        Point2D m0 = new Point2D.Double(coords[0], coords[1]);
        for (double t1 = 0.0; t1 <= 1.0; t1 += 0.1) {
            double u1 = 1 - t1;
            Point2D pt1 = Segment.affineCombo(
                    new Point2D[]{p0, m0},
                    new double[]{u1, t1});
            double t2 = t0 * t1;
            double u2 = 1 - t2;
            Point2D pt2 = Segment.affineCombo(
                    new Point2D[]{p0, p1},
                    new double[]{u2, t2});
            TestUtils.assertEqualPoints(pt1, pt2);
        }
    }
}
