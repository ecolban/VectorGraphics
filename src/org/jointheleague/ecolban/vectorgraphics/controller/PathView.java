package org.jointheleague.ecolban.vectorgraphics.controller;

import java.awt.font.TextLayout;
import java.util.Observer;

public interface PathView extends Observer{

    TextLayout getTextLayout(String text);
	
	void setSize(int width, int height);
}