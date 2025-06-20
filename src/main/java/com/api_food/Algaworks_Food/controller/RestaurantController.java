package com.api_food.Algaworks_Food.controller;

import com.api_food.Algaworks_Food.dto.create.RestaurantCreateDTO;
import com.api_food.Algaworks_Food.dto.list.PaymentMethodListDTO;
import com.api_food.Algaworks_Food.dto.list.RestaurantListDTO;
import com.api_food.Algaworks_Food.dto.list.UserListDTO;
import com.api_food.Algaworks_Food.dto.update.RestaurantUpdateDTO;
import com.api_food.Algaworks_Food.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/add")
    public ResponseEntity<RestaurantCreateDTO> newRestaurant(@Valid @RequestBody RestaurantCreateDTO restaurant){
        RestaurantCreateDTO newRestaurant = restaurantService.newRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRestaurant);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantListDTO> findRestaurant(@PathVariable UUID id){
        RestaurantListDTO restaurant = restaurantService.findRestaurantById(id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantListDTO>> lisRestaurants(){
        List<RestaurantListDTO> restaurants = restaurantService.listRestaurants();
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantUpdateDTO> updateRestaurant(@PathVariable UUID id, @Valid @RequestBody RestaurantUpdateDTO restaurant){
        RestaurantUpdateDTO updateRestaurant = restaurantService.updateRestaurant(id, restaurant);
        return ResponseEntity.status(HttpStatus.OK).body(updateRestaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable UUID id){
        restaurantService.deleteRestaurant(id);
    }

    @PutMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void restaurantOn(@PathVariable UUID id){
        restaurantService.activateRestaurant(id);
    }

    @DeleteMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void restaurantOff(@PathVariable UUID id){
        restaurantService.deactivateRestaurant(id);
    }

    @GetMapping("/{id}/payment-method/all")
    public ResponseEntity<List<PaymentMethodListDTO>> restaurantPaymentMethods(@PathVariable UUID id){
        List<PaymentMethodListDTO> paymentMethods = restaurantService.restaurantListPaymentMethods(id);
        return ResponseEntity.status(HttpStatus.OK).body(paymentMethods);
    }

    @DeleteMapping("/{restaurantId}/payment-method/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void restaurantRemovePaymentMethod(@PathVariable UUID restaurantId, @PathVariable int paymentMethodId){
        restaurantService.restaurantRemovePaymentMethod(restaurantId, paymentMethodId);
    }

    @PutMapping("/{restaurantId}/payment-method/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void restaurantAddPaymentMethod(@PathVariable UUID restaurantId, @PathVariable int paymentMethodId){
        restaurantService.restaurantAddPaymentMethod(restaurantId, paymentMethodId);
    }

    @PutMapping("/{id}/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void restaurantOpen(@PathVariable UUID id){
        restaurantService.restaurantOpen(id);
    }

    @PutMapping("/{id}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void restaurantClose(@PathVariable UUID id){
        restaurantService.closeRestaurant(id);
    }

    @GetMapping("/{id}/responsibles")
    public ResponseEntity<List<UserListDTO>> listResponsiblesForRestaurant(@PathVariable UUID id){
        List<UserListDTO> users = restaurantService.listResponsibleUsersForRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/{restaurantId}/responsibles/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addResponsibleUser(@PathVariable UUID restaurantId, @PathVariable UUID userId){
        restaurantService.addResponsibleUser(restaurantId, userId);
    }

    @DeleteMapping("/{restaurantId}/responsibles/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeResponsibleUser(@PathVariable UUID restaurantId, @PathVariable UUID userId){
        restaurantService.removeResponsibleUser(restaurantId, userId);
    }

    @PutMapping("/activates")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateRestaurants(@RequestBody List<UUID> ids){
        restaurantService.activateRestaurants(ids);
    }

    @DeleteMapping("/activates")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateRestaurants(@RequestBody List<UUID> ids){
        restaurantService.deactivateRestaurants(ids);
    }
}
