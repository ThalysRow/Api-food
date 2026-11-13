package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.api.dto.input.OrderFilterInput;
import com.api_food.Algaworks_Food.api.dto.input.OrderInput;
import com.api_food.Algaworks_Food.api.dto.output.*;
import com.api_food.Algaworks_Food.domain.enums.OrderStatus;
import com.api_food.Algaworks_Food.domain.exception.custom.OrderNotFoundException;
import com.api_food.Algaworks_Food.domain.mapper.OrderMapper;
import com.api_food.Algaworks_Food.domain.model.*;
import com.api_food.Algaworks_Food.domain.repository.OrderRepository;
import com.api_food.Algaworks_Food.domain.specs.OrderSpecs;
import com.api_food.Algaworks_Food.utils.Formatter;
import com.api_food.Algaworks_Food.utils.PageableTranslator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final Formatter stringFormatter;
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final CityService cityService;
    private final ProductService productService;


    @Transactional
    public OrderOutput createNewOrder(OrderInput input){

        UUID restaurantId = input.getRestaurant().getId();
        int paymentMethodId = input.getPaymentMethod().getId();
        UUID userId = input.getUser().getId();
        int cityId = input.getDeliveryAddress().getCity().getId();

        RestaurantModel restaurant = restaurantService.verifyFieldRestaurant(restaurantId);
        UserModel user = userService.verifyUserField(userId);
        PaymentMethodModel paymentMethod = restaurantService.verifyPaymentField(restaurantId, paymentMethodId);
        CityModel city = cityService.verifyCityField(cityId);


        List<Integer> productsId = input.getItens().stream().map(ProductInOrderOutput::getId).toList();

        List<ProductModel> productsFound = productService.returnProductModels(restaurantId, productsId);

        Map<Integer, ProductModel> productsMap = productsFound.stream()
                .collect(Collectors.toMap(ProductModel::getId, p -> p));

        BigDecimal deliveryFee = restaurant.getDeliveryFee();

        List<OrderItemModel> orderItens = new ArrayList<>();

        OrderModel addOrder =  orderMapper.toModel(input);

       for (ProductInOrderOutput itemInput : input.getItens()){

               ProductModel product = productsMap.get(itemInput.getId());
                OrderItemModel orderItem = new OrderItemModel();
                orderItem.setProduct(product);
                orderItem.setQuantity(itemInput.getQuantity());
                orderItem.setUnitPrice(product.getPrice());
                orderItem.setObservations(Formatter.string(itemInput.getObservations()));

                orderItens.add(orderItem);

        }

        addOrder.setDeliveryFee(deliveryFee);

        addOrder.setItens(orderItens);

        addOrder.calcTotalValue();

        addOrder.setStatus(OrderStatus.CREATED);
        addOrder.setDeliveryAddress(stringFormatter.addressFieldsFormatter(addOrder.getDeliveryAddress()));
        addOrder.getDeliveryAddress().setCity(city);
        addOrder.setPaymentMethod(paymentMethod);
        addOrder.setRestaurant(restaurant);
        addOrder.setUser(user);
        addOrder.setDateCreated(OffsetDateTime.now());


        orderRepository.saveAndFlush(addOrder);

        return this.findOrderById(addOrder.getId());
    }

    public OrderOutput findOrderById(Integer id){
        return orderRepository.findById(id)
                .map(orderMapper::toOutput)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public PageResponseOutput<OrderResumeOutput> listOrders(OrderFilterInput filter, Pageable pageable) {
        var mapping = Map.of(
                "id", "id",
                "userName", "user.name",
                "restaurantName", "restaurant.name",
                "totalValue", "totalValue"
        );

        Pageable pageableTranslated = PageableTranslator.translate(pageable, mapping);

        Specification<OrderModel> specs = OrderSpecs.appFilter(filter);
        return PageResponseOutput.of(orderRepository.findAll(specs, pageableTranslated).map(orderMapper::toResumeOutput));
    }

    public OrderModel returnOrderModel(Integer orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
