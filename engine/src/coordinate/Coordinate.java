package coordinate;

public interface Coordinate extends Cloneable{
    int getRow();
    int getColumn();
    CoordinateDTO convertToDTO();
    Coordinate clone();
    String toString();
}