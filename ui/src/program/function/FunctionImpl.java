package program.function;

import cell.CellDTO;
import coordinate.Coordinate;
import coordinate.CoordinateDTO;
import engine.Engine;
import program.outpot.Output;
import sheet.SheetDTO;

import java.text.DecimalFormat;
import java.util.Scanner;

import static java.lang.Character.isUpperCase;

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
            rowBuilder.append(" ".repeat(colLength)).append("|");
        }
        Output.printSheetRow(rowBuilder.toString());
    }

    public void printCellRow(int colLength, int numberOfCols, int rowNumber, SheetDTO sheet)
    {
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append((String.format("%02d", rowNumber))).append(" |");

        // Iterate through the number of columns
        for (int i = 0; i < numberOfCols; i++) {
            CoordinateDTO coordinate = new CoordinateDTO(rowNumber - 1, i);
            if (sheet.getCellMap().containsKey(coordinate))
            {
                String cellValue = getStringValue(sheet.getCellMap().get(coordinate));
                if (cellValue.length() > colLength)
                {
                    cellValue = cellValue.substring(0, colLength - 2);
                    cellValue = cellValue + "..";
                    rowBuilder.append(cellValue).append("|");
                }
                else
                {
                    int spacesBefore = (colLength - cellValue.length()) / 2;
                    int spacesAfter = colLength - cellValue.length() - spacesBefore;

                    rowBuilder.append(" ".repeat(spacesBefore))
                            .append(cellValue)
                            .append(" ".repeat(spacesAfter))
                            .append("|");
                }
            }
            else {
                rowBuilder.append(" ".repeat(colLength)).append("|");
            }
        }
        Output.printSheetRow(rowBuilder.toString());
    }

    private String getStringValue(CellDTO cell)
    {
        String cellValue;

        if (cell.getEffectiveValue() instanceof Double)
        {
            DecimalFormat df = new DecimalFormat("#,###.##");
            cellValue = df.format(cell.getEffectiveValue());
        }
        else if (cell.getEffectiveValue() instanceof Boolean)
        {
            cellValue = cell.getEffectiveValue().toString().toUpperCase();
        }
        else
        {
            cellValue = (String) cell.getEffectiveValue();
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

    private void checkIfCoordinateIsInSheet(int row, int col, CoordinateDTO coor)
    {
        if(row < 0 || coor.getRow() > row || coor.getCol() > col || coor.getCol() < 0){
            throw new IndexOutOfBoundsException("The coordinate given " + coor.toString() + " is out of sheet scope.\ncommand failed.");
        }
    }

    private CoordinateDTO getCellCoordinates(int row, int col)
    {
        Output.printChooseCell(row, col);
        CoordinateDTO coordinateDTO = readCellChoice(scanner.next());
        checkIfCoordinateIsInSheet(row, col, coordinateDTO);
        return coordinateDTO;
    }

    private CoordinateDTO readCellChoice(String input)
    {
        if (input == null || input.length() < 2) {
            throw new IllegalArgumentException("Input must be a capital letter followed by a number.");
        }

        char letter = Character.toUpperCase(input.charAt(0));
        String numberPart = input.substring(1);

        if (!isUpperCase(letter)) {
            throw new IllegalArgumentException("First character must be a  letter (a-z or A-Z), but gut " + letter);
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
            showSheet(sheetProgram.getSheetDTO());
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
        System.exit(0);
    }

    @Override
    public void explainFunctions() {

    }
}
