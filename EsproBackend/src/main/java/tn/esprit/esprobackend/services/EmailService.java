package tn.esprit.esprobackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.esprobackend.entities.otpReset;
import tn.esprit.esprobackend.entities.user;
import tn.esprit.esprobackend.repositories.otpResetRepo;
import tn.esprit.esprobackend.repositories.userRepo;

@Service
@RequiredArgsConstructor
public class EmailService implements  IEmailService {
    @Autowired
    private final JavaMailSender emailSender;
    @Autowired
    private otpResetRepo otpRepo;
  /*  @Value("${spring.mail.verify.host}")
    private String Host;
    @Value("${spring.mail.verify.username}")
    private String fromEmail;*/

    @Override
    public void sendSimpleMailMessage(String to, String subject,String text) {
        try {
            SimpleMailMessage msg= new SimpleMailMessage();
msg.setSubject(subject);
            msg.setFrom("arbia.sassi@esprit.tn");
            msg.setTo(to);
            msg.setText(text);
            emailSender.send(msg);


        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }



    public otpReset addOtp(otpReset o) {
       // String pwd =u.getPassword();
        // u.setPassword(passwordEncoder.encode(pwd));
        return otpRepo.save(o);
    }


}