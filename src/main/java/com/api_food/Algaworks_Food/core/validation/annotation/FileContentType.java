package com.api_food.Algaworks_Food.core.validation.annotation;

import com.api_food.Algaworks_Food.core.validation.FileContentTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileContentTypeValidator.class)
public @interface FileContentType {

    String message() default "The file type must be .jpg or .png";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

    String[] allowed();
}
