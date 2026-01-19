package com.api_food.Algaworks_Food.api.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateInput {
    private UUID id;
    @NotBlank(message = "The name field is required.")
    private String name;
    @Email(message = "The email field must receive a valid email")
    @NotBlank(message = "The email field is required")
    private String email;
}
