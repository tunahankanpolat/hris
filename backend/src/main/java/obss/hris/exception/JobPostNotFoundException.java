package obss.hris.exception;

public class JobPostNotFoundException extends RuntimeException{
    public JobPostNotFoundException(Long jobPostId) {
        super("Job post with id " + jobPostId + " not found. ");
    }
}
