package cell;

import coordinate.Coordinate;

import java.util.List;

public class CellDTO {
    protected Coordinate coordinate;
    protected String originalValue;
    protected Object effectiveValue;
    protected int lastVersionChanged;
    protected List<Coordinate> affectedBy;
    protected List<Coordinate> affecting;

    public  CellDTO(Coordinate coordinate, String originalValue, Object effectiveValue, int lastVersionChanged, List<Coordinate> affectedBy, List<Coordinate> affecting) {
        this.coordinate = coordinate;
        this.originalValue = originalValue;
        this.effectiveValue = effectiveValue;
        this.lastVersionChanged = lastVersionChanged;
        this.affectedBy = affectedBy;
        this.affecting = affecting;
    }



}
