package program.function;

import cell.CellDTO;
import coordinate.CoordinateDTO;
import engine.Engine;
import program.outpot.Output;
import sheet.SheetDTO;

import java.util.Scanner;

public class FunctionImpl implements ProgramFunctions{
    public FunctionImpl() {}
    private Scanner scanner = new Scanner(System.in);

    @Override
    public boolean addFile() {
        return false;
    }

    @Override
    public void showSheet(SheetDTO sheet) {
        Output.printSheetStatments(sheet.getSheetVersion(), sheet.getSheetName());
        printFirstRow(sheet.getLengthOfCol(), sheet.getSizeOfColumns());

        int rowsBefore = (sheet.getHeightOfRow() - 1) / 2;
        int rowsAfter = sheet.getHeightOfRow() - 1 - rowsBefore;

        for(int i = 0; i < sheet.getSizeOfRows(); i++) {
            for(int j = 0; j < rowsBefore; j++) {
                printEmptyCellRow(sheet.getLengthOfCol(), sheet.getSizeOfColumns());
            }
            printCellRow(sheet.getLengthOfCol(), sheet.getSizeOfColumns(), i + 1, sheet);
            for(int j = 0; j < rowsAfter; j++) {
                printEmptyCellRow(sheet.getLengthOfCol(), sheet.getSizeOfColumns());
            }
        }


    }

    public void printFirstRow(int colLength, int numberOfCols)
    {
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append("   ").append("|");

        // Iterate through the number of columns
        for (int i = 0; i < numberOfCols; i++) {
            // Calculate the letter to print (e.g., 'A', 'B', 'C', ...)
            char letter = (char) ('A' + (i % 26));

            // Calculate spaces needed before and after the letter
            int spacesBefore = (colLength - 1) / 2;
            int spacesAfter = colLength - 1 - spacesBefore;

            // Append spaces, letter, and more spaces
            rowBuilder.append(" ".repeat(spacesBefore))
                    .append(letter)
                    .append(" ".repeat(spacesAfter))
                    .append("|");
        }

        // Print the final row
        Output.printSheetRow(rowBuilder.toString());
    }

    public void printEmptyCellRow(int colLength, int numberOfCols)
    {
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append("   ").append("|");
        for (int i = 0; i < numberOfCols; i++) {
            rowBuilder.append(" ".repeat(colLength));
        }
        Output.printSheetRow(rowBuilder.toString());
    }

    public void printCellRow(int colLength, int numberOfCols, int rowNumber, SheetDTO sheet)
    {
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append((String.format("%02d", rowNumber))).append(" |");

        // Iterate through the number of columns
        for (int i = 0; i < numberOfCols; i++) {
            CoordinateDTO coordinate = new CoordinateDTO(i, rowNumber);
            if (sheet.getCellMap().containsKey(coordinate))
            {
                String cellValue = getStringValue(sheet.getCellMap().get(coordinate));
                if (cellValue.length() > colLength)
                {
                    cellValue = cellValue.substring(0, colLength - 3);
                    cellValue = cellValue + "...";
                    rowBuilder.append(cellValue).append("|");
                }
                else
                {
                    int spacesBefore = (colLength - cellValue.length()) / 2;
                    int spacesAfter = colLength - cellValue.length() - spacesBefore;

                    rowBuilder.append(" ".repeat(spacesBefore))
                            .append(cellValue)
                            .append(spacesAfter)
                            .append("|");
                }
            }
        }
        Output.printSheetRow(rowBuilder.toString());
    }

    private String getStringValue(CellDTO cell)
    {
        String cellValue;

        if(cell.getEffectiveValue() instanceof Integer)
        {
            cellValue = cell.getEffectiveValue().toString();
        }
        else if (cell.getEffectiveValue() instanceof Double)
        {
            cellValue = String.format("%.2f", cell.getEffectiveValue());
        }
        else if (cell.getEffectiveValue() instanceof Boolean)
        {
            cellValue = cell.getEffectiveValue().toString();
        }
        else
        {
            cellValue = cell.getEffectiveValue().toString();
        }

        return cellValue;
    }

    @Override
    public void showCell(SheetDTO sheet) {
        CoordinateDTO coordinate = getCellCoordinates(sheet.getSizeOfRows(), sheet.getSizeOfColumns());
        CellDTO cell = sheet.getCell(coordinate);
        if (cell == null) {
            Output.printCellIsEmpty(coordinate);
        }
        else {
            Output.printCell(cell);
        }
    }

    private CoordinateDTO getCellCoordinates(int row, int col)
    {
        Output.printChooseCell(row, col);
        return readCellChoice(scanner.next());
    }

    private CoordinateDTO readCellChoice(String input)
    {
        if (input == null || input.length() < 2) {
            throw new IllegalArgumentException("Input must be a capital letter followed by a number.");
        }

        char letter = input.charAt(0);
        String numberPart = input.substring(1);

        if (letter < 'A' || letter > 'Z') {
            throw new IllegalArgumentException("First character must be a capital letter (A-Z), but gut " + letter);
        }

        int number;
        try {
            number = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Part after capital letter must be a number, but gut " + numberPart);
        }

        int letterAsNumber = letter - 'A';

        return new CoordinateDTO(number - 1, letterAsNumber);
    }

    @Override
    public void updateCell(Engine sheetProgram) {
        CoordinateDTO CellCoordinates = getCellCoordinates(sheetProgram.getSheetDTO().getSizeOfRows(),
                sheetProgram.getSheetDTO().getSizeOfColumns());
        try{
            sheetProgram.changeCell(CellCoordinates, getExpression());
        }
        catch (Exception e) {
            Output.printExeptionMessage(e);
        }
    }

    public String getExpression(){
        Output.printAskForCellFunction();
        return scanner.next();
    }

    @Override
    public void showVersion() {

    }

    @Override
    public void exitProgram() {

    }

    @Override
    public void explainFunctions() {

    }
}
