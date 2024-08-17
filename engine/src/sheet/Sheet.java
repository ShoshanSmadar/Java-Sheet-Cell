package sheet;
import cell.Cell;

public interface Sheet {
    public int getVersion();
    public Cell getCell(int row, int col);
    public void setCell();



}
