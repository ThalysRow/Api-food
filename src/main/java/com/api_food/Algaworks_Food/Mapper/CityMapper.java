package com.api_food.Algaworks_Food.Mapper;

import com.api_food.Algaworks_Food.dto.CityDTO;
import com.api_food.Algaworks_Food.model.CityModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityDTO toDTO(CityModel cityModel);
    CityModel toEntity(CityDTO cityDTO);
}
