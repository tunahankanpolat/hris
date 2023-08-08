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
}
