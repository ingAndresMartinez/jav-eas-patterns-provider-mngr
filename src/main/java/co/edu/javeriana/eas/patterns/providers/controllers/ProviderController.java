package co.edu.javeriana.eas.patterns.providers.controllers;

import co.edu.javeriana.eas.patterns.common.dto.quotation.RequestQuotationWrapperDto;
import co.edu.javeriana.eas.patterns.providers.dto.FindProviderDto;
import co.edu.javeriana.eas.patterns.providers.dto.ProviderDto;
import co.edu.javeriana.eas.patterns.providers.enums.EProviderFilter;
import co.edu.javeriana.eas.patterns.providers.exceptions.AdvisorException;
import co.edu.javeriana.eas.patterns.providers.services.IAdvisorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("advisor")
public class ProviderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderController.class);

    private IAdvisorService advisorService;

    @GetMapping
    public ResponseEntity<List<ProviderDto>> getAllProviders() {
        try {
            return new ResponseEntity<>(advisorService.getAllProviders(), HttpStatus.OK);
        } catch (AdvisorException e) {
            LOGGER.error("Error en consulta de proveedores", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{filter}")
    public ResponseEntity<List<ProviderDto>> getProviderById(@PathVariable EProviderFilter filter, @RequestBody FindProviderDto findProviderDto) {
        try {
            return new ResponseEntity<>(advisorService.getProviderByFilter(filter, findProviderDto), HttpStatus.OK);
        } catch (AdvisorException e) {
            LOGGER.error("Error en consulta de proveedor", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ProviderDto> createProvider(@RequestBody ProviderDto providerDto) {
        try {
            return new ResponseEntity<>(advisorService.createProvider(providerDto), HttpStatus.CREATED);
        } catch (AdvisorException e) {
            LOGGER.error("Error la creación de proveedor", e);
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/notification-provider/{category}")
    public ResponseEntity<Void> reportAdvisorNewQuotation(@PathVariable int category, @RequestBody RequestQuotationWrapperDto requestQuotationWrapperDto) {
        try {
            advisorService.notificationProvidersNewQuotation(category, requestQuotationWrapperDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AdvisorException e) {
            LOGGER.error("Error en la notificacion de proveedores", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    public void setAdvisorService(IAdvisorService advisorService) {
        this.advisorService = advisorService;
    }

}
