package obss.hris.exception;

public class LinkedinScrapeException extends RuntimeException{
    public LinkedinScrapeException(String linkedinUrl) {
        super("Linkedin bilgilerinizi " + linkedinUrl + "adresinden çekerken bir hata oluştu.");
    }
}
