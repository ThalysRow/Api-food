package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.output.ImageUpdateOutput;
import com.api_food.Algaworks_Food.domain.model.PhotoProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhotoProductMapper {

    @Mapping(source = "id", target = "productId")
    ImageUpdateOutput toDto(PhotoProductModel image);
}
