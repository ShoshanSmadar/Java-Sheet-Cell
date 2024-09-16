package expression.impl.bool;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import constant.Constants;
import expression.Expression;
import sheet.Sheet;

public class IfExpression implements Expression {
    Expression condition;
    Expression thenExpression;
    Expression elseExpression;
    public IfExpression(Expression condition, Expression thenExpression, Expression elseExpression) {
        this.condition = condition;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;
    }

    @Override
    public EffectiveValue eval(Sheet sheet) {
        EffectiveValue conditionValue = condition.eval(sheet);
        EffectiveValue thenValue = thenExpression.eval(sheet);
        EffectiveValue elseValue = elseExpression.eval(sheet);

        if(conditionValue.getCellType() == CellType.UNDEFINED) {
            return new EffectiveValueImpl(CellType.UNDEFINED, Constants.UNDEFINED);
        }
        if(conditionValue.getCellType() == CellType.UNKNOWN && !(conditionValue.getValue() instanceof Boolean)) {
            return new EffectiveValueImpl(CellType.UNDEFINED, Constants.UNDEFINED);
        }
        if((boolean) conditionValue.getValue()) {
            return new EffectiveValueImpl(thenValue.getCellType(), thenValue.getValue());
        }
        else {
            return new EffectiveValueImpl(elseValue.getCellType(), elseValue.getValue());
        }
    }

    @Override
    public CellType getFunctionResultType() {
        return null;
    }
}
