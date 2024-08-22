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

        double result = Math.abs(value.extractValueWithExpectation(Double.class));

        return new EffectiveValueImpl(CellType.NUMERIC, result);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }
}