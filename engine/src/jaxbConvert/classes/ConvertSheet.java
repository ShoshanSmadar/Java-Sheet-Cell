package jaxbConvert.classes;

import cell.CellImpl;
import jaxb.STLCell;
import jaxb.STLSheet;
import sheet.Sheet;
import sheet.SheetImpl;

public abstract class ConvertSheet {

    public static Sheet ConvertSheet(STLSheet XMLsheet) {
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
            ConvertSheet.checkIfInSheet(STLcell.getRow() - 1, XMLsheet.getSTLLayout().getRows(), "row");
            ConvertSheet.checkIfInSheet(STLcell.getColumn().toUpperCase().charAt(0) - 'A', XMLsheet.getSTLLayout().getColumns(), "column");

            sheet.enterCellFromXML(new CellImpl(sheet, STLcell.getRow() - 1,
                    STLcell.getColumn().toUpperCase().charAt(0) - 'A', STLcell.getSTLOriginalValue(), 0));
        }
        return sheet;
    }

    private static void checkIfInSheet(int size, int maxSize, String name){
        if(size > maxSize | size < 0){
            throw new IndexOutOfBoundsException("Cell coordinate is outside of sheet. Expected a "+name+" in the range 0-"+maxSize+" and got "+size);
        }
    }

}
