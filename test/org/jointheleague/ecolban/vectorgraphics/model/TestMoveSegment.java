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

public class TestMoveSegment {

    private Double p0;
    private Path2D.Double path;
    private MoveSegment moveSegment;

    @Before
    public void setup() {
        p0 = new Double(1.0, 2.1);
        path = new Path2D.Double();
        double[] coordinates = new double[]{p0.getX(), p0.getY(), 0, 0, 0, 0};
        moveSegment = new MoveSegment(coordinates);
    }

    @Test
    public void testConstructor() {
        assertNotNull(moveSegment);
    }

    @Test
    public void testLength() {
        assertEquals(0.0, moveSegment.length(p0), 1e-6);
    }

    @Test
    public void testAddTo() {
        assertEquals(0, TestUtils.getPathLength(path));moveSegment.addTo(path);
        assertEquals(1, TestUtils.getPathLength(path));assertEquals(p0, path.getCurrentPoint());
        double[] coords = new double[6];
        int type = TestUtils.getLastSegment(path, coords);
        assertEquals(PathIterator.SEG_MOVETO, type);
        Point2D m0 = new Double(coords[0], coords[1]);
        TestUtils.assertEqualPoints(p0, m0);
    }

    @Test
    public void testAddToWithTime() {
        double t0 = ThreadLocalRandom.current().nextDouble();
        assertEquals(0, TestUtils.getPathLength(path));moveSegment.addTo(path, t0);
        assertEquals(1, TestUtils.getPathLength(path));double[] coords = new double[6];
        int type = TestUtils.getLastSegment(path, coords);
        assertEquals(PathIterator.SEG_MOVETO, type);
        Point2D m0 = new Double(coords[0], coords[1]);
        for (double t = 0.0; t <= 1.0; t += 0.01) {
            TestUtils.assertEqualPoints(p0, m0);
        }
    }
}
