package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.api.dto.input.RestaurantInput;
import com.api_food.Algaworks_Food.api.dto.output.PaymentMethodOutput;
import com.api_food.Algaworks_Food.api.dto.output.RestaurantOutput;
import com.api_food.Algaworks_Food.api.dto.output.UserOutput;
import com.api_food.Algaworks_Food.domain.exception.custom.BusinessException;
import com.api_food.Algaworks_Food.domain.exception.custom.EntityInUseException;
import com.api_food.Algaworks_Food.domain.exception.custom.PaymentMethodNotFoundInRestaurantException;
import com.api_food.Algaworks_Food.domain.exception.custom.RestaurantNotFoundException;
import com.api_food.Algaworks_Food.domain.mapper.PaymentMethodMapper;
import com.api_food.Algaworks_Food.domain.mapper.RestaurantMapper;
import com.api_food.Algaworks_Food.domain.mapper.UserMapper;
import com.api_food.Algaworks_Food.domain.model.*;
import com.api_food.Algaworks_Food.domain.repository.RestaurantRepository;
import com.api_food.Algaworks_Food.utils.Formatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final KitchenService kitchenService;
    private final CityService cityService;
    private final PaymentMethodMapper paymentMethodMapper;
    private final PaymentMethodService paymentMethodService;
    private final UserMapper userMapper;
    private final UserService userService;

    @Transactional
    public RestaurantOutput newRestaurant(RestaurantInput input){

         KitchenModel kitchenFound = kitchenService.verifyKitchen(input.getKitchen().getId());
         CityModel cityFound = cityService.verifyCityField(input.getAddress().getCity().getId());

        AddressModel address = AddressModel.createAddress(input.getAddress());
        address.setCity(cityFound);

        RestaurantModel restaurant = RestaurantModel.createRestaurant(input.getName(), input.getDeliveryFee(),
                kitchenFound, address);

        restaurantRepository.saveAndFlush(restaurant);

        return restaurantMapper.toOutput(restaurant);

    }

    public RestaurantOutput findRestaurantById(UUID id){
        return restaurantMapper.toOutput(this.returnRestaurantModel(id));
    }

    public List<RestaurantOutput> listRestaurants(){
        return restaurantRepository.findAll().stream().map(restaurantMapper::toOutput).toList();
    }

    @Transactional
    public RestaurantOutput updateRestaurant(UUID id, RestaurantInput input){

        RestaurantModel restaurantFound = this.returnRestaurantModel(id);

            KitchenModel kitchenFound = kitchenService.verifyKitchen(input.getKitchen().getId());
            CityModel cityFound = cityService.verifyCityField(input.getAddress().getCity().getId());

            AddressModel address = AddressModel.createAddress(input.getAddress());
            address.setCity(cityFound);

            restaurantFound.setName(Formatter.string(input.getName()));
            restaurantFound.setDeliveryFee(input.getDeliveryFee());
            restaurantFound.setKitchen(kitchenFound);
            restaurantFound.setAddress(address);
            restaurantFound.setDateUpdated(OffsetDateTime.now());

            restaurantRepository.saveAndFlush(restaurantFound);

            return restaurantMapper.toOutput(restaurantFound);

        }

        @Transactional
        public void deleteRestaurant(UUID id){
        restaurantRepository.deleteById(this.returnRestaurantModel(id).getId());
        }

        @Transactional
        public void activateRestaurant(UUID id){
            RestaurantModel restaurant = this.returnRestaurantModel(id);

            restaurant.setActive(true);
        }

        @Transactional
        public void deactivateRestaurant(UUID id){
            RestaurantModel restaurant = this.returnRestaurantModel(id);

            restaurant.setActive(false);
        }

        public List<PaymentMethodOutput> restaurantListPaymentMethods(UUID restaurantId){

            RestaurantModel restaurant = this.returnRestaurantModel(restaurantId);

            return restaurant.getPaymentMethods().stream().map(paymentMethodMapper::toOutput).toList();
        }

        @Transactional
        public void restaurantRemovePaymentMethod(UUID restaurantId, int paymentMethodId){

            RestaurantModel restaurant = this.returnRestaurantModel(restaurantId);

            PaymentMethodModel paymentMethod = paymentMethodService.returnPaymentMethodModel(paymentMethodId);

            if (!restaurant.getPaymentMethods().contains(paymentMethod)) {
                throw new BusinessException(String.format("The payment method '%s' is not yet associated with restaurant '%s'.", paymentMethod.getName(), restaurant.getName()));
            }
            restaurant.getPaymentMethods().remove(paymentMethod);
        }

        @Transactional
        public void restaurantAddPaymentMethod(UUID restaurantId, int paymentMethodId){

            RestaurantModel restaurant = this.returnRestaurantModel(restaurantId);

            PaymentMethodModel paymentMethod = paymentMethodService.returnPaymentMethodModel(paymentMethodId);

            if (restaurant.getPaymentMethods().contains(paymentMethod)) {
                throw new EntityInUseException(String.format("The payment method '%s' is already linked to restaurant '%s'.", paymentMethod.getName(), restaurant.getName()));
            }
            restaurant.getPaymentMethods().add(paymentMethod);
        }

        public RestaurantModel returnRestaurantModel(UUID id){
            return restaurantRepository.findById(id).orElseThrow(()-> new RestaurantNotFoundException(id));
        }

        public PaymentMethodModel verifyPaymentField(UUID restaurantId, int id){
            RestaurantModel restaurant = this.returnRestaurantModel(restaurantId);
            return  restaurant.getPaymentMethods()
                    .stream().filter(payment -> payment.getId() == id)
                    .findFirst().orElseThrow(()-> new PaymentMethodNotFoundInRestaurantException(id, restaurant.getName()));
        }

        @Transactional
        public void restaurantOpen(UUID restaurantId){
        RestaurantModel restaurant = this.returnRestaurantModel(restaurantId);

        restaurant.setOpen(true);
        restaurant.setDateUpdated(OffsetDateTime.now());
        restaurantRepository.saveAndFlush(restaurant);
        }

        @Transactional
        public void closeRestaurant(UUID restaurantId){
        RestaurantModel restaurant = this.returnRestaurantModel(restaurantId);

        restaurant.setOpen(false);
        restaurant.setDateUpdated(OffsetDateTime.now());
        restaurantRepository.saveAndFlush(restaurant);
        }

        public List<UserOutput> listResponsiblesUsersForRestaurant(UUID id){
            RestaurantModel restaurant = this.returnRestaurantModel(id);
            return restaurant.getUsers().stream().map(userMapper::toOutput).toList();
        }

        @Transactional
        public void addResponsibleUser(UUID restaurantId, UUID userId){

        RestaurantModel restaurant = this.returnRestaurantModel(restaurantId);

            UserModel user = userService.returnUserModel(userId);

            if(!user.getRestaurants().contains(restaurant)){
                user.getRestaurants().add(restaurant);
            }
        }

        @Transactional
        public void removeResponsibleUser(UUID restaurantId, UUID userId){

        RestaurantModel restaurant = this.returnRestaurantModel(restaurantId);

        UserModel user = userService.returnUserModel(userId);

        if(!user.getRestaurants().contains(restaurant)){
            throw new BusinessException("User is not in restaurant list");
            }

            user.getRestaurants().remove(restaurant);
        }

        @Transactional
        public void activateRestaurants(List<UUID> restaurantsIds){

        try {

            restaurantsIds.forEach(this::activateRestaurant);

        }catch (RestaurantNotFoundException e){

            throw new BusinessException(e.getMessage());

            }
        }

        @Transactional
        public void deactivateRestaurants(List<UUID> restaurantsIds){

        try {

            restaurantsIds.forEach(this::deactivateRestaurant);

        } catch (RestaurantNotFoundException e){

            throw new BusinessException(e.getMessage());
        }
    }

        public RestaurantModel verifyFieldRestaurant(UUID id){
        return restaurantRepository.findById(id).orElseThrow(()-> new BusinessException("restaurant", id));
        }
}
