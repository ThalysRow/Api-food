package com.api_food.Algaworks_Food.dto.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {
    private UUID id;
    @NotBlank(message = "The name field is required.")
    private String name;
    @Email(message = "The email field must receive a valid email")
    @NotBlank(message = "The email field is required")
    private String email;
    @Size(min = 8, max = 30, message = "The password field must contain between 8 and 30 characters.")
    @NotBlank(message = "The password field is required")
    @Pattern(regexp = "^\\S*$", message = "Spaces are not allowed in the password field.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
