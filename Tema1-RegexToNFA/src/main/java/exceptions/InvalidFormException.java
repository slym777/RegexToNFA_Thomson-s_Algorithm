package exceptions;

public class InvalidFormException extends Exception{

    public InvalidFormException() {
        super();
    }

    public InvalidFormException(String message) {
        super(message);
    }
}
