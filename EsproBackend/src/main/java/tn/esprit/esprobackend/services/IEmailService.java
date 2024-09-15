package tn.esprit.esprobackend.services;

public interface IEmailService {


    public void sendSimpleMailMessage(String to, String subject,String text );
}
