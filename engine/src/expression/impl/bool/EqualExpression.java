package expression.impl.bool;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import constant.Constants;
import expression.Expression;
import sheet.Sheet;

public class EqualExpression implements Expression {
    Expression left, right;

    public EqualExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public EffectiveValue eval(Sheet sheet) {
        EffectiveValue leftValue = left.eval(sheet);
        EffectiveValue rightValue = right.eval(sheet);

        if((leftValue.getValue() instanceof Boolean && !(rightValue.getValue() instanceof Boolean))
            || (leftValue.getValue() instanceof Double && !(rightValue.getValue() instanceof Double))
            || (rightValue.getValue() instanceof Boolean && !(leftValue.getValue() instanceof Boolean))
                || (rightValue.getValue() instanceof Double && !(leftValue.getValue() instanceof Double))){
            return new EffectiveValueImpl(CellType.BOOLEAN, false);
        }

        return new EffectiveValueImpl(CellType.BOOLEAN, leftValue.getValue().equals(rightValue.getValue()));
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.BOOLEAN;
    }
}
