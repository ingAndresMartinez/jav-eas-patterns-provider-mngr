package co.edu.javeriana.eas.patterns.providers.services;

import co.edu.javeriana.eas.patterns.providers.dto.email.MailDto;

import javax.mail.MessagingException;
import java.io.IOException;

public interface IEmailService {

    void sendSimpleMessage(MailDto mail) throws MessagingException, IOException;

}