package expression.impl.mathematical;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import expression.Expression;
import sheet.Sheet;

public class PlusMathematicalExpression implements Expression {
    private Expression left;
    private Expression right;

    public PlusMathematicalExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public EffectiveValue eval(Sheet sheet) {
        EffectiveValue leftValue = left.eval(sheet);
        EffectiveValue rightValue = right.eval(sheet);

        double result = leftValue.extractValueWithExpectation(Double.class) + rightValue.extractValueWithExpectation(Double.class);

        return new EffectiveValueImpl(CellType.NUMERIC, result);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.NUMERIC;
    }
}
