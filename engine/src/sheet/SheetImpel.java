package sheet;
import cell.Cell;
import coordinate.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class SheetImpel implements Sheet {
    private Map<Coordinate, Cell> cellMap;
    private int version;
    private final int sizeOfColumns;
    private final int sizeOfRows;

    public SheetImpel(int sizeOfColumns, int sizeOfRows) {
        cellMap = new HashMap<>();
        version = 0;
        this.sizeOfColumns = sizeOfColumns;
        this.sizeOfRows = sizeOfRows;
    }

    @Override
    public int getVersion(){
        return version;}

    @Override
    public Cell getCell(int row, int col) {
        return null;
    }

    @Override
    public void setCell() {

    }


}
