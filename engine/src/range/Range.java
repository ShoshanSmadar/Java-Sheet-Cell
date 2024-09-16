package range;

import coordinate.Coordinate;

import java.util.List;

public interface Range extends Cloneable{
    List<Coordinate> getCoordinates();
    Range clone();
    String getName();
}
