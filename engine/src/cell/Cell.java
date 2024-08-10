package cell;

import cell.cellType.CellType;

abstract class Cell{
    CellType value;
    int lastVersionChanged = 0;
    String originalValue;
    // List<point> afectedBy;
    // List<point> afecting;
}
