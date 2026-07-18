package com.api_food.Algaworks_Food.api.dto.output;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResponseOutput<T> {
    private final List<T> content;
    private final long pageSize;
    private final long totalPages;
    private final long totalElements;
    private final long number;
    private final long numberOfElements;

    public static <T> PageResponseOutput<T> of(Page<T> page) {
        return new PageResponseOutput<>(
                page.getContent(), page.getSize(),page.getTotalPages(),
                page.getTotalElements(), page.getNumber(), page.getNumberOfElements());
    }
}