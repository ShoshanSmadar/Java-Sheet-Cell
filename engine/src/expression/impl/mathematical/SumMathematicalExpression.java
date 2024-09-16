package expression.impl.mathematical;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import coordinate.Coordinate;
import expression.Expression;
import sheet.Sheet;

import java.util.List;

public class SumMathematicalExpression implements Expression {
    String rangeName;

    public SumMathematicalExpression(String rangeName) {
        this.rangeName = rangeName;
    }

    @Override
    public EffectiveValue eval(Sheet sheet) {
        if(!sheet.isNameInRange(rangeName)){
            throw new IllegalArgumentException("Range name given does not exist in sheet");
        }
        List<Coordinate> cellsInRange = sheet.getRangeCellList(rangeName);
        Double sum = 0.0;
        for (Coordinate coordinate : cellsInRange) {
            EffectiveValue result = sheet.getCell(coordinate).getEffectiveValue();
            if(result.getCellType() == CellType.NUMERIC
                    || (result.getCellType() == CellType.UNKNOWN && result.getValue() instanceof Double))
                sum += (double) result.getValue();
        }
        return new EffectiveValueImpl(CellType.NUMERIC, sum);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }
}
