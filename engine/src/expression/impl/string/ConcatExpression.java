package expression.impl.string;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import constant.Constants;
import expression.Expression;
import sheet.Sheet;

public class ConcatExpression implements Expression {
    Expression left;
    Expression right;

    public ConcatExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public EffectiveValue eval(Sheet sheet) {
        EffectiveValue leftValue = left.eval(sheet);
        EffectiveValue rightValue = right.eval(sheet);

        if(leftValue.getCellType() == CellType.UNDEFINED || rightValue.getCellType() == CellType.UNDEFINED){
            return new EffectiveValueImpl(CellType.UNDEFINED, Constants.UNDEFINED);
        }
        if(leftValue.getCellType() == CellType.UNKNOWN && !(leftValue.getValue() instanceof String)) {
            return new EffectiveValueImpl(CellType.UNDEFINED, Constants.UNDEFINED);
        }
        if(rightValue.getCellType() == CellType.UNKNOWN && !(rightValue.getValue() instanceof String)) {
            return new EffectiveValueImpl(CellType.UNDEFINED, Constants.UNDEFINED);
        }
        String result = leftValue.extractValueWithExpectation(String.class) + rightValue.extractValueWithExpectation(String.class);

        return new EffectiveValueImpl(CellType.STRING, result);
    }

    @Override
    public CellType getFunctionResultType() {
        return null;
    }
}
