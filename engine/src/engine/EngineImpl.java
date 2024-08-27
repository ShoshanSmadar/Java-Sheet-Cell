package engine;

import cell.CellDTO;
import coordinate.CoordinateDTO;
import jakarta.xml.bind.JAXBException;
import jaxbConvert.parser.XmlParser;
import sheet.Sheet;
import sheet.SheetDTO;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class EngineImpl implements Engine{
    private List<Sheet> sheetList;

    public EngineImpl() {
        sheetList = new ArrayList<>();
    }

    public EngineImpl(SheetDTO sheet, String sheetName) {
        sheetList = new ArrayList<>();
        sheetList.add(sheet.createSheetFromDTO());
    }

    @Override
    public SheetDTO getSheetDTO() {
        return sheetList.getLast().convertToSheetDTO();
    }

    @Override
    public CellDTO getCellDTO(CoordinateDTO coordinate) {
        return sheetList.getLast().getCell(coordinate.getRow(), coordinate.getCol()).getConvertToCellDTO();
    }

    @Override
    public int getSheetCurrentVersion() {
        return sheetList.size();
    }

    @Override
    public List<Integer> getALLPastSheetNumberOfCellsChanged(){
        List<Integer> cellsChangedList = new ArrayList<>();
        for(Sheet sheet : sheetList){
            cellsChangedList.addLast(sheet.getNumberOfCellsChangedInVersion());
        }
        return cellsChangedList;
    }

    @Override
    public SheetDTO getOldVersionSheet(int versionWanted) {
        if(versionWanted < 1 || versionWanted > sheetList.size()){
            throw new IndexOutOfBoundsException("Invalid version wanted, version must be between 1-" + (sheetList.size()));
        }
        return sheetList.get(versionWanted - 1).convertToSheetDTO();
    }

    @Override
    public void changeCell(CoordinateDTO coordinateToChange, String expression) throws Exception{
        sheetList.add(sheetList.getLast().UpdateCellValueAndSheet(coordinateToChange.getRow(), coordinateToChange.getCol(), expression));
    }

    @Override
    public void enterNewSheetFromXML(String xmlPath) {
        try{
            Sheet newSheet = XmlParser.sheetParser(xmlPath);
            newSheet.calculateAllSheetAffectiveValue();
            sheetList.clear();
            sheetList.add(newSheet);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Error acured while loading XML file.\n" +
                    "The error accured because: file wasn't found in given path.");
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("Error acured while loading XML file.\n" +
                    "The error accured because: file wasn't found in given path.");
        }
        catch(Exception e){
            throw new RuntimeException("Error acured while loading XML file.\n" +
                    "The error accured because: " + e.getMessage());
        }
    }
}