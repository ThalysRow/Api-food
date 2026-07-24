package com.api_food.Algaworks_Food.infra.storage;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoStorage {

    String save(MultipartFile file, int productId);
    void remove(String fileName);
    String getUrl(String url);
}
