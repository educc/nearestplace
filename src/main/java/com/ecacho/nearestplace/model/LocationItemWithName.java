package com.ecacho.nearestplace.model;

import com.ecacho.nearestplace.algorithm.LocationItem;
import edu.princeton.cs.algs4.Point2D;

public class LocationItemWithName implements LocationItem {

    private String name;
    private Point2D point;

    public LocationItemWithName(String name, double x, double y) {
        this.name = name;
        this.point = new Point2D(x, y);
    }

    public String getName() {
        return name;
    }

    @Override
    public Point2D getPoint2D() {
        return point;
    }
}
