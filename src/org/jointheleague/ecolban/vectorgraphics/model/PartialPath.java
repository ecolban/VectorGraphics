package org.jointheleague.ecolban.vectorgraphics.model;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Observable;

public class PartialPath extends Observable {

    private Segment currentSegment = null;
    private Path2D currentPath = new Path2D.Double();
    private double time = 0.0;
    private final PathIterator pathIterator;
    private Point2D startingPoint;

    public PartialPath(PathIterator pathIterator) {
        this.pathIterator = pathIterator;
        currentSegment = getCurrentSegment();
        assert currentSegment instanceof MoveSegment;
        currentSegment.addTo(currentPath);
        assert !pathIterator.isDone();
        pathIterator.next();
        currentSegment = getCurrentSegment();
    }

    private Segment getCurrentSegment() {
        assert !pathIterator.isDone();
        double[] coordinates = new double[6];
        int type = pathIterator.currentSegment(coordinates);
        switch (type) {
            case PathIterator.SEG_MOVETO:
                startingPoint = new Point2D.Double(coordinates[0], coordinates[1]);
                return new MoveSegment(coordinates);
            case PathIterator.SEG_LINETO:
                return new LineSegment(coordinates);
            case PathIterator.SEG_QUADTO:
                return new QuadSegment(coordinates);
            case PathIterator.SEG_CUBICTO:
                return new CubicSegment(coordinates);
            case PathIterator.SEG_CLOSE:
                return new CloseSegment(startingPoint);
            default:
                return null;
        }
    }

    public void incrementTime(double speed) {
        time += speed / currentSegment.length(currentPath.getCurrentPoint());
        if (time >= 1.0) {
            currentSegment.addTo(currentPath);
            pathIterator.next();
            if (!pathIterator.isDone()) {
                currentSegment = getCurrentSegment();
                time = 0.0;
            }
        }
        setChanged();
        notifyObservers();
    }

    public boolean isComplete() {
        return pathIterator.isDone();
    }

    public Path2D getPath2D() {
        if (0.0 < time && time < 1.0) {
            Path2D path = new Path2D.Double(currentPath);
            return currentSegment.addTo(path, time);
        }
        return currentPath;
    }
}
