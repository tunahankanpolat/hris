package obss.hris.exception;

public class CandidateNotFoundException extends RuntimeException{
    public CandidateNotFoundException() {
        super("Candidate not found. ");
    }
}
