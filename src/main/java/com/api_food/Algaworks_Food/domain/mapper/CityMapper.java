package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.create.CityCreateDTO;
import com.api_food.Algaworks_Food.api.dto.list.CityListDTO;
import com.api_food.Algaworks_Food.api.dto.update.CityUpdateDTO;
import com.api_food.Algaworks_Food.domain.model.CityModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityCreateDTO toCreateDTO(CityModel cityModel);
    CityModel toCreateModel(CityCreateDTO cityCreateDTO);
    CityListDTO toCreateListDTO(CityModel cityModel);
    CityModel toUpdateModel(CityListDTO cityListDTO);
    CityUpdateDTO toUpdateDTO(CityModel cityModel);
}
