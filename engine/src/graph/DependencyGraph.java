package graph;

import coordinate.Coordinate;
import coordinate.CoordinateDTO;

import java.util.List;
import java.util.Map;

public interface DependencyGraph extends Cloneable{
    public void addEdge(Coordinate from, Coordinate to);
    public void removeEdge(Coordinate from, Coordinate to);
    public void ExpandGraph(Coordinate coordinate);
    public void ExpandGraph(Coordinate coordinateThatPoints, Coordinate coordinate);
    public List<CoordinateDTO> getIncomingEdgesDTO(Coordinate coord);
    public List<CoordinateDTO> getOutgoingEdgesDTO(Coordinate coord);
    public List<Coordinate> getIncomingEdges(Coordinate coord);
    public DependencyGraph clone();
    public List<Coordinate> topologicalOrder() throws IllegalStateException;
}
