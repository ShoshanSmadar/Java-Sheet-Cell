package cell;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.CellDTO;
import coordinate.Coordinate;

import java.util.List;

public interface Cell extends Cloneable{
    Coordinate getCoordinate();
    CellType getCellType();
    EffectiveValue getEffectiveValue();
    int getLastVersionChanged();
    boolean calculateEffectiveValue();
    void setCellOriginalValue(String value);
    void updateVersion(int version);
    CellDTO getConvertToCellDTO();
    Cell clone();
}
