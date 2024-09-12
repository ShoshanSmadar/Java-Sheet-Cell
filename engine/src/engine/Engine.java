package engine;

import cell.CellDTO;
import coordinate.CoordinateDTO;
import jakarta.xml.bind.JAXBException;
import sheet.SheetDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface Engine {
    SheetDTO getSheetDTO();
    CellDTO getCellDTO(CoordinateDTO coordinate);
    int getSheetCurrentVersion();
    List<Integer> getALLPastSheetNumberOfCellsChanged();
    SheetDTO getOldVersionSheet(int versionWanted);
    void changeCell(CoordinateDTO coordinateToChange, String expression) throws Exception;

    void enterNewSheetFromXML(File file) throws JAXBException, FileNotFoundException;

    void enterNewSheetFromXML(String xmlPath);
    void saveProgram(String xmlPath);
}
