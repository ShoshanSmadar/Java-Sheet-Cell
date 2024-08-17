package expression.impl;

import expression.Expression;

public class AbsExpression implements Expression {
    private Expression expression;

    public AbsExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public double evaluate() {
        return Math.abs(expression.evaluate());
    }
}