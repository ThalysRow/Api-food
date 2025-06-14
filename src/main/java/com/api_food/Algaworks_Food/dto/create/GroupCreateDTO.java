package com.api_food.Algaworks_Food.dto.create;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreateDTO {
private int id;
@NotBlank(message = "The group name is required.")
private String name;
}
