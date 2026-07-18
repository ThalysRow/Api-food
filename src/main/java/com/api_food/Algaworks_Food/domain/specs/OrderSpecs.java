package com.api_food.Algaworks_Food.domain.specs;

import com.api_food.Algaworks_Food.api.dto.input.OrderFilter;
import com.api_food.Algaworks_Food.domain.model.OrderModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class OrderSpecs {
    public static Specification<OrderModel> appFilter(OrderFilter filter) {
        return (root, query, builder) -> {
            var predicates = new ArrayList<Predicate>();

            if (filter != null) {
                if (filter.getUserId() != null) {
                    predicates.add(builder.equal(root.get("user").get("id"), filter.getUserId()));
                }

                if (filter.getRestaurantId() != null) {
                    predicates.add(builder.equal(root.get("restaurant").get("id"), filter.getRestaurantId()));
                }

                if (filter.getCreatedAtFrom() != null) {
                    predicates.add(builder.greaterThanOrEqualTo(root.get("dateCreated"), filter.getCreatedAtFrom()));
                }

                if (filter.getCreatedAtUntil() != null) {
                    predicates.add(builder.lessThanOrEqualTo(root.get("dateCreated"), filter.getCreatedAtUntil()));
                }
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
