package cell;

import coordinate.Coordinate;
import coordinate.CoordinateDTO;

import java.util.ArrayList;
import java.util.List;

public class CellDTO {
    protected CoordinateDTO coordinate;
    protected String originalValue;
    protected Object effectiveValue;
    protected int lastVersionChanged;
    protected List<CoordinateDTO> affectedBy;
    protected List<CoordinateDTO> affecting;

    public CellDTO(CoordinateDTO coordinate, String originalValue, Object effectiveValue,
                   int lastVersionChanged, List<CoordinateDTO> affectedBy, List<CoordinateDTO> affecting) {
        this.coordinate = coordinate;
        this.originalValue = originalValue;
        this.effectiveValue = effectiveValue;
        this.lastVersionChanged = lastVersionChanged;
        this.affectedBy = affectedBy;
        this.affecting = affecting;
    }

    public CoordinateDTO getCoordinate() {
        return coordinate;
    }
    public String getOriginalValue() {
        return originalValue;
    }
    public Object getEffectiveValue() {
        return effectiveValue;
    }
    public int getLastVersionChanged() {
        return lastVersionChanged + 1;
    }
    public List<CoordinateDTO> getAffectedBy() {
        return affectedBy;
    }
    public List<CoordinateDTO> getAffecting() {
        return affecting;
    }

    public List<String> getListOfStringCoordinates(List<CoordinateDTO> coordinateList)
    {
        List<String> listOfStringCoordinates = new ArrayList<>();
        for (CoordinateDTO coordinateDTO : coordinateList) {
            listOfStringCoordinates.add(coordinateDTO.toString());
        }
        return listOfStringCoordinates;
    }

    public void setCoordinate(CoordinateDTO coordinate) {
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
    public void setAffectedBy(List<CoordinateDTO> affectedBy) {
        this.affectedBy = affectedBy;
    }
    public void setAffecting(List<CoordinateDTO> affecting) {
        this.affecting = affecting;
    }


}
