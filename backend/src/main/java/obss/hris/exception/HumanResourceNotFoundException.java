package obss.hris.exception;

public class HumanResourceNotFoundException extends RuntimeException{
    public HumanResourceNotFoundException() {
        super("Human Resource not found. ");
    }
}
