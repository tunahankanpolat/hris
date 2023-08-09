package obss.hris.core.util;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender javaMailSender;

    public void sendMailForJobApplicationStatusChange(String to, String jobPostTitle, String status) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);

        mail.setSubject("Your application status is changed");
        mail.setText("Your application to "+jobPostTitle+" job post has been updated as " + status + ".");

        javaMailSender.send(mail);
    }

    public void sendMailForCandidateBan(String to, String banReason) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject("You are banned");
        mail.setText("You are banned because of " + banReason + ". All your job applications have been rejected");
        javaMailSender.send(mail);
    }
}
