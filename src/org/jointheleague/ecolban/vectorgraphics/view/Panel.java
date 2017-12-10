package org.jointheleague.ecolban.vectorgraphics.view;

import org.jointheleague.ecolban.vectorgraphics.controller.RepaintListener;
import org.jointheleague.ecolban.vectorgraphics.controller.PathController;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;

public class Panel extends JPanel implements RepaintListener {

    private static final Font BIG = new Font("Times New Roman", Font.PLAIN, 144);
    private static final int MARGIN = 5;
    private JFrame frame;
    private PathController pathController;
    private Shape outline;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Panel()::setUpGui);
    }

    private void setUpGui() {
        frame = new JFrame("Vector Graphics");
        frame.add(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(100, 50));
        frame.pack();
        frame.setVisible(true);
        pathController = new PathController();
        pathController.setRepaintListner(this);
    }

    public PathIterator getPathIterator(String text) {
        Graphics2D g2 = (Graphics2D) getGraphics();
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout layout = new TextLayout(text, BIG, frc);
        Rectangle2D bounds = layout.getBounds();
        Dimension preferredSize = new Dimension(
                (int) bounds.getWidth() + 2 * MARGIN,
                (int) bounds.getHeight() + 2 * MARGIN);
        setPreferredSize(preferredSize);
        frame.pack();
        outline = layout.getOutline(AffineTransform.getTranslateInstance(
                MARGIN - bounds.getX(),
                MARGIN - bounds.getY()));
        return outline.getPathIterator(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setFont(BIG);
        g2.setColor(Color.BLACK);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.draw(pathController.getPath2D());
    }
}
