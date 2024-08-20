package expression.impl.mathematical;

import expression.MathematicalExpression;

public class AbsMathematicalExpression implements MathematicalExpression {
    private MathematicalExpression mathematicalExpression;

    public AbsMathematicalExpression(MathematicalExpression mathematicalExpression) {
        this.mathematicalExpression = mathematicalExpression;
    }

    @Override
    public double evaluate() {
        return Math.abs(mathematicalExpression.evaluate());
    }
}