package com.api_food.Algaworks_Food.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordDTO {
    @NotBlank(message = "The currentPassword field is required.")
    @Pattern(regexp = "^\\S*$", message = "Spaces are not allowed in the password field.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String currentPassword;
    @NotBlank(message = "The newPassword field is required.")
    @Pattern(regexp = "^\\S*$", message = "Spaces are not allowed in the password field.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newPassword;
}
