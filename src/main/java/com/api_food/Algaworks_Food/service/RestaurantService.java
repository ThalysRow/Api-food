package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.RestaurantCreateDTO;
import com.api_food.Algaworks_Food.dto.list.PaymentMethodListDTO;
import com.api_food.Algaworks_Food.dto.list.RestaurantListDTO;
import com.api_food.Algaworks_Food.dto.update.RestaurantUpdateDTO;
import com.api_food.Algaworks_Food.exception.custom.BusinessException;
import com.api_food.Algaworks_Food.exception.custom.PaymentMethodNotFoundException;
import com.api_food.Algaworks_Food.exception.custom.RestaurantNotFoundException;
import com.api_food.Algaworks_Food.mapper.PaymentMethodMapper;
import com.api_food.Algaworks_Food.mapper.RestaurantMapper;
import com.api_food.Algaworks_Food.model.CityModel;
import com.api_food.Algaworks_Food.model.KitchenModel;
import com.api_food.Algaworks_Food.model.PaymentMethodModel;
import com.api_food.Algaworks_Food.model.RestaurantModel;
import com.api_food.Algaworks_Food.repository.PaymentMethodRepository;
import com.api_food.Algaworks_Food.repository.RestaurantRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final StringFormatter stringFormatter;
    private final KitchenService kitchenService;
    private final CityService cityService;
    private final PaymentMethodMapper paymentMethodMapper;
    private final PaymentMethodRepository paymentMethodRepository;

    public RestaurantService(RestaurantMapper restaurantMapper, RestaurantRepository restaurantRepository, StringFormatter stringFormatter, KitchenService kitchenService, CityService cityService, PaymentMethodMapper paymentMethodMapper, PaymentMethodRepository paymentMethodRepository) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
        this.stringFormatter = stringFormatter;
        this.kitchenService = kitchenService;
        this.cityService = cityService;
        this.paymentMethodMapper = paymentMethodMapper;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Transactional
    public RestaurantCreateDTO newRestaurant(RestaurantCreateDTO restaurant){

         KitchenModel kitchenFinded = kitchenService.verifyKitchen(restaurant.getKitchen().getId());
         CityModel cityFinded = cityService.verifyCity(restaurant.getAddress().getCity().getId());

        RestaurantCreateDTO data = stringFormatter.restaurantFieldsFormatter(restaurant);

        RestaurantModel newRestaurant = restaurantMapper.toCreateModel(data);
        newRestaurant.setKitchen(kitchenFinded);
        newRestaurant.getAddress().setCity(cityFinded);
        newRestaurant.setDateCreated(OffsetDateTime.now());
        newRestaurant.setDateUpdated(OffsetDateTime.now());
        RestaurantModel saveRestaurant = restaurantRepository.save(newRestaurant);
        return restaurantMapper.toCreateDTO(saveRestaurant);
    }

    public RestaurantListDTO findRestaurantById(UUID id){
        RestaurantModel restaurant = restaurantRepository.findById(id).orElseThrow(()-> new RestaurantNotFoundException(id));
        return restaurantMapper.toListDTO(restaurant);
    }

    public List<RestaurantListDTO> listRestaurants(){
        return restaurantRepository.findAll().stream().map(restaurantMapper::toListDTO).toList();
    }

    @Transactional
    public RestaurantUpdateDTO updateRestaurant(UUID id, RestaurantUpdateDTO restaurant){

        RestaurantModel findRestaurant = restaurantRepository.findById(id)
                .orElseThrow(()-> new RestaurantNotFoundException(id));

            KitchenModel kitchenFinded = kitchenService.verifyKitchen(restaurant.getKitchen().getId());
            CityModel cityFinded = cityService.verifyCity(restaurant.getAddress().getCity().getId());

        RestaurantUpdateDTO data = stringFormatter.restaurantFieldsFormatter(restaurant);

        findRestaurant.setName(data.getName());
        findRestaurant.setDeliveryFee(restaurant.getDeliveryFee());
        findRestaurant.setKitchen(kitchenFinded);
        findRestaurant.getAddress().setCity(cityFinded);
        findRestaurant.getAddress().setZipcode(data.getAddress().getZipcode());
        findRestaurant.getAddress().setStreet(data.getAddress().getStreet());
        findRestaurant.getAddress().setNumber(data.getAddress().getNumber());
        findRestaurant.getAddress().setComplement(data.getAddress().getComplement());
        findRestaurant.getAddress().setNeighborhood(data.getAddress().getNeighborhood());
        RestaurantModel updateRestaurant = restaurantMapper.toUpdateModel(data);
        updateRestaurant.setDateUpdated(OffsetDateTime.now());

        RestaurantModel saveUpdate = restaurantRepository.save(updateRestaurant);
        return restaurantMapper.toUpdateDTO(saveUpdate);
        }

        @Transactional
        public void deleteRestaurant(UUID id){
        RestaurantListDTO restaurant = this.findRestaurantById(id);
        restaurantRepository.deleteById(restaurant.getId());
        }

        @Transactional
        public void activateRestaurant(UUID id){
            RestaurantModel restaurant = restaurantRepository.findById(id)
                    .orElseThrow(()-> new RestaurantNotFoundException(id));

            restaurant.setActive(true);
        }

        @Transactional
        public void deactivateRestaurant(UUID id){
            RestaurantModel restaurant = restaurantRepository.findById(id)
                    .orElseThrow(()-> new RestaurantNotFoundException(id));

            restaurant.setActive(false);
        }

        public List<PaymentMethodListDTO> restaurantListPaymentMethods(UUID id){
            RestaurantModel restaurant = restaurantRepository.findById(id).orElseThrow(()-> new RestaurantNotFoundException(id));

            return restaurant.getPaymentMethods().stream().map(paymentMethodMapper::toListDTO).toList();
        }

        @Transactional
        public void restaurantRemovePaymentMethod(UUID restaurantId, int paymentMethodId){
            RestaurantModel restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(()-> new RestaurantNotFoundException(restaurantId));

            PaymentMethodModel paymentMethod = paymentMethodRepository.findById(paymentMethodId)
                    .orElseThrow(()-> new PaymentMethodNotFoundException(paymentMethodId));

            if (!restaurant.getPaymentMethods().contains(paymentMethod)) {
                throw new BusinessException(String.format("The payment method '%s' is not yet associated with restaurant '%s'.", paymentMethod.getName(), restaurant.getName()));
            }
            restaurant.getPaymentMethods().remove(paymentMethod);
        }
}
