package cell;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import coordinate.Coordinate;
import coordinate.CoordinateImpl;
import expression.Expression;
import expression.parser.FunctionParser;
import sheet.Sheet;

import java.util.ArrayList;
import java.util.List;

public class CellImpl implements Cell{
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
        calculateDependenciesFromString();;
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
        return effectiveValue;
    }

    @Override
    public int getLastVersionChanged(){
        return lastVersionChanged;
    }

    @Override
    public boolean calculateEffectiveValue()
    {
        Expression expression = FunctionParser.parseExpression(originalValue);

        EffectiveValue newEffectiveValue = expression.eval(fatherSheet);

        if (newEffectiveValue.equals(effectiveValue)) {
            return false;
        } else {
            effectiveValue = newEffectiveValue;
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
                this.effectiveValue, this.lastVersionChanged,
                this.fatherSheet.getCellDependingCoordinatesDTO(this.coordinate),
                this.fatherSheet.getCellAfctingCoordinates(this.coordinate));
    }

    private void calculateDependenciesFromString() {
        String patternStart = "\\{\\s*REF\\s*,";
        char endChar = '}';
        int index = 0;

        while ((index = originalValue.indexOf(patternStart, index)) != -1) {
            int endIndex = originalValue.indexOf(endChar, index + patternStart.length());
            if (endIndex != -1) {
                String extracted = originalValue.substring(index + patternStart.length(), endIndex).trim();
                char uppercaseLetter = extracted.charAt(0);

                if (!Character.isUpperCase(uppercaseLetter)) {
                    continue;
                }
                String number = extracted.substring(1);

                int row;
                try {
                    row = Integer.parseInt(number);
                } catch (NumberFormatException e) {
                    continue;
                }
                int col = uppercaseLetter - 'A';

                dependsOn.add(new CoordinateImpl(row, col));
                index = endIndex + 1;
            } else {
                break; // No more closing braces found
            }
        }
    }
}
