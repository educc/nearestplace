package com.ecacho.nearestplace.algorithm.internal;

/* *****************************************************************************
 *  Name: Edward Cacho Casas
 *  Date: January 5 2020
 *  Description: Complex if fun =)
 **************************************************************************** */


import com.ecacho.nearestplace.algorithm.FindNearest;
import com.ecacho.nearestplace.algorithm.LocationItem;
import edu.princeton.cs.algs4.Point2D;

import java.util.LinkedList;
import java.util.List;

public class BruteForce implements FindNearest {
    private List<LocationItem> points;

    public BruteForce() {
        points = new LinkedList<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public boolean contains(LocationItem p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (LocationItem item : points) {
            item.getPoint2D().draw();
        }
    }

    @Override
    public void insert(LocationItem p) {
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }

    public LocationItem nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        LocationItem first = points.iterator().next();
        double min = first.getPoint2D().distanceTo(p);

        LocationItem nearest = first;

        for (LocationItem item : points) {
            Point2D point = item.getPoint2D();
            double distance = point.distanceTo(p);
            if (distance < min) {
                min = distance;
                nearest = item;
            }
        }
        return nearest;
    }

}
