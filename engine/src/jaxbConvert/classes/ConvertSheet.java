package jaxbConvert.classes;

import cell.CellImpl;
import constant.Constants;
import jaxb.STLCell;
import jaxb.STLSheet;
import sheet.Sheet;
import sheet.SheetImpl;

import java.util.Objects;

public abstract class ConvertSheet {

    public static Sheet ConvertSheetFromXML(STLSheet XMLsheet) {
        if(XMLsheet.getSTLLayout().getColumns() < Constants.MIN_SIZE_OF_COLUMNS ||XMLsheet.getSTLLayout().getColumns() > Constants.MAX_SIZE_OF_COLUMNS){
            throw new IndexOutOfBoundsException("Sheet columns must be between "
                    + Constants.MIN_SIZE_OF_COLUMNS +"-"+ Constants.MAX_SIZE_OF_COLUMNS + " but gut " + XMLsheet.getSTLLayout().getColumns());
        }
        if(XMLsheet.getSTLLayout().getRows() < Constants.MIN_SIZE_OF_ROWS ||XMLsheet.getSTLLayout().getRows() > Constants.MAX_SIZE_OF_ROWS){
            throw new IndexOutOfBoundsException("Sheet rows must be between "
                    + Constants.MIN_SIZE_OF_ROWS +"-"+ Constants.MAX_SIZE_OF_ROWS + " but gut " + XMLsheet.getSTLLayout().getRows());
        }
        Sheet sheet = new SheetImpl(XMLsheet.getName(),
                XMLsheet.getSTLLayout().getColumns(),
                XMLsheet.getSTLLayout().getRows(),
                XMLsheet.getSTLLayout().getSTLSize().getRowsHeightUnits(),
                XMLsheet.getSTLLayout().getSTLSize().getColumnWidthUnits());

        for(STLCell STLcell : XMLsheet.getSTLCells().getSTLCell())
        {
            if(STLcell.getColumn().length() > 1)
            {
                throw new IllegalArgumentException("Column input should be one letter but got " + STLcell.getColumn());
            }
            ConvertSheet.checkIfInSheet(STLcell.getRow(), XMLsheet.getSTLLayout().getRows(), "row");
            ConvertSheet.checkIfInSheet(STLcell.getColumn().toUpperCase().charAt(0) - 'A', XMLsheet.getSTLLayout().getColumns(), "column");

            sheet.enterCellFromXML(new CellImpl(sheet, STLcell.getRow(),
                    STLcell.getColumn().toUpperCase().charAt(0) - 'A', STLcell.getSTLOriginalValue(), 0));
        }
        return sheet;
    }

    private static void checkIfInSheet(int size, int maxSize, String name){
        if(size > maxSize | size < 0){
            if(Objects.equals(name, "column"))
            {
                throw new IndexOutOfBoundsException("Cell coordinate is outside of sheet. Expected a "+name
                        +" in the range A-"+((char) (maxSize + 'A' ))+" and got "+ ((char)(size + 'A')));
            }
            else{
                throw new IndexOutOfBoundsException("Cell coordinate is outside of sheet. Expected a "+name+" in the range 0-"+maxSize+" and got "+size);
            }

        }
    }
}
