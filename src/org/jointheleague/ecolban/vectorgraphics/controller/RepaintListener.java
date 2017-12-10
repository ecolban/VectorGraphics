package org.jointheleague.ecolban.vectorgraphics.controller;

import java.awt.geom.PathIterator;

public interface RepaintListener {

    void repaint();

    PathIterator getPathIterator(String text);
}