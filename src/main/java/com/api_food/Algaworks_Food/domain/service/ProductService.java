package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.api.dto.input.ProductInput;
import com.api_food.Algaworks_Food.api.dto.output.ProductOutput;
import com.api_food.Algaworks_Food.domain.exception.custom.BusinessException;
import com.api_food.Algaworks_Food.domain.exception.custom.ProductNotFoundInRestaurantException;
import com.api_food.Algaworks_Food.domain.mapper.ProductMapper;
import com.api_food.Algaworks_Food.domain.model.ProductModel;
import com.api_food.Algaworks_Food.domain.model.RestaurantModel;
import com.api_food.Algaworks_Food.domain.repository.ProductRepository;
import com.api_food.Algaworks_Food.utils.Formatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {
    final private ProductRepository productRepository;
    final private ProductMapper productMapper;
    final private RestaurantService restaurantService;


    @Transactional
    public ProductOutput createProduct(UUID restaurantId, ProductInput input) {

        RestaurantModel restaurant = restaurantService.returnRestaurantModel(restaurantId);

        String name = Formatter.string(input.getName());
        String description = Formatter.string(input.getDescription());

        ProductModel product = ProductModel.addProduct(name, description, input.getPrice(), restaurant);

        restaurant.getProducts().add(product);

        productRepository.saveAndFlush(product);

        return productMapper.toOutput(product);
    }

    public List<ProductOutput> listProductsInRestaurant(UUID id, Boolean listDesableProducts) {
        RestaurantModel restaurant = restaurantService.returnRestaurantModel(id);
        if (listDesableProducts == null || listDesableProducts) {
            return restaurant.getProducts().stream().map(productMapper::toOutput).toList();
        }
        return productRepository.findActiveProducts(restaurant).stream().map(productMapper::toOutput).toList();
    }

    public ProductOutput findProductInRestaurant(UUID restaurantId, int productId ){

        RestaurantModel restaurant = restaurantService.returnRestaurantModel(restaurantId);

        ProductModel findProduct = restaurant.getProducts()
                .stream().filter(product -> product.getId() == productId)
                .findFirst()
                .orElseThrow(()-> new ProductNotFoundInRestaurantException(productId, restaurant.getName()));

        return productMapper.toOutput(findProduct);
    }

    @Transactional
    public ProductOutput updateProductInRestaurant(UUID restaurantId, int productId, ProductInput input) {

        String name = Formatter.string(input.getName());
        String description = Formatter.string(input.getDescription());

        RestaurantModel restaurant = restaurantService.returnRestaurantModel(restaurantId);

        ProductModel findedProduct = restaurant.getProducts()
                .stream().filter(product -> product.getId() == productId)
                .findFirst()
                .orElseThrow(()-> new ProductNotFoundInRestaurantException(productId, restaurant.getName()));

        findedProduct.setName(name);
        findedProduct.setDescription(description);
        findedProduct.setPrice(input.getPrice());
        findedProduct.setActive(input.getActive());

        productRepository.saveAndFlush(findedProduct);

        return productMapper.toOutput(findedProduct);
    }

    public void getProductInRestaurant(UUID restaurantId, int productId){

        RestaurantModel restaurant = restaurantService.verifyFieldRestaurant(restaurantId);

         restaurant.getProducts()
                 .stream()
                .filter(item -> item.getId() == productId)
                .findFirst()
                 .orElseThrow(()-> new ProductNotFoundInRestaurantException(productId, restaurant.getName()));
    }

    @Transactional
    public void verifyProductsField(UUID restaurantId, List<Integer> productsId){
        productsId.forEach(productId -> { getProductInRestaurant(restaurantId, productId);});
    }

    public ProductModel returnProductModel(int id){
        return productRepository.findById(id).orElseThrow(()-> new BusinessException("product", id));
    }

    public ProductModel findProductModelInRestaurant(UUID restaurantId, int productId) {

        RestaurantModel restaurant = restaurantService.verifyFieldRestaurant(restaurantId);

        return restaurant.getProducts()
                .stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElseThrow(()-> new ProductNotFoundInRestaurantException(productId, restaurant.getName()));
    }
}
