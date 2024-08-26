package engine;

import cell.CellDTO;
import coordinate.CoordinateDTO;
import sheet.SheetDTO;

public interface Engine {
    SheetDTO getSheetDTO();
    CellDTO getCellDTO(CoordinateDTO coordinate);
    int getSheetCurrentVersion();
    SheetDTO getOldVersionSheet(int versionWanted);
    void changeCell(CoordinateDTO coordinateToChange, String expression) throws Exception;
    void enterNewSheetFromXML(String xmlPath);
}
