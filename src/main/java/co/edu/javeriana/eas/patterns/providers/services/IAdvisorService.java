package co.edu.javeriana.eas.patterns.providers.services;

import co.edu.javeriana.eas.patterns.common.dto.quotation.RequestQuotationWrapperDto;
import co.edu.javeriana.eas.patterns.providers.dto.FindProviderDto;
import co.edu.javeriana.eas.patterns.providers.dto.ProviderDto;
import co.edu.javeriana.eas.patterns.providers.enums.EProviderFilter;
import co.edu.javeriana.eas.patterns.providers.exceptions.AdvisorException;

import java.util.List;

public interface IAdvisorService {

    List<ProviderDto> getAllProviders() throws AdvisorException;

    List<ProviderDto> getProviderByFilter(EProviderFilter filter, FindProviderDto findProviderDto) throws AdvisorException;

    ProviderDto createProvider(ProviderDto providerDto) throws AdvisorException;

    void notificationProvidersNewQuotation(int category, RequestQuotationWrapperDto requestQuotationWrapperDto) throws AdvisorException;

}