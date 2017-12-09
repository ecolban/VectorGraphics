package org.jointheleague.ecolban.vectorgraphics;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

public class QuadSegment {
	private final Point2D p0;
	private final Point2D p1;
	private final Point2D p2;

	public QuadSegment(Point2D p0, Point2D p1, Point2D p2) {
		super();
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
	}

	private QuadCurve2D interpolate(double t) {
		double u = 1 - t;
		double tt = t * t;
		double tu = t * u;
		double uu = u * u;

		Point2D m0 = new Point2D.Double(
				u * p0.getX() + t * p1.getX(),
				u * p0.getY() + t * p1.getY());

		Point2D q0 = new Point2D.Double(
				uu * p0.getX() + 2 * tu * p1.getX() + tt * p2.getX(),
				uu * p0.getY() + 2 * tu * p1.getY() + tt * p2.getY());

		return new QuadCurve2D.Double(
				p0.getX(), p0.getY(),
				m0.getX(), m0.getY(),
				q0.getX(), q0.getY());
	}

	public void draw(Graphics2D g2, double t) {
		g2.draw(interpolate(t));
	}
}
