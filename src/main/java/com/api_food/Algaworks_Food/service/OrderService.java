package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.OrderCreateDTO;
import com.api_food.Algaworks_Food.dto.list.OrderListDTO;
import com.api_food.Algaworks_Food.dto.list.OrderResumeListDTO;
import com.api_food.Algaworks_Food.dto.list.ProductListDTO;
import com.api_food.Algaworks_Food.enums.OrderStatus;
import com.api_food.Algaworks_Food.exception.custom.OrderNotFoundException;
import com.api_food.Algaworks_Food.mapper.OrderMapper;
import com.api_food.Algaworks_Food.model.*;
import com.api_food.Algaworks_Food.repository.OrderRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final StringFormatter stringFormatter;
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final PaymentMethodService paymentMethodService;
    private final CityService cityService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, StringFormatter stringFormatter, RestaurantService restaurantService, UserService userService, PaymentMethodService paymentMethodService, CityService cityService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.stringFormatter = stringFormatter;
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.paymentMethodService = paymentMethodService;
        this.cityService = cityService;
        this.productService = productService;
    }

    @Transactional
    public OrderCreateDTO createNewOrder(OrderCreateDTO data){

        UUID restaurantId = data.getRestaurant().getId();
        int paymentMethodId = data.getPaymentMethod().getId();
        UUID userId = data.getUser().getId();
        int cityId = data.getDeliveryAddress().getCity().getId();

        RestaurantModel restaurant = restaurantService.verifyFieldRestaurant(restaurantId);
        UserModel user = userService.verifyUserField(userId);
        PaymentMethodModel paymentMethod = paymentMethodService.verifyPaymentField(restaurantId, paymentMethodId);
        CityModel city = cityService.verifyCityField(cityId);


        List<Integer> productsId = data.getItens().stream().map(productId -> productId.getId()).toList();

        productService.verifyProductsField(restaurantId, productsId);

        BigDecimal deliveryFee = restaurant.getDeliveryFee();
        BigDecimal subtotal = new BigDecimal(0);

        for (int i = 0; i < data.getItens().size(); i++) {
            BigDecimal quantity = data.getItens().get(i).getQuantity();
            int productId = data.getItens().get(i).getId();

            ProductListDTO product = productService.findProductInRestaurant(restaurant.getId(), productId);

            subtotal = subtotal.add(product.getPrice().multiply(quantity));
        }

        BigDecimal totalValue = subtotal.add(deliveryFee);

        OrderModel addOrder =  orderMapper.toCreateModel(data);

        addOrder.setSubtotal(subtotal);
        addOrder.setDeliveryFee(deliveryFee);
        addOrder.setTotalValue(totalValue);
        addOrder.setStatus(OrderStatus.CREATED);
        addOrder.setDeliveryAddress(stringFormatter.addressFieldsFormatter(addOrder.getDeliveryAddress()));
        addOrder.getDeliveryAddress().setCity(city);
        addOrder.setPaymentMethod(paymentMethod);
        addOrder.setRestaurant(restaurant);
        addOrder.setUser(user);
        addOrder.setDateCreated(OffsetDateTime.now());
        addOrder.setItens(data.getItens().stream().map(
                item -> {
                    ProductModel product = productService.returnProductModel(item.getId());
                    OrderItemModel orderItem = new OrderItemModel();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setUnitPrice(product.getPrice());
                    orderItem.setTotalPrice(product.getPrice().multiply(item.getQuantity()));
                    orderItem.setObservations(stringFormatter.stringFormated(item.getObservations()));
                    orderItem.setOrder(addOrder);
                    return orderItem;
                }).toList());

        OrderModel savedOrder = orderRepository.save(addOrder);

        return orderMapper.toCreateDTO(savedOrder);
    }

    public OrderListDTO findOrderById(Integer id){
        return orderRepository.findById(id).map(orderMapper::toListDTO).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public List<OrderResumeListDTO> listAllOrders(){
        return  orderRepository.findAll().stream().map(orderMapper::toListResumeDTO).toList();
    }

    public OrderModel returnOrderModel(Integer orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
