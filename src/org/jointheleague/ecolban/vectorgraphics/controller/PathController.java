package org.jointheleague.ecolban.vectorgraphics.controller;

import org.jointheleague.ecolban.vectorgraphics.model.PartialPath;

import javax.swing.*;
import java.awt.geom.Path2D;

public class PathController {


    private PartialPath partialPath;

    private Timer ticker = new Timer(10, e -> onTick());

    private RepaintListener repaintListner;

    public void setRepaintListner(RepaintListener repaintListner) {
        this.repaintListner = repaintListner;
        partialPath = new PartialPath(repaintListner.getPathIterator("Merry Xmas"));
        ticker.start();
    }

    private void onTick() {
        partialPath.incrementTime(2.0);
        if (partialPath.isComplete()) ticker.stop();
        repaintListner.repaint();
    }

    public Path2D getPath2D() {
        return partialPath == null ? new Path2D.Double() : partialPath.getPath(null);
    }
}
