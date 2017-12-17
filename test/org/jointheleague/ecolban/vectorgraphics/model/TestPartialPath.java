package org.jointheleague.ecolban.vectorgraphics.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TestPartialPath {

    private Point2D p0;
    private PartialPath partialPath;
    private boolean isNotified = false;


    @Before
    public void setup() {
        p0 = new Point2D.Double(1.0, 2.1);
        Point2D p1 = new Point2D.Double(3.0, 6.1);
        Point2D p2 = new Point2D.Double(6.0, 1.2);
        Point2D p3 = new Point2D.Double(9.1, 1.0);
        Point2D p4 = new Point2D.Double(2.1, 2.0);
        Point2D p5 = new Point2D.Double(3.1, 1.0);
        Point2D p6 = new Point2D.Double(0.1, 10.0);
        Path2D.Double path = new Path2D.Double();
        path.moveTo(p0.getX(), p0.getY());
        path.lineTo(p1.getX(), p1.getY());
        path.quadTo(p2.getX(), p2.getY(), p3.getX(), p3.getY());
        path.curveTo(p4.getX(), p4.getY(), p5.getX(), p5.getY(), p6.getX(), p6.getY());
        path.closePath();
        PathIterator pathIterator = path.getPathIterator(null);
        partialPath = new PartialPath(pathIterator);
    }

    @Test
    public void testConstructor() {
        assertNotNull(partialPath);
    }

    @Test
    public void testIsComplete() {
        assertFalse(partialPath.isComplete());
    }

    @Test
    public void testGetPath() {
        Path2D currentPath = partialPath.getPath2D();
        assertEquals(1, TestUtils.getPathLength(currentPath));
        assertEquals(p0, currentPath.getCurrentPoint());
        assertEquals(1, TestUtils.getPathLength(currentPath));
    }

    @Test
    public void testIncrementTime() {
        Path2D currentPath = partialPath.getPath2D();
        assertEquals(1, TestUtils.getPathLength(currentPath));
        double[] coords = new double[6];
        while (!partialPath.isComplete()) {
            partialPath.incrementTime(1.0);
            currentPath = partialPath.getPath2D();
            int type = TestUtils.getLastSegment(currentPath, coords);
            int numSegments = TestUtils.getPathLength(currentPath);
            switch (numSegments) {
                case 1:
                    assertEquals(PathIterator.SEG_MOVETO, type);
                    break;
                case 2:
                    assertEquals(PathIterator.SEG_LINETO, type);
                    break;
                case 3:
                    assertEquals(PathIterator.SEG_QUADTO, type);
                    break;
                case 4:
                    assertEquals(PathIterator.SEG_CUBICTO, type);
                    break;
                case 5:
                    assertTrue(PathIterator.SEG_LINETO == type || PathIterator.SEG_CLOSE == type);
                    break;
                default:
                    fail("The number of segments should be between 1 and 5.");
            }
        }
    }

    @Test
    public void testObserversNotified() {

        partialPath.addObserver((o, arg) -> isNotified = true);
        while (!partialPath.isComplete()) {
            isNotified = false;
            partialPath.incrementTime(1.0);
            assertTrue(isNotified);
        }
        assertTrue(isNotified);
    }

}
