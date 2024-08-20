package expression.impl.mathematical;

import expression.MathematicalExpression;

public class PowMathematicalExpression implements MathematicalExpression {
    private MathematicalExpression left;
    private MathematicalExpression right;

    public PowMathematicalExpression(MathematicalExpression left, MathematicalExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate() {
        return Math.pow(left.evaluate(), right.evaluate());
    }
}
