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

    public CellDTO(Coordinate coordinate, String originalValue, Object effectiveValue, int lastVersionChanged, List<Coordinate> affectedBy, List<Coordinate> affecting) {
        this.coordinate = coordinate;
        this.originalValue = originalValue;
        this.effectiveValue = effectiveValue;
        this.lastVersionChanged = lastVersionChanged;
        this.affectedBy = affectedBy;
        this.affecting = affecting;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
    public String getOriginalValue() {
        return originalValue;
    }
    public Object getEffectiveValue() {
        return effectiveValue;
    }
    public int getLastVersionChanged() {
        return lastVersionChanged;
    }
    public List<Coordinate> getAffectedBy() {
        return affectedBy;
    }
    public List<Coordinate> getAffecting() {
        return affecting;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }
    public void setEffectiveValue(Object effectiveValue) {
        this.effectiveValue = effectiveValue;
    }
    public void setLastVersionChanged(int lastVersionChanged) {
        this.lastVersionChanged = lastVersionChanged;
    }
    public void setAffectedBy(List<Coordinate> affectedBy) {
        this.affectedBy = affectedBy;
    }
    public void setAffecting(List<Coordinate> affecting) {
        this.affecting = affecting;
    }


}
