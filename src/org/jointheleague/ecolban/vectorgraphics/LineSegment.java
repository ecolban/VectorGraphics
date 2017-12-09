package org.jointheleague.ecolban.vectorgraphics;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class LineSegment {
	private final Point2D p0;
	private final Point2D p1;

	public LineSegment(Point2D p0, Point2D p1) {
		super();
		this.p0 = p0;
		this.p1 = p1;
	}

	private Line2D interpolate(double t) {
		double u = 1 - t;
		
		Point2D m0 = new Point2D.Double(
				u * p0.getX() + t * p1.getX(),
				u * p0.getY() + t * p1.getY());
		
		return new Line2D.Double(p0, m0);
	}

	public void draw(Graphics2D g2, double t) {
		g2.draw(interpolate(t));
	}
}
