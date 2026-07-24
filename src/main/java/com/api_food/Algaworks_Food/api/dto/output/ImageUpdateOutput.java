package com.api_food.Algaworks_Food.api.dto.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageUpdateOutput {
    private int productId;
    private String fileName;
    private String description;
    private String contentType;
    private int contentSize;
    private String url;
}
