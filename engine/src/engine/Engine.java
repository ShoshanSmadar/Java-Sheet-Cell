package engine;

import cell.CellDTO;
import cell.cellType.CellType;
import coordinate.CoordinateDTO;
import jakarta.xml.bind.JAXBException;
import sheet.SheetDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface Engine {
    void startLineSorter();
    void setSortingRange(String rangeValue);
    List<Character> getPossibleSortingColumns();
    List<List<String>> sortColumns();
    void addColumnToSortingOrder(char column);
    SheetDTO getSheetDTO();
    CellDTO getCellDTO(CoordinateDTO coordinate);
    int getSheetCurrentVersion();
    List<Integer> getALLPastSheetNumberOfCellsChanged();
    SheetDTO getOldVersionSheet(int versionWanted);
    void changeCell(CoordinateDTO coordinateToChange, String expression) throws Exception;
    void enterNewSheetFromXML(File file) throws JAXBException, FileNotFoundException;
    void enterNewSheetFromXML(String xmlPath);
    void addRange(String rangeName, String rangeValues);
    void deleteRange(String rangeName);

    void setLineFilter();

    //filter methods:
    void setRowsToFilter(String rangeValue);
    void setColToFilterBy(Character colToFilterBy);
    List<String> getValueTypesPossibleInColumn();
    void setFilterBy(String cellType);
    void filter();
    List<Character> getColumnsToFiltter();
    SheetDTO getNewFilterdSheet();
}
