package com.example.airspacecontrolcenter.util;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class QuadTree<T> {
    private static final int MAX_OBJECTS = 10;
    private static final int MAX_LEVELS = 5;

    private int level;
    private List<QuadTree<T>> nodes;
    private List<T> objects;
    private Rectangle2D bounds;

    public QuadTree(int level, Rectangle2D bounds) {
        this.level = level;
        this.bounds = bounds;
        this.nodes = new ArrayList<>();
        this.objects = new ArrayList<>();
    }

    public void clear() {
        objects.clear();
        for (QuadTree<T> node : nodes) {
            node.clear();
        }
        nodes.clear();
    }

    private void split() {
        double subWidth = bounds.getWidth() / 2;
        double subHeight = bounds.getHeight() / 2;
        double x = bounds.getX();
        double y = bounds.getY();

        nodes.add(new QuadTree<>(level + 1, new Rectangle2D.Double(x + subWidth, y, subWidth, subHeight)));
        nodes.add(new QuadTree<>(level + 1, new Rectangle2D.Double(x, y, subWidth, subHeight)));
        nodes.add(new QuadTree<>(level + 1, new Rectangle2D.Double(x, y + subHeight, subWidth, subHeight)));
        nodes.add(new QuadTree<>(level + 1, new Rectangle2D.Double(x + subWidth, y + subHeight, subWidth, subHeight)));
    }

    private int getIndex(Rectangle2D pRect) {
        int index = -1;
        double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
        double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

        // Object can completely fit within the top quadrants
        boolean topQuadrant = (pRect.getY() < horizontalMidpoint && pRect.getY() + pRect.getHeight() < horizontalMidpoint);
        // Object can completely fit within the bottom quadrants
        boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);

        // Object can completely fit within the left quadrants
        if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint) {
            if (topQuadrant) {
                index = 1;
            } else if (bottomQuadrant) {
                index = 2;
            }
        }
        // Object can completely fit within the right quadrants
        else if (pRect.getX() > verticalMidpoint) {
            if (topQuadrant) {
                index = 0;
            } else if (bottomQuadrant) {
                index = 3;
            }
        }

        return index;
    }

    public void insert(Rectangle2D pRect, T object) {
        if (nodes.size() > 0) {
            int index = getIndex(pRect);
            if (index != -1) {
                nodes.get(index).insert(pRect, object);
                return;
            }
        }

        objects.add(object);

        if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes.size() == 0) {
                split();
            }

            int i = 0;
            while (i < objects.size()) {
                int index = getIndex(pRect);
                if (index != -1) {
                    nodes.get(index).insert(pRect, objects.remove(i));
                } else {
                    i++;
                }
            }
        }
    }

    public List<T> retrieve(Rectangle2D pRect) {
        List<T> result = new ArrayList<>();
        int index = getIndex(pRect);
        if (index != -1 && nodes.size() > 0) {
            result.addAll(nodes.get(index).retrieve(pRect));
        }
        result.addAll(objects);
        return result;
    }
}

