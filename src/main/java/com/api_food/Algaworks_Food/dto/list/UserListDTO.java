package com.api_food.Algaworks_Food.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDTO {
    private UUID id;
    private String name;
    private String email;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateUpdated;
}
