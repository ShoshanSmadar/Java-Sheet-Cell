package engine;

import cell.CellDTO;
import coordinate.CoordinateDTO;
import sheet.Sheet;
import sheet.SheetDTO;

import java.util.ArrayList;
import java.util.List;

public class EngineImpl implements Engine{
    private List<Sheet> sheetList;
    private String sheetName;

    public EngineImpl() {}

    public EngineImpl(SheetDTO sheet, String sheetName) {
        sheetList = new ArrayList<>();
        sheetList.add(sheet.createSheetFromDTO());
        this.sheetName = sheetName;
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
    public String getSheetName() {
        return this.sheetName;
    }

    @Override
    public int getSheetCurrentVersion() {
        return sheetList.size();
    }

    @Override
    public SheetDTO getOldVersionSheet(int versionWanted) {
        if(versionWanted < 1 || versionWanted >= sheetList.size()){
            throw new IndexOutOfBoundsException("Invalid version wanted, version must be between 1 and " + (sheetList.size()));
        }
        return sheetList.get(versionWanted - 1).convertToSheetDTO();
    }

    @Override
    public void changeCell(CoordinateDTO coordinateToChange, String expression) throws Exception{
        sheetList.add(sheetList.getLast().UpdateCellValueAndSheet(coordinateToChange.getRow(), coordinateToChange.getCol(), expression));
    }
}