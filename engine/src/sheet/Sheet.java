package sheet;
import cell.Cell;
import coordinate.Coordinate;
import coordinate.CoordinateDTO;
import range.Range;

import java.util.HashMap;
import java.util.List;

public interface Sheet {
    List<Coordinate> getRangeCellList(String rangeName);
    void setRangeSet(HashMap<String, Range> rangeSet);
    Boolean isNameInRange(String name);
    void addRange(String rangeName, String range);
    int getNumberOfCellsChangedInVersion();
    public int getVersion();
    public Cell getCell(int row, int col);
    public Cell getCell(Coordinate coordinate);
    void enterCellFromXML(Cell cell);
    public void setCell(int row, int column, String value);
    public int getSizeOfColumns();
    public int getSizeOfRows();
    public void increaseVersion();
    public Sheet UpdateCellValueAndSheet(int row, int col, String value) throws Exception;
    List<Cell> calculateAllSheetAffectiveValue() throws Exception;
    public void addRangeFromXML(Range range);
    public void addCellToRangeDepndingOn(Coordinate coordinate, String rangeName);

    void deleteRange(String name);

    public SheetDTO convertToSheetDTO();
    public List<CoordinateDTO> getCellDependingCoordinatesDTO(Coordinate cellCoordinate);
    public List<CoordinateDTO> getCellAfctingCoordinates(Coordinate cellCoordinate);
    public List<Coordinate> getCellDependingCoordinates(Coordinate cellCoordinate);
    public void enterCoordinateAndDependenciesToGraph(Coordinate cellCoordinate, List<Coordinate> coordinateDependencies);
}
