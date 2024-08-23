package sheet;
import cell.Cell;
import coordinate.Coordinate;
import coordinate.CoordinateDTO;

import java.util.List;

public interface Sheet {
    public int getVersion();
    public Cell getCell(int row, int col);
    public Cell getCell(Coordinate coordinate);
    public void setCell(int row, int column, String value);
    public int getSizeOfColumns();
    public int getSizeOfRows();
    public void increaseVersion();
    public Sheet UpdateCellValueAndSheet(int row, int col, String value) throws Exception;
    public SheetDTO convertToSheetDTO();
    public List<CoordinateDTO> getCellDependingCoordinatesDTO(Coordinate cellCoordinate);
    public List<CoordinateDTO> getCellAfctingCoordinates(Coordinate cellCoordinate);
    public List<Coordinate> getCellDependingCoordinates(Coordinate cellCoordinate);



}
