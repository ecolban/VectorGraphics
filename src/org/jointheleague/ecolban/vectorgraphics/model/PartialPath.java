package org.jointheleague.ecolban.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class PartialPath extends Observable {

    private List<Segment> segments = new ArrayList<>();
    private int currentSegment = 0;
    private double time = 0.0;

    public PartialPath(PathIterator pathIterator) {
        Point2D lastPoint = null;
        Point2D startingPoint = null;
        while (!pathIterator.isDone()) {
            double[] coordinates = new double[6];
            int type = pathIterator.currentSegment(coordinates);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    segments.add(new MoveSegment(coordinates));
                    startingPoint = new Point2D.Double(coordinates[0], coordinates[1]);
                    lastPoint = startingPoint;
                    break;
                case PathIterator.SEG_LINETO:
                    segments.add(new LineSegment(lastPoint, coordinates));
                    lastPoint = new Point2D.Double(coordinates[0], coordinates[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    segments.add(new QuadSegment(lastPoint, coordinates));
                    lastPoint = new Point2D.Double(coordinates[2], coordinates[3]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    segments.add(new CubicSegment(lastPoint, coordinates));
                    lastPoint = new Point2D.Double(coordinates[4], coordinates[5]);
                case PathIterator.SEG_CLOSE:
                    segments.add(new CloseSegment(lastPoint, startingPoint));
                default:
            }
            pathIterator.next();
        }
    }

    public void incrementTime(double speed) {
        if (isComplete()) return;

        time += speed / segments.get(currentSegment).size();
        if (time > 1.0) time = 1.0;
        while (time >= 1.0 && currentSegment < segments.size() - 1) {
            currentSegment++;
            if (segments.get(currentSegment).consumesTime()) {
                time = 0.0;
            }
        }
        setChanged();
        notifyObservers();
    }

    public boolean isComplete() {
        return currentSegment == segments.size() - 1 && time >= 1.0;
    }

    public Path2D getPath2D() {
        Path2D path = new Path2D.Double();
        for (int i = 0; i < currentSegment; i++) {
            segments.get(i).addTo(path);
        }
        if (0.0 < time) {
            if (time < 1.0) {
                segments.get(currentSegment).addTo(path, time);
            } else {
                segments.get(currentSegment).addTo(path);
            }
        }
        return path;
    }
}
