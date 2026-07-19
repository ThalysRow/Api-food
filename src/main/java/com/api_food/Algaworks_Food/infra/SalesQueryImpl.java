package com.api_food.Algaworks_Food.infra;

import com.api_food.Algaworks_Food.api.dto.output.DailySalesOutput;
import com.api_food.Algaworks_Food.domain.enums.OrderStatus;
import com.api_food.Algaworks_Food.domain.filter.DailySalesFilter;
import com.api_food.Algaworks_Food.domain.model.OrderModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalesQueryImpl implements SalesQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<DailySalesOutput> searchDailySales(DailySalesFilter filter) {

        var predicates = new ArrayList<Predicate>();

        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(DailySalesOutput.class);
        var root = query.from(OrderModel.class);

        predicates.add(root.get("status").in(OrderStatus.CONFIRMED, OrderStatus.DELIVERED));

        if (filter.getRestaurantId() != null){
            predicates.add(builder.equal(root.get("restaurant").get("id"), filter.getRestaurantId()));
        }

        if (filter.getCreatedAtFrom() != null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("dateCreated"), filter.getCreatedAtFrom()));
        }

        if(filter.getCreatedAtUntil() != null){
            predicates.add(builder.lessThanOrEqualTo(root.get("dateCreated"), filter.getCreatedAtUntil()));
        }

        var functionDateCreated = builder.function("date",
                LocalDate.class,
                root.get("dateCreated"));

        var select = builder.construct(DailySalesOutput.class,
                functionDateCreated,
                builder.count(root.get("id")),
                builder.sum(root.get("totalValue"))
                );

        query.select(select);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateCreated);

        return manager.createQuery(query).getResultList();
    }
}
