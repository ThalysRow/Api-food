package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.exception.EntityInUseException;
import com.api_food.Algaworks_Food.exception.EntityNotFoundException;
import com.api_food.Algaworks_Food.mapper.KitchenMapper;
import com.api_food.Algaworks_Food.dto.KitchenDTO;
import com.api_food.Algaworks_Food.model.KitchenModel;
import com.api_food.Algaworks_Food.repository.KitchenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public KitchenDTO findKitchenById(UUID id){
       KitchenModel kitchenFinded = kitchenRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Kitchen not found"));
       return kitchenMapper.toDTO(kitchenFinded);
    }

    public void deleteKitchen(UUID id){
        KitchenDTO kitchenFinded = this.findKitchenById(id);

        if (kitchenFinded.getRestaurants() != null && !kitchenFinded.getRestaurants().isEmpty()){
            throw new EntityInUseException("Kitchen cannot be deleted, it is in use by restaurants.");
        }

        kitchenRepository.deleteById(kitchenFinded.getId());
    }

}
