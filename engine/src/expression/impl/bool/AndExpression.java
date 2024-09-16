package expression.impl.bool;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import constant.Constants;
import expression.Expression;
import sheet.Sheet;

public class AndExpression implements Expression {
    Expression left, right;

    public AndExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public EffectiveValue eval(Sheet sheet) {
        EffectiveValue leftValue = left.eval(sheet);
        EffectiveValue rightValue = right.eval(sheet);

        if(leftValue.getCellType() == CellType.UNDEFINED || rightValue.getCellType() == CellType.UNDEFINED){
            return new EffectiveValueImpl(CellType.UNDEFINED, CellType.UNDEFINED);
        }
        if(leftValue.getCellType() == CellType.UNKNOWN && !(leftValue.getValue() instanceof Boolean)) {
            return new EffectiveValueImpl(CellType.UNDEFINED, Constants.UNDEFINED);
        }
        if(rightValue.getCellType() == CellType.UNKNOWN && !(rightValue.getValue() instanceof Boolean)) {
            return new EffectiveValueImpl(CellType.UNDEFINED, Constants.UNDEFINED);
        }

        boolean result = (boolean) leftValue.getValue() && (boolean) rightValue.getValue();
        return new EffectiveValueImpl(CellType.NUMERIC, result);
    }

    @Override
    public CellType getFunctionResultType() {
        return CellType.BOOLEAN;
    }
}
