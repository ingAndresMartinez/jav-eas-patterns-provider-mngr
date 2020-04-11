package co.edu.javeriana.eas.patterns.providers.mappers;

import co.edu.javeriana.eas.patterns.persistence.entities.ProviderEntity;
import co.edu.javeriana.eas.patterns.providers.dto.ProviderDto;

import java.util.ArrayList;
import java.util.List;

public class ProviderMapper {

    private ProviderMapper() {
    }

    public static List<ProviderDto> providerEntityListMapperInProviderDtoList(List<ProviderEntity> providerEntities) {
        List<ProviderDto> providerDtos = new ArrayList<>();
        providerEntities.forEach(providerEntity -> providerDtos.add(providerEntityMapperInProviderDto(providerEntity)));
        return providerDtos;
    }

    public static ProviderDto providerEntityMapperInProviderDto(ProviderEntity providerEntity) {
        ProviderDto providerDto = new ProviderDto();
        providerDto.setProviderId(providerEntity.getId());
        providerDto.setIdentificationType(providerEntity.getIdentificationType());
        providerDto.setIdentificationNumber(providerEntity.getIdentificationNumber());
        providerDto.setBusinessName(providerEntity.getBusinessName());
        providerDto.setCategoryId(providerEntity.getCategory().getId());
        providerDto.setAddress(providerEntity.getAddress());
        providerDto.setEmail(providerEntity.getEmail());
        providerDto.setEndPoint(providerEntity.getEndPoint());
        providerDto.setGeneralInformation(providerEntity.getGeneralInformation());
        providerDto.setPhoneNumber(providerEntity.getPhoneNumber());
        return providerDto;
    }

    public static ProviderEntity providerDtoMapperInProviderEntity(ProviderDto providerDto) {
        ProviderEntity providerEntity = new ProviderEntity();
        providerEntity.setIdentificationNumber(providerDto.getIdentificationNumber());
        providerEntity.setIdentificationType(providerDto.getIdentificationType());
        providerEntity.setBusinessName(providerDto.getBusinessName());
        providerEntity.setAddress(providerDto.getAddress());
        providerEntity.setEmail(providerDto.getEmail());
        providerEntity.setEndPoint(providerDto.getEndPoint());
        providerEntity.setGeneralInformation(providerDto.getGeneralInformation());
        providerEntity.setPhoneNumber(providerDto.getPhoneNumber());
        return providerEntity;
    }

}