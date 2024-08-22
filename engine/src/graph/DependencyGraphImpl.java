package graph;

import coordinate.Coordinate;

import java.util.*;

public class DependencyGraphImpl implements DependencyGraph {
    private Map<Coordinate, List<Coordinate>> adjList;

    public DependencyGraphImpl() {
        this.adjList = new HashMap<>();
    }


    @Override
    public void addEdge(Coordinate from, Coordinate to) {
        adjList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }

    @Override
    public void removeEdge(Coordinate from, Coordinate to) {
        List<Coordinate> edges = adjList.get(from);
        if (edges != null) {
            edges.remove(to);
        }
    }

    @Override
    public List<Coordinate> topologicalSort() throws Exception {
        Map<Coordinate, Boolean> visited = new HashMap<>();
        Map<Coordinate, Boolean> inRecStack = new HashMap<>();
        Stack<Coordinate> stack = new Stack<>();

        for (Coordinate coord : adjList.keySet()) {
            if (!visited.containsKey(coord)) {
                topologicalSortUtil(coord, visited, inRecStack, stack);
            }
        }

        List<Coordinate> sortedCoordinates = new ArrayList<>();
        while (!stack.isEmpty()) {
            sortedCoordinates.add(stack.pop());
        }

        return sortedCoordinates;
    }


    @Override
    public void ExpandGraph(Coordinate coordinate) {
        if (!adjList.containsKey(coordinate)) {
            adjList.put(coordinate, new ArrayList<>());
        }
    }

    public void removeCoordinate(Coordinate coordinate) {
        adjList.remove(coordinate);

    }

    private void topologicalSortUtil(Coordinate coord, Map<Coordinate, Boolean> visited,
                                     Map<Coordinate, Boolean> inRecStack, Stack<Coordinate> stack) throws Exception {
        if (inRecStack.getOrDefault(coord, false)) {
            throw new Exception("A Cycle was detected in the sheet, cells are depending on each other. Command was cancelled.");
        }

        if (visited.getOrDefault(coord, false)) {
            return; // Node has already been processed
        }

        // Mark the node as visited and part of the recursion stack
        visited.put(coord, true);
        inRecStack.put(coord, true);

        List<Coordinate> dependencies = adjList.get(coord);
        if (dependencies != null) {
            for (Coordinate dependent : dependencies) {
                topologicalSortUtil(dependent, visited, inRecStack, stack);
            }
        }

        // Remove from the recursion stack and push to stack
        inRecStack.put(coord, false);
        stack.push(coord);
    }
}
