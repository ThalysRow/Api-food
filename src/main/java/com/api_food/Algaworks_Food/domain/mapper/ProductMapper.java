package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.output.ProductOutput;
import com.api_food.Algaworks_Food.domain.model.ProductModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductOutput toOutput(ProductModel productModel);
}
