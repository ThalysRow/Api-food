package com.api_food.Algaworks_Food.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenListDTO {
    private UUID id;
    private String name;
}
