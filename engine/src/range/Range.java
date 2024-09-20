package range;

import coordinate.Coordinate;

import java.util.List;

public interface Range extends Cloneable{
    List<Coordinate> getCoordinates();
    Range clone();
    String getName();
    RangeDTO getRangeDTO();
    void checkIfRangeCanBeDeleted() throws RuntimeException;
    void addCellToDependencies(Coordinate coordinate);
    void removeCellFromDependencies(Coordinate coordinate);
}
