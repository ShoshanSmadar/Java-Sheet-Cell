package exception;

public class NumberIsNotAnIntException extends Exception {
    private Object number;

    public NumberIsNotAnIntException(Object number) {
        super("The provided value '" + number + "' is not an integer.");
        this.number = number;
    }

    public Object getNumber() {
        return number;
    }
}