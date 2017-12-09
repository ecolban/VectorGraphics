package org.jointheleague.ecolban.vectorgraphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Panel extends JPanel {

	private final Point2D p0 = new Point2D.Double();
	private final Point2D p1 = new Point2D.Double();
	private final Point2D p2 = new Point2D.Double();
	// private final Point2D p3 = new Point2D.Double();
	private final Point2D[] points = { p0, p1, p2 };
	private Point2D selected;

	private Timer ticker = new Timer(10, e -> onTick());

	private double progressTime = 0.0;

	private final Stroke THICK = new BasicStroke(5);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Panel()::setUpGui);
	}

	private void setUpGui() {
		JFrame frame = new JFrame("Vector Graphics");
		frame.add(this);
		setPreferredSize(new Dimension(640, 400));
		frame.pack();
		ThreadLocalRandom rng = ThreadLocalRandom.current();
		for (Point2D p : points) {
			p.setLocation(rng.nextDouble(this.getWidth()), rng.nextDouble(getHeight()));
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		addMouseListeners();
	}

	private void addMouseListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				onMousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				onMouseReleased();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				onMouseDragged(e);
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.RED);
		for (Point2D p : points) {
			g2.fillRect((int) p.getX() - 3, (int) p.getY() - 3, 6, 6);
		}
		g2.setColor(Color.GRAY.brighter());
		QuadSegment cubic = new QuadSegment(p0, p1, p2);
		cubic.draw(g2, 1.0);
		g2.setColor(Color.BLACK);
		g2.setStroke(THICK);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (progressTime > 0) {
			cubic.draw(g2, progressTime);
		}
	}

	private void onTick() {
		progressTime += 0.01;
		if (progressTime >= 1.0) {
			ticker.stop();
			progressTime = 1.0;
		}
		repaint();
	}

	private void onMousePressed(MouseEvent e) {
		for (Point2D p : points) {
			if (Math.abs(e.getX() - p.getX()) < 5 && Math.abs(e.getY() - p.getY()) < 5) {
				selected = p;
				break;
			}
		}
		if (selected != null) {
			ticker.stop();
			progressTime = 0.0;
		}
	}

	private void onMouseReleased() {
		if (selected != null) {
			ticker.start();
			selected = null;
		}
	}

	private void onMouseDragged(MouseEvent e) {
		if (selected != null) {
			selected.setLocation(e.getX(), e.getY());
			repaint();
		}
	}
}
