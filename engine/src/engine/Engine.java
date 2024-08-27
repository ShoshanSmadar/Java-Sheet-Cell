package engine;

import cell.CellDTO;
import coordinate.CoordinateDTO;
import sheet.SheetDTO;

import java.util.List;

public interface Engine {
    SheetDTO getSheetDTO();
    CellDTO getCellDTO(CoordinateDTO coordinate);
    int getSheetCurrentVersion();
    List<Integer> getALLPastSheetNumberOfCellsChanged();
    SheetDTO getOldVersionSheet(int versionWanted);
    void changeCell(CoordinateDTO coordinateToChange, String expression) throws Exception;
    void enterNewSheetFromXML(String xmlPath);
}
