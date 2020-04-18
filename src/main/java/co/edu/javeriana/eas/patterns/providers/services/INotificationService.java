package co.edu.javeriana.eas.patterns.providers.services;

import co.edu.javeriana.eas.patterns.common.dto.quotation.RequestQuotationWrapperDto;
import co.edu.javeriana.eas.patterns.persistence.entities.ProviderEntity;
import co.edu.javeriana.eas.patterns.providers.exceptions.AdvisorException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface INotificationService {

    void sendNotificationHandler(ProviderEntity providerEntity, RequestQuotationWrapperDto requestQuotationWrapperDto) throws IOException, MessagingException, AdvisorException;

}