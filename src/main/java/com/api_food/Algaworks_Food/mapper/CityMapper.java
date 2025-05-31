package com.api_food.Algaworks_Food.mapper;

import com.api_food.Algaworks_Food.dto.create.CityCreateDTO;
import com.api_food.Algaworks_Food.dto.list.CityListDTO;
import com.api_food.Algaworks_Food.dto.update.CityUpdateDTO;
import com.api_food.Algaworks_Food.model.CityModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityCreateDTO toCreateDTO(CityModel cityModel);
    CityModel toCreateModel(CityCreateDTO cityCreateDTO);
    CityListDTO toCreateListDTO(CityModel cityModel);
    CityModel toUpdateModel(CityListDTO cityListDTO);
    CityUpdateDTO toUpdateDTO(CityModel cityModel);
}
