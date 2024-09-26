package range;

import coordinate.Coordinate;
import row.Row;

import java.util.List;

public interface Range extends Cloneable{
    List<Coordinate> getCoordinates();

    int getRowStart();

    int getRowEnd();

    int getColStart();

    int getColEnd();

    Range clone();
    String getName();
    RangeDTO getRangeDTO();
    void checkIfRangeCanBeDeleted() throws RuntimeException;
    void addCellToDependencies(Coordinate coordinate);
    void removeCellFromDependencies(Coordinate coordinate);
}
