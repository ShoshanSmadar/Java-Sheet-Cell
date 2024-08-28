package expression.impl.mathematical;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import expression.Expression;
import sheet.Sheet;

public class AbsMathematicalExpression implements Expression {
    private Expression expression;

    public AbsMathematicalExpression(Expression mathematicalExpression) {
        this.expression = mathematicalExpression;
    }

    @Override
    public EffectiveValue eval(Sheet sheet) {
        EffectiveValue value = expression.eval(sheet);

        if(value.getCellType() == CellType.UNDEFINED) {
            return new EffectiveValueImpl(CellType.NUMERIC, Double.NaN);
        }
        //if the function gets a reference of a string or bool
        if(value.getCellType() == CellType.UNKNOWN && !(value.getValue() instanceof Double)) {
            return new EffectiveValueImpl(CellType.NUMERIC, Double.NaN);
        }

        double result = Math.abs(value.extractValueWithExpectation(Double.class));

        return new EffectiveValueImpl(CellType.NUMERIC, result);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }
}