package cell;

import cell.cellType.CellType;
import java.util.List;

abstract class CellImpl {
    //private final Coordinate coordinate;
    private CellType value;
    private int lastVersionChanged;
    private String originalValue;
    private List<Cell> dependsOn;
    private List<Cell> afecting;
}
