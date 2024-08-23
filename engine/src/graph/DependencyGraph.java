package graph;

import coordinate.Coordinate;
import coordinate.CoordinateDTO;

import java.util.List;

public interface DependencyGraph {
    public void addEdge(Coordinate from, Coordinate to);
    public void removeEdge(Coordinate from, Coordinate to);
    public List<Coordinate> topologicalSort() throws Exception;
    public void ExpandGraph(Coordinate coordinate);
    public List<CoordinateDTO> getIncomingEdgesDTO(Coordinate coord);
    public List<CoordinateDTO> getOutgoingEdgesDTO(Coordinate coord);
    public List<Coordinate> getIncomingEdges(Coordinate coord);
}
