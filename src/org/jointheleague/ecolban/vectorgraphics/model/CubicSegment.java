package org.jointheleague.ecolban.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class CubicSegment implements Segment {
	private final Point2D p1;
	private final Point2D p2;
	private final Point2D p3;

	public CubicSegment(final double[] coordinates) {
		super();
		this.p1 = new Point2D.Double(coordinates[0], coordinates[1]);
		this.p2 = new Point2D.Double(coordinates[2], coordinates[3]);
		this.p3 = new Point2D.Double(coordinates[4], coordinates[5]);
	}

	@Override
	public boolean consumesTime() {
		return true;
	}

	@Override
	public double length(Point2D p0) {
		return p0.distance(p1) + p1.distance(p2) + p2.distance(p3);
	}

	@Override
	public Path2D addTo(Path2D path) {
		path.curveTo(
				p1.getX(), p1.getY(),
				p2.getX(), p2.getY(),
				p3.getX(), p3.getY());
		return path;
	}

	@Override
	public Path2D addTo(Path2D path, double t) {
		final Point2D p0 = path.getCurrentPoint();
		final double u = 1 - t;
		final double uu = u * u;
		final double ut = u * t;
		final double tt = t * t;
		final Point2D m0 = Segment.affineCombo(
				new Point2D[] { p0, p1 },
				new double[] { u, t });
		final Point2D q0 = Segment.affineCombo(
				new Point2D[] { p0, p1, p2 },
				new double[] { uu, 2 * ut, tt });
		final Point2D c0 = Segment.affineCombo(
				new Point2D[] { p0, p1, p2, p3 },
				new double[] { uu * u, 3 * uu * t, 3 * ut * t, tt * t });
		path.curveTo(
				m0.getX(), m0.getY(),
				q0.getX(), q0.getY(),
				c0.getX(), c0.getY());
		return path;
	}
}
