package sheet;
import cell.Cell;

public interface Sheet {
    public int getVersion();
    public Cell getCell(int row, int col);
    public void setCell(int row, int column, String value);
    public int getSizeOfColumns();
    public int getSizeOfRows();



}
