package com.api_food.Algaworks_Food.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenDTO {
    private UUID id;
    private String name;
}
