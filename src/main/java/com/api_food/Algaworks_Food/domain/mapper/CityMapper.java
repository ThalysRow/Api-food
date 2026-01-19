package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.output.CityOutput;
import com.api_food.Algaworks_Food.domain.model.CityModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityOutput toCityOutput(CityModel city);
}
