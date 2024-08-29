package graph;

import coordinate.Coordinate;
import coordinate.CoordinateDTO;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class DependencyGraphImpl implements DependencyGraph, Serializable {
    private Map<Coordinate, List<Coordinate>> adjList;

    public DependencyGraphImpl() {
        this.adjList = new HashMap<>();
    }

    @Override
    public DependencyGraph clone()
    {
        try{
            DependencyGraphImpl newGraph = (DependencyGraphImpl) super.clone();
            newGraph.adjList = new HashMap<>();
            for(Map.Entry<Coordinate, List<Coordinate>> entry : this.adjList.entrySet()) {
               Coordinate from = entry.getKey();
               List<Coordinate> to = new ArrayList<>(entry.getValue());
               newGraph.adjList.put(from, to);
            }
            return newGraph;
        }
        catch(CloneNotSupportedException e){
            return null;
        }
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
    public void ExpandGraph(Coordinate coordinate) {
        if(!adjList.containsKey(coordinate)) {
            adjList.put(coordinate, new ArrayList<>());
        }
    }

    @Override
    public void ExpandGraph(Coordinate coordinateThatPoints, Coordinate coordinate){
        if(!adjList.containsKey(coordinateThatPoints)) {
            adjList.put(coordinateThatPoints, new ArrayList<>());
        }
        if(!adjList.get(coordinateThatPoints).contains(coordinate)) {
            adjList.get(coordinateThatPoints).add(coordinate);
        }
    }

    public void removeCoordinate(Coordinate coordinate) {
        adjList.remove(coordinate);
    }

    @Override
    public List<CoordinateDTO> getIncomingEdgesDTO(Coordinate coord) {
        List<Coordinate> incomingEdges = new ArrayList<>();
        for (Map.Entry<Coordinate, List<Coordinate>> entry : adjList.entrySet()) {
            if (entry.getValue().contains(coord)) {
                if(!incomingEdges.contains(entry.getKey())){
                    incomingEdges.add(entry.getKey());
                }
            }
        }

        return incomingEdges.stream().map(Coordinate::convertToDTO).toList();
    }

    public List<CoordinateDTO> getOutgoingEdgesDTO(Coordinate coord)
    {
        return this.adjList.get(coord).stream().map(Coordinate::convertToDTO).toList();
    }

    @Override
    public List<Coordinate> getIncomingEdges(Coordinate coord) {
        return this.adjList.get(coord);
    }

    @Override
    public List<Coordinate> topologicalOrder() throws IllegalStateException {
        Map<Coordinate, Boolean> visited = new HashMap<>();
        Map<Coordinate, Boolean> inPath = new HashMap<>();
        List<Coordinate> order = new ArrayList<>();

        for (Coordinate node : adjList.keySet()) {
            if (!visited.containsKey(node)) {
                if (!dfs(node, visited, inPath, order)) {
                    throw new IllegalStateException("A Cycle was detected in the sheet, cells are depending on each other.");
                }
            }
        }
        Collections.reverse(order);
        return order;
    }

    private boolean dfs(Coordinate node, Map<Coordinate, Boolean> visited, Map<Coordinate, Boolean> inPath, List<Coordinate> order) {
        visited.put(node, true);
        inPath.put(node, true);

        if (adjList.containsKey(node)) {
            for (Coordinate neighbor : adjList.get(node)) {
                if (!visited.containsKey(neighbor)) {
                    if (!dfs(neighbor, visited, inPath, order)) {
                        return false;
                    }
                } else if (inPath.get(neighbor)) {
                    return false;
                }
            }
        }

        inPath.put(node, false);
        order.add(node);
        return true;
    }

    private void topologicalSortUtil(Coordinate coord, Map<Coordinate, Boolean> visited,
                                     Map<Coordinate, Boolean> inRecStack, Stack<Coordinate> stack) throws Exception {
        if (inRecStack.getOrDefault(coord, false)) {
            throw new Exception("A Cycle was detected in the sheet, cells are depending on each other. Command was cancelled.");
        }

        if (visited.getOrDefault(coord, false)) {
            return;
        }

        visited.put(coord, true);
        inRecStack.put(coord, true);

        List<Coordinate> dependencies = adjList.get(coord);
        if (dependencies != null) {
            for (Coordinate dependent : dependencies) {
                topologicalSortUtil(dependent, visited, inRecStack, stack);
            }
        }

        inRecStack.put(coord, false);
        stack.push(coord);
    }
}
