package cell;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import coordinate.Coordinate;

import java.util.List;

public interface Cell {
    Coordinate getCoordinate();
    CellType getCellType();
    EffectiveValue getEffectiveValue();
    int getLastVersionChanged();
    void calculateEffectiveValue();
    List<Cell> getDependsOn();
    List<Cell> getAffecting ();
    void setCellOriginalValue(String value);

}
