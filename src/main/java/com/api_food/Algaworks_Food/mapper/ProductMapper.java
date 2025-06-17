package com.api_food.Algaworks_Food.mapper;

import com.api_food.Algaworks_Food.dto.create.ProductCreateDTO;
import com.api_food.Algaworks_Food.dto.list.ProductListDTO;
import com.api_food.Algaworks_Food.model.ProductModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductModel toCreateModel(ProductCreateDTO productCreateDTO);
    ProductCreateDTO toCreateDTO(ProductModel productModel);
    ProductListDTO createListDTO(ProductModel productModel);
}
