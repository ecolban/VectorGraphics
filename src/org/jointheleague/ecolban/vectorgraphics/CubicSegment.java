package org.jointheleague.ecolban.vectorgraphics;

import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;

public class CubicSegment {
	private final Point2D p0;
	private final Point2D p1;
	private final Point2D p2;
	private final Point2D p3;

	public CubicSegment(Point2D p0, Point2D p1, Point2D p2, Point2D p3) {
		super();
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}

	private CubicCurve2D interpolate(double t) {
		double u = 1 - t;
		double tt = t * t;
		double tu = t * u;
		double uu = u * u;
		double ttt = tt * t;
		double ttu = tt * u;
		double tuu = tu * u;
		double uuu = uu * u;

		Point2D m0 = new Point2D.Double(
				u * p0.getX() + t * p1.getX(),
				u * p0.getY() + t * p1.getY());

		Point2D q0 = new Point2D.Double(
				uu * p0.getX() + 2 * tu * p1.getX() + tt * p2.getX(),
				uu * p0.getY() + 2 * tu * p1.getY() + tt * p2.getY());

		Point2D c0 = new Point2D.Double(
				uuu * p0.getX() + 3 * tuu * p1.getX() + 3 * ttu * p2.getX() + ttt * p3.getX(),
				uuu * p0.getY() + 3 * tuu * p1.getY() + 3 * ttu * p2.getY() + ttt * p3.getY());

		return new CubicCurve2D.Double(
				p0.getX(), p0.getY(),
				m0.getX(), m0.getY(),
				q0.getX(), q0.getY(),
				c0.getX(), c0.getY());
	}

	public void draw(Graphics2D g2, double t) {
		g2.draw(interpolate(t));
	}
}
