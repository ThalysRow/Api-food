package com.api_food.Algaworks_Food.dto.update;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private UUID id;
    @NotBlank(message = "The name field is required.")
    private String name;
    @NotBlank(message = "The email field is required")
    @Email(message = "The email field must receive a valid email")
    private String email;
}
