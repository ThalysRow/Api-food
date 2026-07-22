package com.api_food.Algaworks_Food.core.validation.annotation;

import com.api_food.Algaworks_Food.core.validation.FileSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileSizeValidator.class)
public @interface FileSize {

    String message() default "The image max-size is 20MB";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String max();
}
