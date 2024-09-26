package lineFiltter;

import cell.cellType.CellType;

import java.util.List;

public interface LineFillter {
    void setColToFilter(Character colToFilterBy);

    void setRowsToFilter(String rangeValue);

    void setColToFilterBy(Character colToFilterBy);

    List<String> getValueTypesPossibleInColumn();

    void setFilterBy(CellType cellType);

    void filter();
}
