package obss.hris.exception;

public class CandidateBannedException extends RuntimeException{
    public CandidateBannedException() {
        super("Yeni ilanlara başvurmanız engellenmiştir. ");
    }
}
