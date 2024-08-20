package expression.impl.mathematical;

import expression.MathematicalExpression;

public class DevideMathematicalExpression implements MathematicalExpression {
    private MathematicalExpression left;
    private MathematicalExpression right;

    public DevideMathematicalExpression(MathematicalExpression left, MathematicalExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate() {
        return left.evaluate() / right.evaluate();
    }
}