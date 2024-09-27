package lineFiltter;

import cell.cellType.CellType;
import sheet.SheetDTO;

import java.util.List;

public interface LineFillter {
    List<Character> getPossibleColumns();

    void setRowsToFilter(String rangeValue);

    void setColToFilterBy(Character colToFilterBy);

    List<String> getValueTypesPossibleInColumn();

    void setFilterBy(CellType cellType);

    void filter();

    SheetDTO getFilterdSheet();
}
