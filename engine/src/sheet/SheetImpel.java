package sheet;
import cell.Cell;
import cell.CellImpl;
import coordinate.Coordinate;
import coordinate.CoordinateFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        return cellMap.get(CoordinateFactory.createCoordinate(row, col));
    }

    @Override
    public void setCell(int row, int column, String value) {
        Coordinate coordinate = CoordinateFactory.createCoordinate(row, column);
        Optional
                .ofNullable(cellMap.get(coordinate))
                .or(() -> {
                    Cell newCell = new CellImpl(row, column, value, 1);
                    cellMap.put(coordinate, newCell);
                    return Optional.of(newCell);
                })
                .ifPresent(cell -> cell.setCellOriginalValue(value));
        }


}
