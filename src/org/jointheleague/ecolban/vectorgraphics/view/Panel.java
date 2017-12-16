package org.jointheleague.ecolban.vectorgraphics.view;

import org.jointheleague.ecolban.vectorgraphics.controller.PathController;
import org.jointheleague.ecolban.vectorgraphics.controller.PathView;
import org.jointheleague.ecolban.vectorgraphics.model.PartialPath;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

public class Panel extends JPanel implements PathView {

    private static final Font BIG = new Font("Times New Roman", Font.PLAIN, 144);
    private JFrame frame;
    private Set<PartialPath> models = new HashSet<>();

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Panel()::setUpGui);
    }

    private void setUpGui() {
        frame = new JFrame("Vector Graphics");
        frame.add(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 100));
        frame.pack();
        frame.setVisible(true);
        new PathController(this);
    }

    public TextLayout getTextLayout(String text) {
        Graphics2D g2 = (Graphics2D) getGraphics();
        FontRenderContext frc = g2.getFontRenderContext();
        return new TextLayout(text, BIG, frc);
    }

    public void setSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        frame.pack();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setFont(BIG);
        g2.setColor(Color.BLACK);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (PartialPath model : models) {
            g2.draw(model.getPath2D());
        }
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof PartialPath) {
            PartialPath model = (PartialPath) observable;
            if (!models.contains(model)) models.add(model);
        }
        repaint();
    }
}
