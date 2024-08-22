package sheet;
import cell.Cell;
import coordinate.Coordinate;

public interface Sheet {
    public int getVersion();
    public Cell getCell(int row, int col);
    public Cell getCell(Coordinate coordinate);
    public void setCell(int row, int column, String value);
    public int getSizeOfColumns();
    public int getSizeOfRows();
    public void increaseVersion();
    public Sheet UpdateCellValueAndSheet(int row, int col, String value) throws Exception;



}
