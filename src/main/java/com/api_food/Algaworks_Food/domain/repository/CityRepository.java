package com.api_food.Algaworks_Food.domain.repository;
import com.api_food.Algaworks_Food.domain.model.CityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<CityModel, Integer> {
    @Query("SELECT COUNT(r) > 0 FROM RestaurantModel r WHERE r.address.city.id = :cityId")
    boolean isCityInUseByRestaurant(@Param("cityId") int cityId);

    @Query("SELECT COUNT(o) > 0 FROM OrderModel o WHERE o.deliveryAddress.city.id = :cityId")
    boolean isCityInUseByOrder(@Param("cityId") int cityId);
}
