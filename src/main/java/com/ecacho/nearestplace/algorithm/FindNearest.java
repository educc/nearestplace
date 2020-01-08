package com.ecacho.nearestplace.algorithm;

import edu.princeton.cs.algs4.Point2D;

public interface FindNearest {
    void insert(LocationItem item);
    LocationItem nearest(Point2D point2D);
    boolean isEmpty();
    int size();
    boolean contains(LocationItem p);
}
