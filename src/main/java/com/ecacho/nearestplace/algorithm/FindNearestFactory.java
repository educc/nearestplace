package com.ecacho.nearestplace.algorithm;

import com.ecacho.nearestplace.algorithm.internal.BruteForce;
import com.ecacho.nearestplace.algorithm.internal.KdTree;

import java.util.HashMap;
import java.util.Map;

public class FindNearestFactory {

    private Map<String, FindNearest> mapFindNearest;

    private static FindNearestFactory factory;

    public FindNearestFactory() {
        this.mapFindNearest = new HashMap<>();
        this.mapFindNearest.put("brute-force", new BruteForce());
        this.mapFindNearest.put("kd-tree", new KdTree());
    }

    public static FindNearestFactory getInstance() {
        if (factory == null) factory = new FindNearestFactory();
        return factory;
    }

    public FindNearest get(String algorithmName) {
        return mapFindNearest.get(algorithmName);
    }
}
