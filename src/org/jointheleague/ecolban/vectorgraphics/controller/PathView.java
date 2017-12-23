package org.jointheleague.ecolban.vectorgraphics.controller;

import java.awt.*;
import java.awt.font.TextLayout;
import java.util.Observer;

public interface PathView extends Observer {

    /**
     * Get the text layout of a text string in a given font
     * @param text a text string
     * @param font a font
     * @return the text layout
     */
    TextLayout getTextLayout(String text, Font font);

    /**
     * Returns the dimensions of the panel in which something can be drawn
     * @return the dimension of the panel
     */
    Dimension getSize();
}