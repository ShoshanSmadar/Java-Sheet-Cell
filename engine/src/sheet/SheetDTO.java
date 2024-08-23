package sheet;

import cell.CellDTO;
import coordinate.Coordinate;

import java.util.Map;

public class SheetDTO {
    private Map<Coordinate, CellDTO> cellMap;
    private int version;
    private int sizeOfColumns;
    private int sizeOfRows;

    // Constructors, getters, and setters

    public SheetDTO() {}

    public SheetDTO(Map<Coordinate, CellDTO> cellMap, int version, int sizeOfColumns, int sizeOfRows) {
        this.cellMap = cellMap;
        this.version = version;
        this.sizeOfColumns = sizeOfColumns;
        this.sizeOfRows = sizeOfRows;
    }

    public Map<Coordinate, CellDTO> getCellMap() {
        return cellMap;
    }

    public void setCellMap(Map<Coordinate, CellDTO> cellMap) {
        this.cellMap = cellMap;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getSizeOfColumns() {
        return sizeOfColumns;
    }

    public void setSizeOfColumns(int sizeOfColumns) {
        this.sizeOfColumns = sizeOfColumns;
    }

    public int getSizeOfRows() {
        return sizeOfRows;
    }

    public void setSizeOfRows(int sizeOfRows) {
        this.sizeOfRows = sizeOfRows;
    }
}
