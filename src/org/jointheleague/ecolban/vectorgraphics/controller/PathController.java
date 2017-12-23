package org.jointheleague.ecolban.vectorgraphics.controller;

import org.jointheleague.ecolban.vectorgraphics.model.PartialPath;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

public class PathController {

    private Set<PartialPath> partialPaths = new HashSet<>();;

    private Timer ticker = new Timer(10, e -> onTick());

    private PathView pathView;

    public PathController(PathView pathView) {
        this.pathView = pathView;
        createModels();
        ticker.start();
    }

    public void createModels() {
        final String text = "Awesome!";
        final Font font = new Font("Times New Roman", Font.PLAIN, 144);
        TextLayout textLayout = pathView.getTextLayout(text, font);
        Rectangle2D bounds = textLayout.getBounds();
        Dimension dimension = pathView.getSize();
        double leftMargin = (dimension.getWidth() - bounds.getWidth()) / 2;
        double topMargin = (dimension.getHeight() - bounds.getHeight()) / 2;
        Shape outline = textLayout.getOutline(
                AffineTransform.getTranslateInstance(leftMargin - bounds.getX(), topMargin - bounds.getY()));
        PathIterator pathIterator = outline.getPathIterator(null);
        PartialPath partialPath = new PartialPath(pathIterator);
        partialPaths.add(partialPath);
        partialPath.addObserver(pathView);
    }

    private void onTick() {
        int numAlive = 0;
        for (PartialPath p : partialPaths) {
            if (!p.isComplete())
                p.incrementTime(2.0);
                numAlive++;
        }
        if (numAlive <= 0) ticker.stop();
    }
}
