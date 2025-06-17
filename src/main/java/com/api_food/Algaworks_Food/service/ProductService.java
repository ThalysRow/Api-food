package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.ProductCreateDTO;
import com.api_food.Algaworks_Food.dto.list.ProductListDTO;
import com.api_food.Algaworks_Food.dto.update.ProductUpdateDTO;
import com.api_food.Algaworks_Food.exception.custom.ProductNotFoundInRestaurantException;
import com.api_food.Algaworks_Food.mapper.ProductMapper;
import com.api_food.Algaworks_Food.model.ProductModel;
import com.api_food.Algaworks_Food.model.RestaurantModel;
import com.api_food.Algaworks_Food.repository.ProductRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    final private ProductRepository productRepository;
    final private StringFormatter stringFormatter;
    final private ProductMapper productMapper;
    final private RestaurantService restaurantService;

    public ProductService(ProductRepository productRepository, StringFormatter stringFormatter, ProductMapper productMapper, RestaurantService restaurantService) {
        this.productRepository = productRepository;
        this.stringFormatter = stringFormatter;
        this.productMapper = productMapper;
        this.restaurantService = restaurantService;
    }

    @Transactional
    public ProductCreateDTO createProduct(UUID restaurantId, ProductCreateDTO data) {

        RestaurantModel restaurant = restaurantService.returnRestaurantModel(restaurantId);

        data.setName(stringFormatter.stringFormated(data.getName()));
        data.setDescription(stringFormatter.stringFormated(data.getDescription()));

        ProductModel addProduct = productMapper.toCreateModel(data);
        addProduct.setRestaurant(restaurant);
        restaurant.getProducts().add(addProduct);
        ProductModel saveProduct = productRepository.save(addProduct);
        return productMapper.toCreateDTO(saveProduct);
    }

    public List<ProductListDTO> listProductsRestaurant(UUID id){
        RestaurantModel restaurant = restaurantService.returnRestaurantModel(id);

        return restaurant.getProducts().stream().map(productMapper::createListDTO).toList();
    }

    public ProductListDTO findProductInRestaurant(UUID restaurantId, int productId ){

        RestaurantModel restaurant = restaurantService.returnRestaurantModel(restaurantId);

        ProductModel findProduct = restaurant.getProducts()
                .stream().filter(product -> product.getId() == productId)
                .findFirst()
                .orElseThrow(()-> new ProductNotFoundInRestaurantException(productId, restaurant.getName()));

        return productMapper.createListDTO(findProduct);
    }

    @Transactional
    public ProductUpdateDTO  updateProductRestaurant(UUID restaurantId, int productId, ProductUpdateDTO data) {

        data.setName(stringFormatter.stringFormated(data.getName()));
        data.setDescription(stringFormatter.stringFormated(data.getDescription()));
        data.setPrice(data.getPrice());
        data.setActive(data.getActive());

        RestaurantModel restaurant = restaurantService.returnRestaurantModel(restaurantId);

        ProductModel findProduct = restaurant.getProducts()
                .stream().filter(product -> product.getId() == productId)
                .findFirst()
                .orElseThrow(()-> new ProductNotFoundInRestaurantException(productId, restaurant.getName()));

        findProduct.setName(data.getName());
        findProduct.setDescription(data.getDescription());
        findProduct.setPrice(data.getPrice());
        findProduct.setActive(data.getActive());

        ProductModel saveProduct = productRepository.save(findProduct);

        return productMapper.createUpdateDTO(saveProduct);

    }
}
