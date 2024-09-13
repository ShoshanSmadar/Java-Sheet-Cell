package sheet;

import coordinate.Coordinate;
import cell.CellDTO;
import coordinate.CoordinateDTO;

import java.util.Map;


public class SheetDTO {
    protected String SheetName;
    protected int sheetVersion;
    protected String sheetName;
    protected Map<CoordinateDTO, CellDTO> cellMap;
    protected int sizeOfColumns;
    protected int lengthOfCol;
    protected int sizeOfRows;
    protected int heightOfRow;

    public SheetDTO(String sheetName, int sheetVersion, Map<CoordinateDTO, CellDTO> cellMap,
                    int sizeOfColumns, int lengthOfCol, int sizeOfRows, int heightOfRow){
        this.SheetName = sheetName;
        this.sheetVersion = sheetVersion;
        this.cellMap = cellMap;
        this.sizeOfColumns = sizeOfColumns;
        this.lengthOfCol = lengthOfCol;
        this.sizeOfRows = sizeOfRows;
        this.heightOfRow = heightOfRow;
    }

    public String getSheetName() {
        return SheetName;
    }

    public int getSheetVersion() {
        return sheetVersion + 1;
    }

    public Map<CoordinateDTO, CellDTO> getCellMap() {
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

    public void setCellMap(Map<CoordinateDTO, CellDTO> cellMap) {
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
        return new SheetImpl(this.getSheetName(),this.sizeOfColumns, this.sizeOfRows, this.heightOfRow, this.lengthOfCol);
    }

    public CellDTO getCell(CoordinateDTO coordinate) {
        return cellMap.get(coordinate);
    }

//    public CellDTO getCell(String coordinate){
//
//    }
}
