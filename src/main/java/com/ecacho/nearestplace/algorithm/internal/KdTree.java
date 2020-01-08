package com.ecacho.nearestplace.algorithm.internal;

/* *****************************************************************************
 *  Name: Edward Cacho Casas
 *  Date: January 5 2020
 *  Description: Complex if fun =)
 **************************************************************************** */

import com.ecacho.nearestplace.algorithm.FindNearest;
import com.ecacho.nearestplace.algorithm.LocationItem;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class KdTree implements FindNearest {

    private Node root;

    private static final double MIN_X  = 0;
    private static final double MAX_X  = 1;
    private static final double MIN_Y  = 0;
    private static final double MAX_Y  = 1;

    public KdTree() { }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    @Override
    public void insert(LocationItem p) {
        if (p == null) throw new IllegalArgumentException();
        root = put(root, root, p, 0);
    }

    @Override
    public boolean contains(LocationItem p) {
        if (p == null) throw new IllegalArgumentException();
        Node current = root;
        while (current != null) {
            int cmp = current.compareTo(p);
            if (cmp < 0) {
                current = current.leftOrBottom;
            } else if (cmp > 0) {
                current = current.rightOrTop;
            } else {
                return true;
            }
        }
        return false;
    }


    // draw all points to standard draw
    public void draw() {
        draw(root);
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public LocationItem nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        return nearest(p, root, root.value);
    }

    // **************************************************************
    // **************************************************************
    // PRIVATE
    // **************************************************************
    // **************************************************************

    private void draw(Node node) {
        if (node == null) return;
        draw(node.leftOrBottom);
        draw(node.rightOrTop);

        StdDraw.setPenColor(Color.BLACK);

        StdDraw.setPenRadius(0.02);
        node.value.getPoint2D().draw();


        StdDraw.setPenRadius(0.002);
        boolean isRed = node.level % 2 == 0;
        if (isRed) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.line(
                    node.value.getPoint2D().x(),node.rectangle.ymin(),
                    node.value.getPoint2D().x(),node.rectangle.ymax()
            );
        } else {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.line(
                    node.rectangle.xmin(),node.value.getPoint2D().y(),
                    node.rectangle.xmax(),node.value.getPoint2D().y()
            );
        }
    }

    private LocationItem nearest(Point2D query, Node current, LocationItem closest) {
        double distance = current.value.getPoint2D().distanceTo(query);
        if (distance < query.distanceTo(closest.getPoint2D())) {
            closest = current.value;
        }

        boolean leftNodeWasVisited = false;

        if (current.leftOrBottom != null &&
                current.leftOrBottom.rectangle.contains(query)) {
            closest = nearest(query, current.leftOrBottom, closest);
            leftNodeWasVisited = true;
        }

        boolean rightNodeWasVisited = false;
        if (current.rightOrTop != null &&
                current.rightOrTop.rectangle.contains(query)) {
            closest = nearest(query, current.rightOrTop, closest);
            rightNodeWasVisited = true;
        }

        // verify if is necessary to visit
        if (!leftNodeWasVisited && current.leftOrBottom != null) {
            if (canBeExistsClosest(query, closest.getPoint2D(), current.leftOrBottom)) {
                closest = nearest(query, current.leftOrBottom, closest);
            }

        }
        if (!rightNodeWasVisited && current.rightOrTop != null) {
            if (canBeExistsClosest(query, closest.getPoint2D(), current.rightOrTop)) {
                closest = nearest(query, current.rightOrTop, closest);
            }
        }
        //StdOut.println(closest);
        return closest;
    }

    private boolean canBeExistsClosest(Point2D queryPoint,
                                       Point2D closest,
                                       Node node) {
        Point2D minPoint = null;

        // find a nearest point between a point and a rectangle

        //          1  |      2      |   3
        //        -----|-------------|-------
        //             |             |
        //          4  |      5      |   6
        //             |             |
        //        -----|-------------|-------
        //          7  |      8      |   9

        if (queryPoint.y() <= node.rectangle.ymax() &&
            queryPoint.y() >= node.rectangle.ymin()) { // on lateral (4 or 6)

            if (queryPoint.x() <= node.rectangle.xmin()) { // on left (4)
                minPoint = new Point2D(node.rectangle.xmin(), queryPoint.y());
            } else { // on right (6)
                minPoint = new Point2D(node.rectangle.xmax(), queryPoint.y());
            }

        } else if (queryPoint.x() <= node.rectangle.xmax() &&
                    queryPoint.x() >= node.rectangle.xmin()) { // on top or bottom (2 or 8)

            if (queryPoint.y() >= node.rectangle.ymax()) { // on top (2)
                minPoint = new Point2D(queryPoint.x(), node.rectangle.ymax());
            } else { // on bottom (8)
                minPoint = new Point2D(queryPoint.x(), node.rectangle.ymin());
            }
        } else { // any corners (1 or 3 or 7 or 9)

            if (queryPoint.y() > node.rectangle.ymax() &&
                queryPoint.x() < node.rectangle.xmin()) { // top left (1)
                minPoint = new Point2D(node.rectangle.xmin(), node.rectangle.ymax());
            } else if (queryPoint.y() > node.rectangle.ymax() &&
                        queryPoint.x() > node.rectangle.xmax()) { // top right (3)
                minPoint = new Point2D(node.rectangle.xmax(), node.rectangle.ymax());
            } else if (queryPoint.y() < node.rectangle.ymin() &&
                        queryPoint.x() < node.rectangle.xmin()) { // bottom left (7)
                minPoint = new Point2D(node.rectangle.xmin(), node.rectangle.ymin());
            } else { // bottom right (9)
                minPoint = new Point2D(node.rectangle.xmax(), node.rectangle.ymin());
            }
        }

        //StdOut.println(queryPoint);
        //StdOut.println(String.format("canBe: even = %d, cmp = %d", evenLevel?1:0, cmp));

        // calc distance
        double distanceMinPoint = queryPoint.distanceTo(minPoint);
        double distanceClosest = queryPoint.distanceTo(closest);
        return distanceMinPoint < distanceClosest;
    }

    private Node put(Node node, Node parent, LocationItem value, int level) {
        if (node == null) return new Node(value, level, parent);

        int compare = node.compareTo(value);
        if (compare == 0) { // equals
            node.value = value;
        }
        else if (compare < 0) { // lesser
            node.leftOrBottom = put(node.leftOrBottom, node, value, level+1);
        }
        else { // higher
            node.rightOrTop = put(node.rightOrTop, node, value, level+1);
        }
        node.count = 1 + size(node.leftOrBottom) + size(node.rightOrTop);
        return node;
    }


    private int size(Node node) {
        if (node == null) return 0;
        return node.count;
    }

    private static class Node {
        LocationItem value;
        int level;
        Node leftOrBottom;
        Node rightOrTop;
        RectHV rectangle;
        int count = 1;

        public Node(LocationItem value, int level, Node parent) {
            this.value = value;
            this.level = level;
            createRectangle(parent);
        }

        public int compareTo(LocationItem another) {
            boolean isEven = isEvenLevel();
            if (value.equals(another.getPoint2D())) return 0;
            if (isEven) {
                return value.getPoint2D().x() < another.getPoint2D().x() ? 1 : -1;
            }
            return value.getPoint2D().y() < another.getPoint2D().y() ? 1 : -1;
        }

        private boolean isEvenLevel() {
            return level % 2 == 0;
        }

        private void createRectangle(Node parent) {
            if (parent == null) {
                this.rectangle = new RectHV(MIN_X, MIN_Y, MAX_X, MAX_Y);
                return;
            }

            boolean evenLevel = isEvenLevel();
            int cmp = parent.compareTo(this.value);


            if ( evenLevel && cmp == -1) { // vertical bottom
                this.rectangle = new RectHV(
                        parent.rectangle.xmin(),
                        parent.rectangle.ymin(),
                        parent.rectangle.xmax(),
                        parent.value.getPoint2D().y()
                );
            }
            if ( evenLevel && cmp == 1) { // vertical top
                this.rectangle = new RectHV(
                        parent.rectangle.xmin(),
                        parent.value.getPoint2D().y(),
                        parent.rectangle.xmax(),
                        parent.rectangle.ymax()
                );
            }

            if ( !evenLevel && cmp == -1) { // horizontal left
                this.rectangle = new RectHV(
                        parent.rectangle.xmin(),
                        parent.rectangle.ymin(),
                        parent.value.getPoint2D().x(),
                        parent.rectangle.ymax()
                );
            }
            if ( !evenLevel && cmp == 1) { // horizontal right
                this.rectangle = new RectHV(
                        parent.value.getPoint2D().x(),
                        parent.rectangle.ymin(),
                        parent.rectangle.xmax(),
                        parent.rectangle.ymax()
                );
            }
        }
    }

}
