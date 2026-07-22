package com.api_food.Algaworks_Food.core.validation;

import com.api_food.Algaworks_Food.core.validation.annotation.FileSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize max;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        this.max = DataSize.parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file == null || file.getSize() <= max.toBytes();
    }
}