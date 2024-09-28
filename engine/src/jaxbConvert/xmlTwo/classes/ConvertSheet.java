package jaxbConvert.xmlTwo.classes;

import cell.CellImpl;
import constant.Constants;
import coordinate.Coordinate;
import coordinate.CoordinateFactory;
import jaxb.xmlTwo.STLBoundaries;
import jaxb.xmlTwo.STLCell;
import jaxb.xmlTwo.STLRange;
import range.RangeImpl;
import sheet.Sheet;
import sheet.SheetImpl;

import java.util.Objects;

public abstract class ConvertSheet {

    public static Sheet ConvertSheetFromXML(jaxb.xmlTwo.STLSheet XMLsheet) {
        if(XMLsheet.getSTLLayout().getColumns() < Constants.MIN_SIZE_OF_COLUMNS
                || XMLsheet.getSTLLayout().getColumns() > Constants.MAX_SIZE_OF_COLUMNS){
            throw new IndexOutOfBoundsException("Sheet columns must be between "
                    + Constants.MIN_SIZE_OF_COLUMNS +"-"+ Constants.MAX_SIZE_OF_COLUMNS
                    + " but gut " + XMLsheet.getSTLLayout().getColumns());
        }
        if(XMLsheet.getSTLLayout().getRows() < Constants.MIN_SIZE_OF_ROWS
                || XMLsheet.getSTLLayout().getRows() > Constants.MAX_SIZE_OF_ROWS){
            throw new IndexOutOfBoundsException("Sheet rows must be between "
                    + Constants.MIN_SIZE_OF_ROWS +"-"+ Constants.MAX_SIZE_OF_ROWS
                    + " but gut " + XMLsheet.getSTLLayout().getRows());
        }
        Sheet sheet = new SheetImpl(XMLsheet.getName(),
                XMLsheet.getSTLLayout().getColumns(),
                XMLsheet.getSTLLayout().getRows(),
                XMLsheet.getSTLLayout().getSTLSize().getRowsHeightUnits(),
                XMLsheet.getSTLLayout().getSTLSize().getColumnWidthUnits());

        for(STLRange STLrange : XMLsheet.getSTLRanges().getSTLRange()){
            String name = STLrange.getName();
            STLBoundaries boundaries = STLrange.getSTLBoundaries();
            Coordinate from = getCoordinateFromString(boundaries.getFrom(),
                    XMLsheet.getSTLLayout().getRows(), XMLsheet.getSTLLayout().getColumns());
            Coordinate to = getCoordinateFromString(boundaries.getTo(),
                    XMLsheet.getSTLLayout().getRows(), XMLsheet.getSTLLayout().getColumns());
            sheet.addRangeFromXML(new RangeImpl(name, from, to,XMLsheet.getSTLLayout().getRows(), XMLsheet.getSTLLayout().getColumns()));
        }

        for(STLCell STLcell : XMLsheet.getSTLCells().getSTLCell())
        {
            if(STLcell.getColumn().length() > 1)
            {
                throw new IllegalArgumentException("Column input should be one letter but got " + STLcell.getColumn());
            }
            ConvertSheet.checkIfInSheet(STLcell.getRow()-1, XMLsheet.getSTLLayout().getRows(), "row");
            ConvertSheet.checkIfInSheet(STLcell.getColumn()
                    .toUpperCase().charAt(0) - 'A', XMLsheet.getSTLLayout().getColumns(), "column");

            sheet.enterCellFromXML(new CellImpl(sheet, STLcell.getRow() -1,
                    STLcell.getColumn().toUpperCase().charAt(0) - 'A', STLcell.getSTLOriginalValue(), 0));
        }



        return sheet;
    }

    private static Coordinate getCoordinateFromString(String coordinate,int maxSizeRow, int maxSizeColumn){
        Integer row;
        Integer col;
        try{
            char colLetter = coordinate.charAt(0);
            row = Integer.parseInt(coordinate.substring(1));
            col = colLetter - 'A';
        }
        catch(Exception e){
            throw new RuntimeException("Error while range parsing coordinate", e);
        }
            checkIfInSheet(row, maxSizeRow, "row");
            checkIfInSheet(col, maxSizeColumn, "column");
            return CoordinateFactory.createCoordinate(row - 1, col);
    }

    private static void checkIfInSheet(int size, int maxSize, String name){
        if(size > maxSize | size < 0){
            if(Objects.equals(name, "column"))
            {
                throw new IndexOutOfBoundsException("Cell coordinate is outside of sheet. Expected a "+name
                        +" in the range A-"+((char) (maxSize + 'A' ))+" and got "+ ((char)(size + 'A')));
            }
            else{
                throw new IndexOutOfBoundsException("Cell coordinate is outside of sheet. Expected a "
                        +name+" in the range 0-"+maxSize+" and got "+size);
            }

        }
    }
}
