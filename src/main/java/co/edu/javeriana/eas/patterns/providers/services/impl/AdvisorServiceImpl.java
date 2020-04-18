package co.edu.javeriana.eas.patterns.providers.services.impl;

import co.edu.javeriana.eas.patterns.common.dto.quotation.RequestQuotationWrapperDto;
import co.edu.javeriana.eas.patterns.common.enums.EExceptionCode;
import co.edu.javeriana.eas.patterns.persistence.entities.*;
import co.edu.javeriana.eas.patterns.persistence.repositories.ICategoryRepository;
import co.edu.javeriana.eas.patterns.persistence.repositories.IProviderRepository;
import co.edu.javeriana.eas.patterns.providers.dto.FindProviderDto;
import co.edu.javeriana.eas.patterns.providers.dto.ProviderDto;
import co.edu.javeriana.eas.patterns.providers.enums.EProviderFilter;
import co.edu.javeriana.eas.patterns.providers.exceptions.AdvisorException;
import co.edu.javeriana.eas.patterns.providers.mappers.ProviderMapper;
import co.edu.javeriana.eas.patterns.providers.services.IAdvisorService;
import co.edu.javeriana.eas.patterns.providers.services.INotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdvisorServiceImpl implements IAdvisorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdvisorServiceImpl.class);

    private ICategoryRepository categoryRepository;
    private IProviderRepository providerRepository;
    private INotificationService notificationService;

    @Override
    public List<ProviderDto> getAllProviders() throws AdvisorException {
        LOGGER.info("INICIA CONSULTA DE TODOS LOS PROVEEDORES");
        List<ProviderEntity> provides = new ArrayList<>();
        providerRepository.findAll().forEach(provides::add);
        if (provides.isEmpty()) {
            throw new AdvisorException(EExceptionCode.PROVIDER_NOT_FOUND, "No existen proveedor cargados");
        }
        List<ProviderDto> providesMappers = new ArrayList<>();
        provides.forEach(providerEntity -> providesMappers.add(ProviderMapper.providerEntityMapperInProviderDto(providerEntity)));
        LOGGER.info("FINALIZA CONSULTA DE TODOS LOS PROVEEDORES [{}]", provides);
        return providesMappers;
    }

    @Override
    public List<ProviderDto> getProviderByFilter(EProviderFilter filter, FindProviderDto findProviderDto) throws AdvisorException {
        LOGGER.info("INICIA CONSULTA DE PROVEEDOR POR FILTRO [{}] -> [{}]", filter, findProviderDto);
        List<ProviderEntity> listProviders = getProvider(filter, findProviderDto);
        if (listProviders.isEmpty()) {
            throw new AdvisorException(EExceptionCode.PROVIDER_NOT_FOUND, "El Proveedor no existe");
        }
        List<ProviderDto> result = ProviderMapper.providerEntityListMapperInProviderDtoList(listProviders);
        LOGGER.info("FINALIZA CONSULTA DE PROVEEDOR POR FILTRO [{}] -> [{}]", filter, result);
        return result;
    }

    @Override
    public ProviderDto createProvider(ProviderDto providerDto) throws AdvisorException {
        LOGGER.info("INICIA CREACIÓN DE PROVEEDOR -> [{}]", providerDto);
        CategoryEntity categoryEntity = categoryRepository.findById(providerDto.getCategoryId())
                .orElseThrow(() -> new AdvisorException(EExceptionCode.PROVIDER_NOT_FOUND, "No existe la categoria ingresada"));
        ProviderEntity providerEntity = ProviderMapper.providerDtoMapperInProviderEntity(providerDto);
        providerEntity.setCategory(categoryEntity);
        providerRepository.save(providerEntity);
        providerDto.setProviderId(providerEntity.getId());
        LOGGER.info("FINALIZA CREACIÓN DE PROVEEDOR -> [{}]", providerDto);
        return providerDto;
    }

    @Override
    public void notificationProvidersNewQuotation(int category, RequestQuotationWrapperDto requestQuotationWrapperDto) throws AdvisorException {
        FindProviderDto findProviderDto = new FindProviderDto();
        findProviderDto.setCategoryId(category);
        List<ProviderEntity> providersToReport = getProvider(EProviderFilter.CATEGORY, findProviderDto);
        providersToReport.forEach(providerEntity -> {
            try {
                notificationService.sendNotificationHandler(providerEntity, requestQuotationWrapperDto);
            } catch (IOException | MessagingException | AdvisorException e) {
                LOGGER.info("Error en notificacion:", e);
            }
        });
    }

    private List<ProviderEntity> getProvider(EProviderFilter filter, FindProviderDto findProviderDto) throws AdvisorException {
        CategoryEntity categoryEntity;
        List<ProviderEntity> providerEntities = new ArrayList<>();
        switch (filter) {
            case CATEGORY:
                categoryEntity = categoryRepository.findById(findProviderDto.getCategoryId())
                        .orElseThrow(() -> new AdvisorException(EExceptionCode.CATEGORY_NOT_FOUND, "No existe la categoria ingresada."));
                providerEntities = providerRepository.findByCategory(categoryEntity);
                break;
            case IDENTIFICATION:
                Optional<ProviderEntity> providerEntity = providerRepository.findByIdentificationTypeAndIdentificationNumber(findProviderDto.getIdentificationType(), findProviderDto.getIdentificationNumber());
                if (providerEntity.isPresent()) {
                    providerEntities.add(providerEntity.get());
                }
                break;
            default:
                Iterable<Integer> iter = Collections.singletonList(findProviderDto.getProviderId());
                providerRepository.findAllById(iter).forEach(providerEntities::add);
        }
        return providerEntities;
    }

    @Autowired
    public void setCategoryRepository(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setNotificationService(INotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Autowired
    public void setProviderRepository(IProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }
}