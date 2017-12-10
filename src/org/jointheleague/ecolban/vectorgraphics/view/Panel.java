package org.jointheleague.ecolban.vectorgraphics.view;

import org.jointheleague.ecolban.vectorgraphics.model.PartialPath;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Panel extends JPanel {

    private static final Font BIG = new Font("Times New Roman", Font.PLAIN, 200);

    private Timer ticker = new Timer(10, e -> onTick());
    private PartialPath partialPath;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Panel()::setUpGui);
    }

    private void setUpGui() {
        JFrame frame = new JFrame("Vector Graphics");
        frame.add(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BufferedImage bufferedImage = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout layout = new TextLayout("$@#&", BIG, frc);
        Rectangle2D bounds = layout.getBounds();
        setPreferredSize(new Dimension((int) bounds.getWidth() + 40, (int) bounds.getHeight() + 40));
        frame.pack();
        frame.setVisible(true);
        Shape outline = layout.getOutline(AffineTransform.getTranslateInstance(20, getHeight() - 50));
        PathIterator pi = outline.getPathIterator(null);
        partialPath = new PartialPath(pi);
        ticker.start();
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setFont(BIG);
        g2.setColor(Color.BLACK);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.draw(partialPath.getPath(null));
    }

    private void onTick() {
        partialPath.incrementTime(1.0);
        if (partialPath.isComplete()) ticker.stop();
        repaint();
    }


}
