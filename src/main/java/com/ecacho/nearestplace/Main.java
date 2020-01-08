package com.ecacho.nearestplace;

import com.ecacho.nearestplace.algorithm.FindNearest;
import com.ecacho.nearestplace.algorithm.FindNearestFactory;
import com.ecacho.nearestplace.model.LocationItemWithName;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Main {

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        String algorithmName = args[0];
        String personFiles = args[1];
        String agenciesFiles = args[2];

        FindNearest findNearest = create(algorithmName, agenciesFiles);
        findNearestFromPoint(findNearest, personFiles);
        StdOut.println("time = " + stopwatch.elapsedTime());
    }

    private static void findNearestFromPoint(FindNearest findNearest, String personFiles) {
        StdOut.println("person,agency");

        In in = new In(personFiles);
        while (!in.isEmpty()) {
            String personName = in.readString();
            double x = in.readDouble();
            double y = in.readDouble();

            Point2D point2D = new Point2D(x, y);

            LocationItemWithName nearest = (LocationItemWithName) findNearest.nearest(point2D);

            String agencyName = nearest.getName();
            StdOut.printf("%s,%s\n", personName, agencyName);
        }
    }

    private static FindNearest create(String algorithmName, String agenciesFile) {
        FindNearest findNearest = FindNearestFactory.getInstance().get(algorithmName);
        if (findNearest == null) throw new IllegalArgumentException("Algorithm doesn't exist: " + algorithmName);

        In in = new In(agenciesFile);
        while (!in.isEmpty()) {
            String name = in.readString();
            double x = in.readDouble();
            double y = in.readDouble();
            findNearest.insert(new LocationItemWithName(name, x, y));
        }
        return findNearest;
    }


}
