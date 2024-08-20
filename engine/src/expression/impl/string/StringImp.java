package expression.impl.string;

import expression.StringExpression;

public class StringImp implements StringExpression{
    private String string;

    public StringImp(String string) {
        this.string = string;
    }

    @Override
    public String evaluate() {
        return string;
    }


}
