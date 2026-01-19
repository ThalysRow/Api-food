package com.api_food.Algaworks_Food.domain.mapper;

import com.api_food.Algaworks_Food.api.dto.create.ProductCreateDTO;
import com.api_food.Algaworks_Food.api.dto.list.ProductListDTO;
import com.api_food.Algaworks_Food.api.dto.update.ProductUpdateDTO;
import com.api_food.Algaworks_Food.domain.model.ProductModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductModel toCreateModel(ProductCreateDTO productCreateDTO);
    ProductCreateDTO toCreateDTO(ProductModel productModel);
    ProductListDTO createListDTO(ProductModel productModel);
    ProductUpdateDTO createUpdateDTO(ProductModel productModel);
}
