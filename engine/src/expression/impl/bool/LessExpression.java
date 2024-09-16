package expression.impl.bool;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import constant.Constants;
import expression.Expression;
import sheet.Sheet;

public class LessExpression implements Expression {
    Expression left, right;

    public LessExpression(Expression left, Expression right) {
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
        if(leftValue.getCellType() == CellType.UNKNOWN && !(leftValue.getValue() instanceof Double)) {
            return new EffectiveValueImpl(CellType.UNDEFINED, Constants.UNDEFINED);
        }
        if(rightValue.getCellType() == CellType.UNKNOWN && !(rightValue.getValue() instanceof Double)) {
            return new EffectiveValueImpl(CellType.UNDEFINED, Constants.UNDEFINED);
        }
        return new EffectiveValueImpl(CellType.BOOLEAN, (Double) leftValue.getValue() <= (Double) rightValue.getValue());
    }

    @Override
    public CellType getFunctionResultType() {
        return null;
    }
}
