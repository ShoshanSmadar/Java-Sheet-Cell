package sheet;

import coordinate.Coordinate;
import cell.CellDTO;

import java.util.Map;


public class SheetDTO {
    protected int sheetVersion;
    protected String sheetName;
    protected Map<Coordinate, CellDTO> cellMap;
    protected int sizeOfColumns;
    protected int lengthOfCol;
    protected int sizeOfRows;
    protected int heightOfRow;

    public SheetDTO(int sheetVersion, Map<Coordinate, CellDTO> cellMap, int sizeOfColumns, int lengthOfCol, int sizeOfRows, int heightOfRow){
        this.sheetVersion = sheetVersion;
        this.cellMap = cellMap;
        this.sizeOfColumns = sizeOfColumns;
        this.lengthOfCol = lengthOfCol;
        this.sizeOfRows = sizeOfRows;
        this.heightOfRow = heightOfRow;
    }

    public int getSheetVersion() {
        return sheetVersion;
    }

    public Map<Coordinate, CellDTO> getCellMap() {
        return cellMap;
    }

    public int getSizeOfColumns() {
        return sizeOfColumns;
    }

    public int getLengthOfCol() {
        return lengthOfCol;
    }

    public int getSizeOfRows() {
        return sizeOfRows;
    }

    public int getHeightOfRow() {
        return heightOfRow;
    }

    public void setCellMap(Map<Coordinate, CellDTO> cellMap) {
        this.cellMap = cellMap;
    }

    public void setSizeOfColumns(int sizeOfColumns) {
        this.sizeOfColumns = sizeOfColumns;
    }

    public void setLengthOfCol(int lengthOfCol) {
        this.lengthOfCol = lengthOfCol;
    }

    public void setSizeOfRows(int sizeOfRows) {
        this.sizeOfRows = sizeOfRows;
    }

    public void setHeightOfRow(int heightOfRow) {
        this.heightOfRow = heightOfRow;
    }

    public void setSheetVersion(int sheetVersion) {
        this.sheetVersion = sheetVersion;
    }

    public Sheet createSheetFromDTO(){
        //TODO!!!
        return null;
    }
}
