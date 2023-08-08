package obss.hris.exception;

public class CandidateAlreadyBannedException extends RuntimeException{
    public CandidateAlreadyBannedException() {
        super("Bu kullanıcı zaten engellenmiştir. ");
    }
}
