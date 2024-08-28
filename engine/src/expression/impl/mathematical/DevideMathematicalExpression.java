package expression.impl.mathematical;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import expression.Expression;
import sheet.Sheet;

public class DevideMathematicalExpression implements Expression {
    private Expression left;
    private Expression right;

    public DevideMathematicalExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public EffectiveValue eval(Sheet sheet) {
        EffectiveValue leftValue = left.eval(sheet);
        EffectiveValue rightValue = right.eval(sheet);

        if(leftValue.getCellType() == CellType.UNDEFINED) {
            return new EffectiveValueImpl(CellType.NUMERIC, Double.NaN);
        }
        if( rightValue.getCellType() == CellType.UNDEFINED){
            return new EffectiveValueImpl(CellType.NUMERIC,  Double.NaN);
        }
        if(leftValue.getCellType() == CellType.UNKNOWN && !(leftValue.getValue() instanceof Double)) {
            return new EffectiveValueImpl(CellType.NUMERIC, Double.NaN);
        }
        if(rightValue.getCellType() == CellType.UNKNOWN && !(rightValue.getValue() instanceof Double)) {
            return new EffectiveValueImpl(CellType.NUMERIC, Double.NaN);
        }

        double rightValueExtracted = rightValue.extractValueWithExpectation(Double.class);
        if (rightValueExtracted == 0.0) {
            return new EffectiveValueImpl(CellType.NUMERIC, Double.NaN);
        }
        double result = leftValue.extractValueWithExpectation(Double.class) /rightValueExtracted ;

        return new EffectiveValueImpl(CellType.NUMERIC, result);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }
}