package com.api_food.Algaworks_Food.api.controller;

import com.api_food.Algaworks_Food.api.dto.input.RestaurantInput;
import com.api_food.Algaworks_Food.api.dto.output.PaymentMethodOutput;
import com.api_food.Algaworks_Food.api.dto.output.RestaurantOutput;
import com.api_food.Algaworks_Food.api.dto.output.UserOutput;
import com.api_food.Algaworks_Food.domain.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantOutput newRestaurant(@Valid @RequestBody RestaurantInput input){
        return  restaurantService.newRestaurant(input);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantOutput findRestaurant(@PathVariable UUID id){
        return restaurantService.findRestaurantById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantOutput> lisRestaurants(){
        return restaurantService.listRestaurants();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantOutput updateRestaurant(@PathVariable UUID id,
                                                                @Valid @RequestBody RestaurantInput input){
       return restaurantService.updateRestaurant(id, input);
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
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentMethodOutput> restaurantPaymentMethods(@PathVariable UUID id){
       return restaurantService.restaurantListPaymentMethods(id);
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
    @ResponseStatus(HttpStatus.OK)
    public List<UserOutput> listResponsiblesForRestaurant(@PathVariable UUID id){
        return restaurantService.listResponsiblesUsersForRestaurant(id);
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
    public void activateRestaurants(@RequestBody List<UUID> restaurantsIds){
        restaurantService.activateRestaurants(restaurantsIds);
    }

    @DeleteMapping("/activates")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateRestaurants(@RequestBody List<UUID> restaurantsIds){
        restaurantService.deactivateRestaurants(restaurantsIds);
    }
}
