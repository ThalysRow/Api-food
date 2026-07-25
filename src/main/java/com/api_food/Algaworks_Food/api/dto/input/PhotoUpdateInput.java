package com.api_food.Algaworks_Food.api.dto.input;

import com.api_food.Algaworks_Food.core.validation.annotation.FileContentType;
import com.api_food.Algaworks_Food.core.validation.annotation.FileSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUpdateInput {

    @NotNull(message = "The field image is required")
    @FileSize(max = "20MB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    private MultipartFile image;
    @NotBlank(message = "The field description is required")
    private String description;
}
