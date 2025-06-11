package com.api_food.Algaworks_Food.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.OffsetDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class MessageException {

    private Integer status;
    private String type;
    private String title;
    private String detail;
    private OffsetDateTime dateTime;
}
