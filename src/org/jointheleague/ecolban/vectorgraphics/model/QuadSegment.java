package org.jointheleague.ecolban.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class QuadSegment implements Segment {
	private final Point2D p1;
	private final Point2D p2;

	public QuadSegment(final double[] coordinates) {
		super();
		this.p1 = new Point2D.Double(coordinates[0], coordinates[1]);
		this.p2 = new Point2D.Double(coordinates[2], coordinates[3]);
	}

	@Override
	public boolean consumesTime() {
		return true;
	}

	@Override
	public double length(Point2D p0) {
		return p0.distance(p1) + p1.distance(p2);
	}

	@Override
	public Path2D addTo(Path2D path) {
		path.quadTo(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		return path;
	}

	@Override
	public Path2D addTo(Path2D path, double t) {
		final Point2D p0 = path.getCurrentPoint();
		final double u = 1 - t;
		final Point2D m0 = Segment.affineCombo(new Point2D[] { p0, p1 }, new double[] { u, t });
		final Point2D q0 = Segment.affineCombo(new Point2D[] { p0, p1, p2 }, new double[] { u * u, 2 * t * u, t * t });
		path.quadTo(m0.getX(), m0.getY(), q0.getX(), q0.getY());
		return path;
	}
}
