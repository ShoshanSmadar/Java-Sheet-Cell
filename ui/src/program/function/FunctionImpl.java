package program.function;

import cell.CellDTO;
import coordinate.CoordinateDTO;
import engine.Engine;
import program.outpot.Output;
import sheet.SheetDTO;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Scanner;

import static java.lang.Character.isUpperCase;

public class FunctionImpl implements ProgramFunctions{
    public FunctionImpl() {}
    private Scanner scanner = new Scanner(System.in);

    @Override
    public boolean addFile(Engine sheetProgram) {
        Output.printAskForXMLFile();
        try {
            String fileName = scanner.nextLine().trim();
            if(!fileName.endsWith(".xml"))
            {
                throw new Exception("Invalid file format, try again.\n");
            }
            Output.printLoadingFile();
            sheetProgram.enterNewSheetFromXML(fileName);
            Output.printFinshLoadingFile();
            return true;
        }
        catch(Exception e) {
            Output.printExeptionMessage(e);
            return false;
        }
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
        System.out.println();

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

    private void checkIfCoordinateIsInSheet(int row, int col, CoordinateDTO coordinate)
    {
        if(row < 0 || coordinate.getRow() > row || coordinate.getCol() > col || coordinate.getCol() < 0){
            throw new IndexOutOfBoundsException("The coordinate given " + coordinate.toString() + " is out of sheet scope.\ncommand failed.");
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
            Output.printCellUpdatedSucsesfully();
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
    public void ShowOldVersionTable(Engine sheetProgram) {
        StringBuilder VersionLine = new StringBuilder();
        StringBuilder CellsChangedLine = new StringBuilder();
        VersionLine.append("Version number          |");
        CellsChangedLine.append("Number Of Cells Changed |");
        int columnSize = Integer.max( sheetProgram.getALLPastSheetNumberOfCellsChanged().size(), Collections.max(sheetProgram.getALLPastSheetNumberOfCellsChanged())) + 2;
        int versionNumber = 0;
        for(int cellsThatHaveChanged : sheetProgram.getALLPastSheetNumberOfCellsChanged()){
            versionNumber++;
            VersionLine.append(centerStringInSize(String.valueOf(versionNumber), columnSize)).append("|");
            CellsChangedLine.append(centerStringInSize(String.valueOf(cellsThatHaveChanged), columnSize)).append("|");
        }
        Output.printShowTableVersion(VersionLine.toString(), CellsChangedLine.toString());

        Output.PrintAskIfShowSpecificOldVersion();
        if(getAndCheckIntInput(1,2) == 1){
            showVersion(sheetProgram);
        }
    }

    private static String centerStringInSize(String itemToCenter, int size) {
        int numberLength = itemToCenter.length();

        // Calculate the number of spaces on the left
        int leftPadding = (size - numberLength) / 2;

        // Build the result string
        StringBuilder result = new StringBuilder();

        // Append left spaces
        for (int i = 0; i < leftPadding; i++) {
            result.append(" ");
        }

        // Append the number
        result.append(itemToCenter);

        // Append right spaces (if needed)
        while (result.length() < size) {
            result.append(" ");
        }

        return result.toString();
    }


    private void showVersion(Engine sheetProgram) {
        Output.printAskForVersionNumber((sheetProgram.getSheetCurrentVersion()));
        int userChoice = getAndCheckIntInput(1, (sheetProgram.getSheetCurrentVersion()));
//        try{
//            userChoice = scanner.nextInt();
//        }
//        catch (Exception e) {
//            throw new IllegalArgumentException("Input must be a number");
//        }
//        if (userChoice < 1 || userChoice > sheetProgram.getSheetCurrentVersion() + 1) {
//            throw new IndexOutOfBoundsException("Input must be a number between 1-" +  (sheetProgram.getSheetCurrentVersion() + 1));
//        }
        showSheet(sheetProgram.getOldVersionSheet(userChoice));
    }

    private int getAndCheckIntInput(int minSize, int maxSize){
        int userChoice = -1;
        boolean finished = false;
        while(!finished){
            try{
                userChoice = scanner.nextInt();
            }
            catch (Exception e) {
                Output.printInputMustBeANumber();
                scanner.next();
                continue;
            }
            if (userChoice < minSize || userChoice > maxSize) {
                Output.printWrongInputShowCorrect(minSize, maxSize);
                continue;
            }
            finished = true;
        }
        return userChoice;
    }

    @Override
    public void exitProgram() {
        System.exit(0);
    }

    @Override
    public void explainFunctions() {
        Output.printAskWichFunctionToHelpWith();
        int input = getAndCheckIntInput(1, (HelpFunction.getString().length +1));
        if(input >0 && input <= HelpFunction.getString().length){
            Output.printOneFunctionHelp(input);
        }
        else{
            Output.printAllHelp();
        }

    }
}
