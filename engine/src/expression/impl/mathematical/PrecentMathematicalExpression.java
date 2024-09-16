package expression.impl.mathematical;

import cell.cellType.CellType;
import cell.cellType.EffectiveValue;
import cell.cellType.EffectiveValueImpl;
import constant.Constants;
import expression.Expression;
import sheet.Sheet;

public class PrecentMathematicalExpression implements Expression {
    Expression part;
    Expression whole;

    public PrecentMathematicalExpression(Expression part, Expression whole) {
        this.part = part;
        this.whole = whole;
    }
    @Override
    public EffectiveValue eval(Sheet sheet) {
        EffectiveValue partValue = part.eval(sheet);
        EffectiveValue wholeValue = whole.eval(sheet);

        if(partValue.getCellType() == CellType.UNDEFINED) {
            return new EffectiveValueImpl(CellType.NUMERIC, Double.NaN);
        }
        if( wholeValue.getCellType() == CellType.UNDEFINED){
            return new EffectiveValueImpl(CellType.NUMERIC,  Double.NaN);
        }
        if(partValue.getCellType() == CellType.UNKNOWN && !(partValue.getValue() instanceof Double)) {
            return new EffectiveValueImpl(CellType.NUMERIC, Double.NaN);
        }
        if(wholeValue.getCellType() == CellType.UNKNOWN && !(wholeValue.getValue() instanceof Double)) {
            return new EffectiveValueImpl(CellType.NUMERIC, Double.NaN);
        }

        return new EffectiveValueImpl(CellType.NUMERIC, (Double) partValue.getValue() * (Double) wholeValue.getValue() / 100);
    }

    @Override
    public CellType getFunctionResultType() {
        return null;
    }
}
