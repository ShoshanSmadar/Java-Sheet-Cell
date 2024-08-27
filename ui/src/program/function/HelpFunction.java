package program.function;

import java.util.List;

public class HelpFunction {
    static String[]  descriptions = {
            "1. PLUS(arg1, arg2)\n" +
                    "   Description:\n" +
                    "   This function performs addition between two arguments.\n" +
                    "   Arguments:\n" +
                    "   - arg1: A number or a function that returns a number.\n" +
                    "   - arg2: A number or a function that returns a number.\n" +
                    "   Return Value:\n" +
                    "   The result of the addition.\n" +
                    "   For example: {PLUS,1,2} will be 3.",

            "2. MINUS(arg1, arg2)\n" +
                    "   Description:\n" +
                    "   This function performs subtraction between two arguments: arg1 – arg2.\n" +
                    "   Arguments:\n" +
                    "   - arg1: A number or a function that returns a number.\n" +
                    "   - arg2: A number or a function that returns a number.\n" +
                    "   Return Value:\n" +
                    "   The result of the subtraction.\n" +
                    "   For example: {MINUS,5,2} will be 3.",

            "3. TIMES(arg1, arg2)\n" +
                    "   Description:\n" +
                    "   This function performs multiplication between two arguments.\n" +
                    "   Arguments:\n" +
                    "   - arg1: A number or a function that returns a number.\n" +
                    "   - arg2: A number or a function that returns a number.\n" +
                    "   Return Value:\n" +
                    "   The result of the multiplication.\n" +
                    "   For example: {TIMES,5,2} will be 10.",

            "4. DIVIDE(arg1, arg2)\n" +
                    "   Description:\n" +
                    "   This function performs division between two arguments: arg1 / arg2.\n" +
                    "   If arg2 equals 0, the function cannot be calculated, and its value is NaN.\n" +
                    "   Arguments:\n" +
                    "   - arg1: A number or a function that returns a number. This is the numerator.\n" +
                    "   - arg2: A number or a function that returns a number. This is the denominator.\n" +
                    "   Return Value:\n" +
                    "   The result of the division.\n" +
                    "   For example: {DIVIDE,6,2} will be 3.",

            "5. MOD(arg1, arg2)\n" +
                    "   Description:\n" +
                    "   This function performs a modulo operation between two arguments: arg1 % arg2.\n" +
                    "   Arguments:\n" +
                    "   - arg1: A number or a function that returns a number.\n" +
                    "   - arg2: A number or a function that returns a number.\n" +
                    "   Return Value:\n" +
                    "   The result of the operation— a number.\n" +
                    "   For example: {MOD,5,2} will be 1.",

            "6. POW(arg1, arg2)\n" +
                    "   Description:\n" +
                    "   This function performs exponentiation between two arguments: arg1^arg2.\n" +
                    "   Arguments:\n" +
                    "   - arg1: A number or a function that returns a number.\n" +
                    "   - arg2: A number or a function that returns a number.\n" +
                    "   Return Value:\n" +
                    "   The result of the exponentiation—a number.\n" +
                    "   For example: {POW,6,2} will be 36.",

            "7. ABS(arg1)\n" +
                    "   Description:\n" +
                    "   This function returns the absolute value of an argument: |arg1|.\n" +
                    "   Arguments:\n" +
                    "   - arg1: A number or a function that returns a number.\n" +
                    "   Return Value:\n" +
                    "   The absolute value of the argument— a number.\n" +
                    "   For example: {ABS,-6} will be 6.",

            "8. CONCAT(str1, str2)\n" +
                    "   Description:\n" +
                    "   This function concatenates two string arguments.\n" +
                    "   Arguments:\n" +
                    "   - str1: A string or a function that returns a string.\n" +
                    "   - str2: A string or a function that returns a string.\n" +
                    "   Return Value:\n" +
                    "   The concatenated string.\n" +
                    "   For example: {CONCAT,HELLO,GUEST} will be HELLOGUEST.",

            "9. SUB(source, start-index, end-index)\n" +
                    "   Description:\n" +
                    "   This function extracts a substring from a source string.\n" +
                    "   If any index is out of the string's bounds (less than 0; greater than its length, etc.), will return !UNDEFINED!.\n" +
                    "   Arguments:\n" +
                    "   - source: A string or a function that returns a string. This is the source string to work on.\n" +
                    "   - start-index: A number or a function that returns a number. This is the starting index (0-based) from which to begin the cut.\n" +
                    "   - end-index: A number or a function that returns a number. This is the ending index (inclusive) at which to finish the cut.\n" +
                    "   For example: {SUB,HELLOGUEST,0,4} will be HELLO.",

            "10. REF(cell-id)\n" +
                    "   Description:\n" +
                    "   This function allows you to retrieve the value of another cell.\n" +
                    "   Arguments:\n" +
                    "   - cell-id: The identifier of the cell in the format. Example: 5A\n" +
                    "   Return Value:\n" +
                    "   The effective value of the referenced cell.\n" +
                    "   For example: {REF,A4} will show the value of cell A4\n" +
                    "   Another example: {PLUS,{REF,D2},5} will add the value in cell D2 with the number 5."
    };

    public static String[] getString(){
        return descriptions;
    }
}
