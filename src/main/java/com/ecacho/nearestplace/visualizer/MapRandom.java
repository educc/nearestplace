package com.ecacho.nearestplace.visualizer;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class MapRandom {

    public static void main(String[] args) {
        int sizeBlack = Integer.parseInt(args[0]);
        int sizeGreen = Integer.parseInt(args[1]);

        List<Point2D> pointsBlack = randomPoints(sizeBlack);
        List<Point2D> pointsGreen = randomPoints(sizeGreen);

        StdDraw.enableDoubleBuffering();
        StdDraw.clear();

        paintMap(pointsBlack, Color.BLACK, 0.01);
        paintMap(pointsGreen, Color.RED, 0.03);

        StdDraw.show();
    }


    public static void paintMap(List<Point2D> points, Color color, double penRadius) {
        for (Point2D point : points) {
            StdDraw.setPenRadius(penRadius);
            StdDraw.setPenColor(color);
            point.draw();
        }

    }

    public static List<Point2D> randomPoints(int size) {
        List<Point2D> result = new LinkedList<>();
        int n = size;
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            result.add(new Point2D(x, y));
        }
        return result;
    }
}
