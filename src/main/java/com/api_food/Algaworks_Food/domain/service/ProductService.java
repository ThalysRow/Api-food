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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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


    public List<ProductModel> returnProductModels(UUID restaurantId, List<Integer> productIds) {
        List<ProductModel> products = productRepository.findByRestaurantIdProductsId(restaurantId, productIds);

        Set<Integer> foundIds = products.stream()
                .map(ProductModel::getId)
                .collect(Collectors.toSet());

            for (Integer id : productIds){
                if (!foundIds.contains(id)){
                    throw new BusinessException("product", id);
                }
            }

            return products;
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
