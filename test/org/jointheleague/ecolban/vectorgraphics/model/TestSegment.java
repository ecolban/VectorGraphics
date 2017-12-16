package org.jointheleague.ecolban.vectorgraphics.model;

import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.fail;

public class TestSegment {

    @Test
    public void testAffineCombo() {
        Point2D p0 = new Point2D.Double(1.0, 2.1);
        Point2D p1 = new Point2D.Double(3.0, 6.1);
        Point2D p2 = new Point2D.Double(4.1, 6.2);
        try {
            Segment.affineCombo(null, null);
            fail("Should fail");
        } catch (IllegalArgumentException e) {
        }
        try {
            Segment.affineCombo(null, new double[]{1.1, 2.1});
            fail("Should fail");
        } catch (IllegalArgumentException e) {
        }
        try {
            Segment.affineCombo(new Point2D[]{p0, p1, p2}, null);
            fail("Should fail");
        } catch (IllegalArgumentException e) {
        }
        try {
            Segment.affineCombo(new Point2D[]{p0, p1, p2}, new double[]{1.1, 2.1});
            fail("Should fail");
        } catch (IllegalArgumentException e) {
        }

        Point2D expected = new Point2D.Double(2.0, 4.1);
        Point2D actual = Segment.affineCombo(new Point2D[]{p0, p1}, new double[]{0.5, 0.5});
        TestUtils.assertEqualPoints(expected, actual);
    }
}
