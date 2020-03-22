package exceptions;


public class InvalidPrefixException extends Exception{

    public InvalidPrefixException() {
        super();
    }

    public InvalidPrefixException(String message) {
        super(message);
    }
}
