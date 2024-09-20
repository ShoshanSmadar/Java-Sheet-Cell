package cell;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import coordinate.Coordinate;
import coordinate.CoordinateFactory;
import coordinate.CoordinateImpl;
import expression.Expression;
import expression.parser.FunctionParser;
import sheet.Sheet;

import java.util.ArrayList;
import java.util.List;

public class CellImpl implements Cell, Cloneable {
    private Sheet fatherSheet;
    private Coordinate coordinate;
    private EffectiveValue effectiveValue;
    private int lastVersionChanged;
    private String originalValue;
    private List<Coordinate> dependsOn;

    public CellImpl(Sheet sheet, int row, int column, String originalValue, int version)  {
        this.fatherSheet = sheet;
        this.coordinate = new CoordinateImpl(row, column);
        this.originalValue = originalValue;
        this.lastVersionChanged = version;
        this.dependsOn = new ArrayList<>();
        calculateDependenciesFromString();
        calculateRangeDependencies("{average,");
        calculateRangeDependencies("{sum,");
        sheet.enterCoordinateAndDependenciesToGraph(coordinate, this.dependsOn);
    }

    @Override
    public CellImpl clone(){
        try{
            CellImpl newCell = (CellImpl) super.clone();
            newCell.coordinate = CoordinateFactory.createCoordinate(this.coordinate.getRow(), this.coordinate.getColumn());
            newCell.dependsOn = new ArrayList<>();
            for (Coordinate coord :  this.dependsOn) {
                newCell.dependsOn.add(CoordinateFactory.createCoordinate(coord.getRow(), coord.getColumn()));
            }
            return newCell;
        }
        catch (CloneNotSupportedException e){
            return null;
        }
    }

    @Override
    public List<Coordinate> getdependingOn() {
        return this.dependsOn;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public CellType getCellType() {
        return effectiveValue.getCellType();
    }

    @Override
    public EffectiveValue getEffectiveValue() {
        boolean bool = calculateEffectiveValue();
        return effectiveValue;
    }

    @Override
    public int getLastVersionChanged(){
        return lastVersionChanged;
    }

    @Override
    public void setFatherSheet(Sheet newSheet){
        this.fatherSheet = newSheet;
    }

    @Override
    public boolean calculateEffectiveValue()
    {
        Expression expression = FunctionParser.parseExpression(originalValue);

        EffectiveValue newEffectiveValue = expression.eval(fatherSheet);

        if (newEffectiveValue.equals(effectiveValue)) {
            return false;
        } else {
            this.effectiveValue = newEffectiveValue;
            return true;
        }
    }

    @Override
    public void setCellOriginalValue(String value)
    {
        this.originalValue = value;
    }

    @Override
    public void updateVersion(int version) {
        this.lastVersionChanged = version;
    }

    @Override
    public CellDTO getConvertToCellDTO() {
        return new CellDTO(this.coordinate.convertToDTO(), this.originalValue,
                this.effectiveValue.getValue(), this.lastVersionChanged,
                this.fatherSheet.getCellDependingCoordinatesDTO(this.coordinate),
                this.fatherSheet.getCellAfctingCoordinates(this.coordinate));
    }

    private void calculateDependenciesFromString() {
        String patternStart = "{ref,";  // lowercase pattern
        char endChar = '}';
        int index = 0;

        // Convert the original value to lowercase for case-insensitive search
        String lowerCaseOriginal = originalValue.toLowerCase();

        while ((index = lowerCaseOriginal.indexOf(patternStart, index)) != -1) {
            int endIndex = lowerCaseOriginal.indexOf(endChar, index + patternStart.length());
            if (endIndex != -1) {
                String extracted = originalValue.substring(index + patternStart.length(), endIndex).trim();

                // Extract the first letter and convert to uppercase
                char letter = extracted.charAt(0);
                char uppercaseLetter = Character.toUpperCase(letter);

                if (!Character.isUpperCase(uppercaseLetter)) {
                    continue;
                }

                String number = extracted.substring(1);

                int row;
                try {
                    row = Integer.parseInt(number) - 1;
                } catch (NumberFormatException e) {
                    continue;
                }

                // Column based on the uppercase letter
                int col = uppercaseLetter - 'A';

                Coordinate newCoordinate = CoordinateFactory.createCoordinate(row, col);
                if (!this.dependsOn.contains(newCoordinate)) {
                    this.dependsOn.add(new CoordinateImpl(row, col));
                }
                index = endIndex + 1;
            } else {
                break; // No more closing braces found
            }
        }
    }



    private void calculateRangeDependencies(String patternStart){
            char endChar = '}';
            int index = 0;

            // Convert the original value to lowercase for case-insensitive search
            String lowerCaseOriginal = originalValue.toLowerCase();

            while ((index = lowerCaseOriginal.indexOf(patternStart, index)) != -1) {
                int endIndex = lowerCaseOriginal.indexOf(endChar, index + patternStart.length());
                if (endIndex != -1) {
                    String extracted = originalValue.substring(index + patternStart.length(), endIndex).trim();

                    if(fatherSheet.isNameInRange(extracted)) {
                        fatherSheet.addCellToRangeDepndingOn(this.coordinate, extracted);
                        List<Coordinate> rangeList = fatherSheet.getRangeCellList(extracted);
                        for (Coordinate coordinate : rangeList) {
                            if (!this.dependsOn.contains(coordinate)) {
                                this.dependsOn.add(new CoordinateImpl(coordinate.getRow(), coordinate.getColumn()));
                            }
                        }
                    }

                    index = endIndex + 1;
                } else {
                    break; // No more closing braces found
                }
            }
    }
}
