package dynamicAnalysis;

import cell.Cell;
import cell.CellDTO;
import sheet.Sheet;
import sheet.SheetDTO;
import sheet.SheetImpl;

import java.util.ArrayList;
import java.util.List;


public class DynamicAnalysis {
    SheetDTO sheetDTO;
    Sheet sheet;
    List<CellDTO> cellsThatCanChange;



    public DynamicAnalysis(SheetDTO sheetDTO, Sheet sheet){
        this.sheetDTO = sheetDTO;
        this.sheet = sheet;
        this.cellsThatCanChange = checkPossibleCells();
    }

    public void updateVersion(SheetDTO newSheetDTO, Sheet newSheet){
        sheetDTO = newSheetDTO;
        sheet = newSheet;
        this.cellsThatCanChange = checkPossibleCells();
    }

    private List<CellDTO> checkPossibleCells(){
        List<CellDTO> cells = new ArrayList<>();
        for(CellDTO cell : sheetDTO.getCellMap().values()){
            if(isNumeric(cell.getOriginalValue())){
                cells.add(cell);
            }
        }
        return cells;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<CellDTO> getCellsThatCanChange(){
        return cellsThatCanChange;
    }

    public SheetDTO changeCellsAndGetSheet(List<CellDTO> cells){
        for(CellDTO cell : cells){
            try {
                sheet = sheet.UpdateCellValueAndSheetForDynamicAnalysis(cell.getCoordinate().getRow()
                        , cell.getCoordinate().getCol()
                        , cell.getOriginalValue()
                        , cell.getUserName()
                        , sheet);
            }
            catch (Exception ignored){
            }
        }
        sheetDTO = sheet.convertToSheetDTO();
        return sheetDTO;
    }

    public SheetDTO getSheetDTO(){
        return sheetDTO;
    }
}
