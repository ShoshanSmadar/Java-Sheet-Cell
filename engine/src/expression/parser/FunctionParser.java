package expression.parser;

import cell.cellType.CellType;
import expression.Expression;
import expression.impl.BaseExpression;
import expression.impl.RefExpression;
import expression.impl.bool.*;
import expression.impl.mathematical.*;
import expression.impl.string.ConcatExpression;
import expression.impl.string.SubExpression;
import expression.impl.string.UndefeindExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.lang.Character.isLetter;

public enum FunctionParser {
    BASE {
        @Override
        public Expression parse(List<String> arguments) {
            if (arguments.size() != 1) {
                throw new IllegalArgumentException("Invalid number of arguments for BASE function, Expected 1, but got " + arguments.size());
            }

            String value = arguments.get(0).trim();
            if (isBoolean(value)) {
                return new BaseExpression(Boolean.parseBoolean(value), CellType.BOOLEAN);
            } else if (isNumeric(value)) {
                return new BaseExpression(Double.parseDouble(value), CellType.NUMERIC);
            } else {
                return new BaseExpression(value, CellType.STRING);
            }
        }

        private boolean isBoolean(String value) {
            return "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value);
        }

        private boolean isNumeric(String value) {
            try {
                Double.parseDouble(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    },
    PLUS {
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(2, arguments.size(), "PLUS");
            Expression left = parseExpression(arguments.get(0).trim());
            Expression right = parseExpression(arguments.get(1).trim());

            if ((!left.getFunctionResultType().equals(CellType.NUMERIC) && !left.getFunctionResultType().equals(CellType.UNKNOWN))
                    || (!right.getFunctionResultType().equals(CellType.NUMERIC)) && !right.getFunctionResultType().equals(CellType.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid argument types for PLUS function, Expected NUMERIC but got " + left.getFunctionResultType() + "on the left and " + right.getFunctionResultType() + "on the right.");
            }

            return new PlusMathematicalExpression(left, right);
        }
    },
    ABS{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(1, arguments.size(), "ABS");

            Expression value = parseExpression(arguments.get(0).trim());
            if(!value.getFunctionResultType().equals(CellType.NUMERIC) && !value.getFunctionResultType().equals(CellType.UNKNOWN)){
                throw new IllegalArgumentException("Invalid argument types for ABS function, Expected NUMERIC but got " + value.getFunctionResultType());
            }
            return new AbsMathematicalExpression(value);
        }
    },
    DIVIDE {
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(2, arguments.size(), "DIVIDE");

            Expression left = parseExpression(arguments.get(0).trim());
            Expression right = parseExpression(arguments.get(1).trim());

            if ((!left.getFunctionResultType().equals(CellType.NUMERIC) && !left.getFunctionResultType().equals(CellType.UNKNOWN))
                    || (!right.getFunctionResultType().equals(CellType.NUMERIC)) && !right.getFunctionResultType().equals(CellType.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid argument types for DIVIDE function, Expected NUMERIC but got " + left.getFunctionResultType() + "on the left and " + right.getFunctionResultType() + "on the right.");
            }
            return new DevideMathematicalExpression(left, right);
        }
    },
    MINUS{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(2, arguments.size(), "MINUS");

            Expression left = parseExpression(arguments.get(0).trim());
            Expression right = parseExpression(arguments.get(1).trim());

            if ((!left.getFunctionResultType().equals(CellType.NUMERIC) && !left.getFunctionResultType().equals(CellType.UNKNOWN))
                    || (!right.getFunctionResultType().equals(CellType.NUMERIC)) && !right.getFunctionResultType().equals(CellType.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid argument types for MINUS function, Expected NUMERIC but got " + left.getFunctionResultType() + "on the left and " + right.getFunctionResultType() + "on the right.");
            }
            return new MinusMathematicalExpression(left, right);
        }
    },
    MOD{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(2, arguments.size(), "MOD");

            Expression left = parseExpression(arguments.get(0).trim());
            Expression right = parseExpression(arguments.get(1).trim());

            if ((!left.getFunctionResultType().equals(CellType.NUMERIC) && !left.getFunctionResultType().equals(CellType.UNKNOWN))
                    || (!right.getFunctionResultType().equals(CellType.NUMERIC)) && !right.getFunctionResultType().equals(CellType.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid argument types for MOD function, Expected NUMERIC but got " + left.getFunctionResultType() + "on the left and " + right.getFunctionResultType() + "on the right.");
            }
            return new ModMathematicalExpression(left, right);
        }
    },
    POW{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(2, arguments.size(), "POW");

            Expression left = parseExpression(arguments.get(0).trim());
            Expression right = parseExpression(arguments.get(1).trim());

            if ((!left.getFunctionResultType().equals(CellType.NUMERIC) && !left.getFunctionResultType().equals(CellType.UNKNOWN))
                    || (!right.getFunctionResultType().equals(CellType.NUMERIC)) && !right.getFunctionResultType().equals(CellType.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid argument types for POW function, Expected NUMERIC but got " + left.getFunctionResultType() + "on the left and " + right.getFunctionResultType() + "on the right.");
            }
            return new PowMathematicalExpression(left, right);
        }
    },
    TIMES{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(2, arguments.size(), "TIMES");

            Expression left = parseExpression(arguments.get(0).trim());
            Expression right = parseExpression(arguments.get(1).trim());

            if ((!left.getFunctionResultType().equals(CellType.NUMERIC) && !left.getFunctionResultType().equals(CellType.UNKNOWN))
                || (!right.getFunctionResultType().equals(CellType.NUMERIC)) && !right.getFunctionResultType().equals(CellType.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid argument types for TIMES function, Expected NUMERIC but got " + left.getFunctionResultType() + "on the left and " + right.getFunctionResultType() + "on the right.");
            }
            return new TimesMathematicalExpression(left, right);
        }
    },
    CONCAT{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(2, arguments.size(), "CONCAT");

            Expression left = parseExpression(arguments.get(0).trim());
            Expression right = parseExpression(arguments.get(1).trim());

            if ((!left.getFunctionResultType().equals(CellType.STRING) && !left.getFunctionResultType().equals(CellType.UNKNOWN))
                    || !right.getFunctionResultType().equals(CellType.STRING) && !right.getFunctionResultType().equals(CellType.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid argument types for CONCAT function, Expected STRING but got " + left.getFunctionResultType() + "on the left and " + right.getFunctionResultType() + "on the right.");
            }
            return new ConcatExpression(left, right);
        }
    },
    SUB{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(3, arguments.size(), "SUB");

            Expression source = parseExpression(arguments.get(0).trim());
            Expression startIndex = parseExpression(arguments.get(1).trim());
            Expression endIndex = parseExpression(arguments.get(2).trim());

            if(source.getFunctionResultType().equals(CellType.UNDEFINED)){
                return new UndefeindExpression();
            }
            if (!source.getFunctionResultType().equals(CellType.STRING) && !source.getFunctionResultType().equals(CellType.UNKNOWN))
            {
                throw new IllegalArgumentException("Invalid argument types for SUB function, Expected STRING but got " + source.getFunctionResultType() + "as source.");
            }
            if ((!startIndex.getFunctionResultType().equals(CellType.NUMERIC) && !startIndex.getFunctionResultType().equals(CellType.UNKNOWN))
                    || (!endIndex.getFunctionResultType().equals(CellType.NUMERIC) && !endIndex.getFunctionResultType().equals(CellType.UNKNOWN))) {
                throw new IllegalArgumentException("Invalid argument types for SUB function, Expected NUMERIC but got " + startIndex.getFunctionResultType() + "as start index and " + endIndex.getFunctionResultType() + "as end index.");
            }
            return new SubExpression(source, startIndex, endIndex);
        }
    },
    REF{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(1, arguments.size(), "REF");

            String input = arguments.get(0).trim();
            char Letter = input.charAt(0);
            if(!isLetter(Letter)) {
                throw new IllegalArgumentException("Invalid argument for cell name, expected a letter but got" + Letter);
            }
            String number = input.substring(1);

            int row;
            try{
                row = Integer.parseInt(number) - 1;
            }
            catch(NumberFormatException e){
                throw new IllegalArgumentException("Invalid argument for cell name, expected a number but got" + number);
            }
            int col = Character.toUpperCase(Letter) - 'A';

            return new RefExpression(row, col);
        }
    },
    SUM{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(1, arguments.size(), "SUM");

            return new SumMathematicalExpression(arguments.get(0).trim());
        }
    },
    AVERAGE{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(1, arguments.size(), "SUM");

            return new AverageMathematicalExpression(arguments.get(0).trim());
        }
    },
    PERCENT{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(2, arguments.size(), "PERCENT");
            Expression part = parseExpression(arguments.get(0).trim());
            Expression whole = parseExpression(arguments.get(1).trim());

            if ((!part.getFunctionResultType().equals(CellType.BOOLEAN) && !part.getFunctionResultType().equals(CellType.UNKNOWN))
                    || (!whole.getFunctionResultType().equals(CellType.BOOLEAN)) && !whole.getFunctionResultType()
                    .equals(CellType.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid argument types for PERCENT function, Expected NUMERIC but got "
                        + part.getFunctionResultType() + "as the part and "
                        + whole.getFunctionResultType() + "as the whole.");
            }

                return new PrecentMathematicalExpression(part, whole);
        }
    },
    EQUAL{
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(2, arguments.size(), "EQUAL");
            Expression argLeft = parseExpression(arguments.get(0).trim());
            Expression argRight = parseExpression(arguments.get(1).trim());

            return new EqualExpression(argLeft, argRight);
        }
    },
    NOT{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(1, arguments.size(), "NOT");
            Expression exp = parseExpression(arguments.get(0).trim());
            if (!exp.getFunctionResultType().equals(CellType.STRING) && !exp.getFunctionResultType().equals(CellType.UNKNOWN))
            {
                throw new IllegalArgumentException("Invalid argument types for NOT function, Expected BOOLEAN but got " + exp.getFunctionResultType() + "as source.");
            }

            return new NotExpression(exp);
        }
    },
    OR{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(2, arguments.size(), "OR");
            Expression left = parseExpression(arguments.get(0).trim());
            Expression right = parseExpression(arguments.get(1).trim());

            if ((!left.getFunctionResultType().equals(CellType.BOOLEAN) && !left.getFunctionResultType().equals(CellType.UNKNOWN))
                    || (!right.getFunctionResultType().equals(CellType.BOOLEAN)) && !right.getFunctionResultType()
                    .equals(CellType.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid argument types for OR function, Expected BOOLEAN but got "
                        + left.getFunctionResultType() + "on the left and "
                        + right.getFunctionResultType() + "on the right.");
            }

            return new OrExpression(left, right);
        }
    },
    AND{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(2, arguments.size(), "AND");
            Expression left = parseExpression(arguments.get(0).trim());
            Expression right = parseExpression(arguments.get(1).trim());

            if ((!left.getFunctionResultType().equals(CellType.BOOLEAN) && !left.getFunctionResultType().equals(CellType.UNKNOWN))
                    || (!right.getFunctionResultType().equals(CellType.BOOLEAN)) && !right.getFunctionResultType()
                    .equals(CellType.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid argument types for AND function, Expected BOOLEAN but got "
                        + left.getFunctionResultType() + "on the left and "
                        + right.getFunctionResultType() + "on the right.");
            }

            return new AndExpression(left, right);
        }
    },
    BIGGER{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(2, arguments.size(), "BIGGER");
            Expression left = parseExpression(arguments.get(0).trim());
            Expression right = parseExpression(arguments.get(1).trim());

            if ((!left.getFunctionResultType().equals(CellType.NUMERIC) && !left.getFunctionResultType().equals(CellType.UNKNOWN))
                    || (!right.getFunctionResultType().equals(CellType.NUMERIC)) && !right.getFunctionResultType()
                    .equals(CellType.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid argument types for BIGGER function, Expected NUMERIC but got "
                        + left.getFunctionResultType() + "on the left and "
                        + right.getFunctionResultType() + "on the right.");
            }

            return new BiggerExpression(left, right);
        }
    },
    LESS{
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(2, arguments.size(), "LESS");
            Expression left = parseExpression(arguments.get(0).trim());
            Expression right = parseExpression(arguments.get(1).trim());

            if ((!left.getFunctionResultType().equals(CellType.NUMERIC) && !left.getFunctionResultType().equals(CellType.UNKNOWN))
                    || (!right.getFunctionResultType().equals(CellType.NUMERIC)) && !right.getFunctionResultType()
                    .equals(CellType.UNKNOWN)) {
                throw new IllegalArgumentException("Invalid argument types for LESS function, Expected NUMERIC but got "
                        + left.getFunctionResultType() + "on the left and "
                        + right.getFunctionResultType() + "on the right.");
            }

            return new LessExpression(left, right);
        }
    },
    IF{
        @Override
        public Expression parse(List<String> arguments) {
            checkNumberOfArguments(3, arguments.size(), "IF");
            Expression condition = parseExpression(arguments.get(0).trim());
            Expression then = parseExpression(arguments.get(1).trim());
            Expression elseExpression = parseExpression(arguments.get(2).trim());

            if (!condition.getFunctionResultType().equals(CellType.BOOLEAN) &&
                    !condition.getFunctionResultType().equals(CellType.UNKNOWN))
            {
                throw new IllegalArgumentException("Invalid argument types for IF function, Expected BOOLEAN but got " + condition.getFunctionResultType() + "as source.");
            }

            return new IfExpression(condition, then, elseExpression);
        }
    };

    abstract public Expression parse(List<String> arguments);

    private static void checkNumberOfArguments(int expected, int actual, String nameOfFunction) {
        if (actual != expected) {
            throw new IllegalArgumentException("Invalid number of arguments for " + nameOfFunction+ " function, Expected "+ expected +", but got " + actual);
        }
    }

    public static Expression parseExpression(String input) {

        if (input.startsWith("{") && input.endsWith("}")) {

            String functionContent = input.substring(1, input.length() - 1);
            List<String> topLevelParts = parseMainParts(functionContent);


            String functionName = topLevelParts.get(0).trim().toUpperCase();

            //remove the first element from the array
            topLevelParts.remove(0);

            boolean isFunction = false;
            for( FunctionParser function : FunctionParser.values()){
                if(function.name().equals(functionName)){
                    isFunction = true;
                }
            }
            if(!isFunction){
                throw new IllegalArgumentException("Invalid function name " + functionName);
            }

            return FunctionParser.valueOf(functionName).parse(topLevelParts);
        }

        // handle identity expression
        return FunctionParser.BASE.parse(List.of(input.trim()));
    }

    private static List<String> parseMainParts(String input) {
        List<String> parts = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : input.toCharArray()) {
            if (c == '{') {
                stack.push(c);
            } else if (c == '}') {
                stack.pop();
            }

            if (c == ',' && stack.isEmpty()) {
                // If we are at a comma and the stack is empty, it's a separator for top-level parts
                parts.add(buffer.toString().trim());
                buffer.setLength(0); // Clear the buffer for the next part
            } else {
                buffer.append(c);
            }
        }

        // Add the last part
        if (buffer.length() > 0) {
            parts.add(buffer.toString().trim());
        }

        return parts;
    }

    }
