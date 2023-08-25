package obss.hris.exception;

public class JobPostNotActiveException extends RuntimeException{
    public JobPostNotActiveException() {
        super("İş ilanı aktif değil.");
    }
}
