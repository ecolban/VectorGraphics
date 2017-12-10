package org.jointheleague.ecolban.vectorgraphics.controller;

import org.jointheleague.ecolban.vectorgraphics.model.PartialPath;

import javax.swing.*;

import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;

public class PathController {
	
	private static final int MARGIN = 20;
	private PartialPath partialPath;

	private Timer ticker = new Timer(10, e -> onTick());

	private PathView pathView;

	public PathController(PathView pathView) {
		this.pathView = pathView;
		createModels();
		ticker.start();
	}

	public void createModels() {
		TextLayout textLayout = pathView.getTextLayout("Merry Xmas");
		Rectangle2D bounds = textLayout.getBounds();
		Shape outline = textLayout.getOutline(
				AffineTransform.getTranslateInstance(MARGIN - bounds.getX(), MARGIN - bounds.getY()));
		PathIterator pathIterator = outline.getPathIterator(null);
		partialPath = new PartialPath(pathIterator);
		pathView.setSize((int) bounds.getWidth() + 2 * MARGIN, (int) bounds.getHeight()+ 2 * MARGIN);
		partialPath.addObserver(pathView);
	}

	private void onTick() {
		partialPath.incrementTime(2.0);
		if (partialPath.isComplete()) ticker.stop();
	}
}
