package co.edu.javeriana.eas.patterns.providers.services.impl;

import co.edu.javeriana.eas.patterns.common.dto.quotation.RequestQuotationWrapperDto;
import co.edu.javeriana.eas.patterns.persistence.entities.ProviderEntity;
import co.edu.javeriana.eas.patterns.providers.dto.email.MailDto;
import co.edu.javeriana.eas.patterns.providers.services.IEmailService;
import co.edu.javeriana.eas.patterns.providers.services.INotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class NotificationServiceImpl implements INotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private String emailFrom;

    private IEmailService emailService;
    private RestTemplate restTemplate;

    @Override
    public void sendNotificationHandler(ProviderEntity providerEntity, RequestQuotationWrapperDto requestQuotationWrapperDto) throws IOException, MessagingException {
        LOGGER.info("inicia proceso de notificación para proveedor [{}].", providerEntity.getId());
        if (Objects.isNull(providerEntity.getEndPoint())) {
            sendEmail(providerEntity);
        } else {
            callEndPoint(providerEntity, requestQuotationWrapperDto);
        }
        LOGGER.info("finaliza proceso de notificación para proveedor [{}].", providerEntity.getId());
    }

    private void sendEmail(ProviderEntity providerEntity) throws IOException, MessagingException {
        LOGGER.info("inicia proceso de construcción de email para proveedor [{}].", providerEntity.getId());
        MailDto mail = new MailDto();
        mail.setFrom(emailFrom);
        mail.setTo(providerEntity.getEmail());
        mail.setSubject("Notificación de Cotización");
        Map<String, Object> model = new HashMap<>();
        model.put("businessName", providerEntity.getBusinessName());
        mail.setModel(model);
        LOGGER.info("Datos de envio [{}] -> [{}].", mail, model);
        emailService.sendSimpleMessage(mail);
        LOGGER.info("finaliza proceso de construcción de email para proveedor [{}].", providerEntity.getId());
    }

    private void callEndPoint(ProviderEntity providerEntity, RequestQuotationWrapperDto requestQuotationWrapperDto) {
        try {
            restTemplate.postForEntity(providerEntity.getEndPoint(), requestQuotationWrapperDto, Void.class);
        } catch (HttpClientErrorException e) {
            LOGGER.error("Error en notificacion a proveedor externo: ", e);
        }

    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setEmailService(IEmailService emailService) {
        this.emailService = emailService;
    }

    @Value("${spring.mail.username}")
    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }
}