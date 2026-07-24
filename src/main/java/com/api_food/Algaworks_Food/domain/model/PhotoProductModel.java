package com.api_food.Algaworks_Food.domain.model;

import com.api_food.Algaworks_Food.utils.Formatter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "photo_product")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class PhotoProductModel {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "product_id")
    private int id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "description")
    private String description;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "content_size", nullable = false)
    private int contentSize;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private ProductModel product;

    public static PhotoProductModel create(ProductModel product, String fileName,
                                      String description, String contentType, int contentSize) {

        PhotoProductModel photo = new PhotoProductModel();

        photo.setProduct(product);
        photo.setFileName(fileName);
        photo.setDescription(Formatter.string(description));
        photo.setContentType(contentType);
        photo.setContentSize(contentSize);

        return photo;
    }

    public void update(String fileName,
                                           String description, String contentType, int contentSize) {
        this.setFileName(fileName);
        this.setDescription(Formatter.string(description));
        this.setContentType(contentType);
        this.setContentSize(contentSize);

    }

}
