package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.Mapper.KitchenMapper;
import com.api_food.Algaworks_Food.dto.KitchenDTO;
import com.api_food.Algaworks_Food.model.KitchenModel;
import com.api_food.Algaworks_Food.repository.KitchenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KitchenService {
    private final KitchenMapper kitchenMapper;
    private final KitchenRepository kitchenRepository;

    public KitchenService(KitchenMapper kitchenMapper, KitchenRepository kitchenRepository) {
        this.kitchenMapper = kitchenMapper;
        this.kitchenRepository = kitchenRepository;
    }

    public KitchenDTO addKitchen(KitchenDTO kitchen){
        KitchenModel kitchenModel = kitchenMapper.toEntity(kitchen);
        KitchenModel kitchenSaved = kitchenRepository.save(kitchenModel);
        return kitchenMapper.toDTO(kitchenSaved);
    }

    public List<KitchenDTO> listKitchens(){
        return kitchenRepository.findAll().stream().map(kitchenMapper::toDTO).toList();
    }
}
