package org.jointheleague.ecolban.vectorgraphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Panel extends JPanel implements Runnable, ActionListener {

	private final Point2D p0 = new Point2D.Double();
	private final Point2D p1 = new Point2D.Double();
	private final Point2D p2 = new Point2D.Double();
	private final Point2D p3 = new Point2D.Double();
	private final Point2D[] points = { p0, p1, p2, p3 };
	private Point2D selected;

	private final Timer ticker = new Timer(10, this);

	private double progressTime = 0.0;

	private final Stroke THICK = new BasicStroke(5);
	private final Font BIG = new Font("Serif", Font.PLAIN, 288);

	public static void main(String[] args) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontFamilies = ge.getAvailableFontFamilyNames();
		for (String s : fontFamilies) {
			System.out.println(s);
		}
		SwingUtilities.invokeLater(new Panel());
	}

	@Override
	public void run() {
		setUpGui();
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
		CubicSegment cubic = new CubicSegment(p0, p1, p2, p3);
		cubic.draw(g2, 1.0);
		g2.setColor(Color.BLACK);
		g2.setStroke(THICK);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		cubic.draw(g2, progressTime);
	}

	private void onMousePressed(MouseEvent e) {
		for (Point2D p : points) {
			if (Math.abs(e.getX() - p.getX()) < 3 && Math.abs(e.getY() - p.getY()) < 5) {
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
			progressTime = 0.0;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		progressTime += 0.01;
		if (progressTime >= 1.0) {
			ticker.stop();
			progressTime = 1.0;
		}
		repaint();
	}

}
