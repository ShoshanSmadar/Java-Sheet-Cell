/*package jaxbConvert.xmlOne.classes;

import cell.CellDTO;
import engine.Engine;
import jaxb.xmlOne.*;
import sheet.SheetDTO;

import java.util.ArrayList;
import java.util.List;

public abstract class ConvertToXML {
    public static List<STLSheet> convertSheetToXML(Engine engine){
        List<STLSheet> sheets = new ArrayList<>();
        SheetDTO SheetExample = engine.getSheetDTO();
        STLLayout stlLayout = setSTLLayout(SheetExample.getSizeOfColumns(), SheetExample.getSizeOfRows(),
                SheetExample.getHeightOfRow(), SheetExample.getLengthOfCol());
        for(int i = 0 ; i < engine.getSheetCurrentVersion(); i++){
            SheetDTO dtoSheet = engine.getOldVersionSheet(i + 1);
            STLSheet stlSheet = new STLSheet();
            stlSheet.setName(dtoSheet.getSheetName());
            stlSheet.setSTLLayout(stlLayout);
            STLCells cells = new STLCells();
            for(CellDTO cell : dtoSheet.getCellMap().values()){
                cells.getSTLCell().add(setSTLCell(cell));
            }
            stlSheet.setSTLCells(cells);
            sheets.add(stlSheet);
        }
        return sheets;
    }

    private static STLCell setSTLCell(CellDTO dtoCell){
        STLCell stlCell = new STLCell();
        stlCell.setSTLOriginalValue(dtoCell.getOriginalValue());
        stlCell.setColumn((Character.toString((char)('A' + dtoCell.getCoordinate().getCol()))));
        stlCell.setRow(dtoCell.getCoordinate().getRow());
        return stlCell;
    }

    private static STLLayout setSTLLayout(int columns, int rows, int height, int width){
        STLLayout stlLayout = new STLLayout();
        stlLayout.setSTLSize(setSTLSize(height,width));
        stlLayout.setColumns(columns);
        stlLayout.setRows(rows);
        return stlLayout;
    }

    private static STLSize setSTLSize(int height, int width){
        STLSize stlSize = new STLSize();
        stlSize.setColumnWidthUnits(width);
        stlSize.setRowsHeightUnits(height);
        return stlSize;
    }
}*/


