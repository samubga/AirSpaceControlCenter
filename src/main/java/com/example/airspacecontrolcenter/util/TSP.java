package com.example.airspacecontrolcenter.util;

import java.util.*;

public class TSP {

    private static final double INFINITY = Double.POSITIVE_INFINITY;

    public static double tsp(double[][] adjacencyMatrix, int[] path, int n, int pos, Map<Integer, Map<Boolean, Double>> memo) {
        if (n == 0) {
            return adjacencyMatrix[pos][0];
        }

        if (memo.containsKey(n) && memo.get(n).containsKey(pos == 0)) {
            return memo.get(n).get(pos == 0);
        }

        double minCost = INFINITY;
        for (int i = 1; i < n; i++) {
            if (adjacencyMatrix[pos][path[n - i]] > 0) {
                double cost = adjacencyMatrix[pos][path[n - i]] + tsp(adjacencyMatrix, path, n - i, path[n - i], memo);
                minCost = Math.min(minCost, cost);
            }
        }

        memo.putIfAbsent(n, new HashMap<>());
        memo.get(n).put(pos == 0, minCost);

        return minCost;
    }

    public static List<Integer> getOptimalPath(double[][] adjacencyMatrix) {
        int n = adjacencyMatrix.length;
        int[] path = new int[n];
        for (int i = 1; i < n; i++) {
            path[i] = i;
        }

        Map<Integer, Map<Boolean,Double>> memo = new HashMap<>();

        double minCost = tsp(adjacencyMatrix, path, n - 1, 0, memo);

        // Reconstruct the optimal path
        List<Integer> optimalPath = new ArrayList<>();
        int pos = 0;
        optimalPath.add(0);
        for (int i = 1; i < n; i++) {
            double minCostLocal = INFINITY;
            int nextCity = -1;
            for (int j = 1; j < n; j++) {
                if (pos >= 0 && adjacencyMatrix[pos][path[j]] > 0 && tsp(adjacencyMatrix, path, j, path[j], memo) < minCostLocal) {
                    minCostLocal = tsp(adjacencyMatrix, path, j, path[j], memo);
                    nextCity = path[j];
                }
            }
            optimalPath.add(nextCity);
            pos = nextCity;
        }

        return optimalPath;
    }
}
