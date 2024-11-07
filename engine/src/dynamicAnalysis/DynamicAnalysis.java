package dynamicAnalysis;

import cell.Cell;
import cell.CellDTO;
import sheet.Sheet;
import sheet.SheetDTO;

import java.util.ArrayList;
import java.util.List;


public class DynamicAnalysis {
    SheetDTO sheetDTO;
    List<CellDTO> cellsThatCanChange;



    public DynamicAnalysis(SheetDTO sheet){
        this.sheetDTO = sheet;
        this.cellsThatCanChange = checkPossibleCells();
    }

    public void updateVersion(SheetDTO newSheet){
        sheetDTO = newSheet;
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
        Sheet sheet =  sheetDTO.createSheetFromDTO();
        for(CellDTO cell : cells){
            try {
                sheet.UpdateCellValueAndSheet(cell.getCoordinate().getRow()
                        , cell.getCoordinate().getCol()
                        , cell.getOriginalValue()
                        , cell.getUserName());
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
