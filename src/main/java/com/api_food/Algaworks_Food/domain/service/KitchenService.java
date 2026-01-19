package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.api.dto.create.KitchenCreateDTO;
import com.api_food.Algaworks_Food.api.dto.list.KitchenListDTO;
import com.api_food.Algaworks_Food.api.dto.update.KitchenUpdateDTO;
import com.api_food.Algaworks_Food.domain.exception.custom.BusinessException;
import com.api_food.Algaworks_Food.domain.exception.custom.EntityInUseException;
import com.api_food.Algaworks_Food.domain.exception.custom.KitchenNotFoundException;
import com.api_food.Algaworks_Food.domain.mapper.KitchenMapper;
import com.api_food.Algaworks_Food.domain.model.KitchenModel;
import com.api_food.Algaworks_Food.domain.repository.KitchenRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class KitchenService {
    private final KitchenMapper kitchenMapper;
    private final KitchenRepository kitchenRepository;
    private final StringFormatter stringFormatter;

    public KitchenService(KitchenMapper kitchenMapper, KitchenRepository kitchenRepository, StringFormatter stringFormatter) {
        this.kitchenMapper = kitchenMapper;
        this.kitchenRepository = kitchenRepository;
        this.stringFormatter = stringFormatter;
    }

    @Transactional
    public KitchenCreateDTO addKitchen(KitchenCreateDTO kitchen){

       String formatedName = stringFormatter.stringFormated(kitchen.getName());
        kitchen.setName(formatedName);

        KitchenModel newKitchen = kitchenMapper.toCreateModel(kitchen);
        KitchenModel kitchenSaved = kitchenRepository.save(newKitchen);
        return kitchenMapper.toCreateDTO(kitchenSaved);
    }

    public List<KitchenListDTO> listKitchens(){
        return kitchenRepository.findAll().stream().map(kitchenMapper::toCreateListDTO).toList();
    }

    public KitchenListDTO findKitchenById(UUID id){
       KitchenModel kitchenFinded = kitchenRepository.findById(id).orElseThrow(()-> new KitchenNotFoundException(id));
       return kitchenMapper.toCreateListDTO(kitchenFinded);
    }

    @Transactional
    public void deleteKitchen(UUID id){

        KitchenModel kitchen = kitchenRepository.findById(id).orElseThrow(()-> new KitchenNotFoundException(id));

        if (kitchen.getRestaurants() != null && !kitchen.getRestaurants().isEmpty()){
            throw new EntityInUseException("kitchen", kitchen.getId(), "restaurants");
        }

        kitchenRepository.deleteById(kitchen.getId());
    }

    @Transactional
    public KitchenUpdateDTO updateKitchen(UUID id, KitchenUpdateDTO kitchen){
        KitchenListDTO kitchenFinded = this.findKitchenById(id);
        String nameFormated = stringFormatter.stringFormated(kitchen.getName());
        kitchenFinded.setName(nameFormated);

        KitchenModel newKitchen = kitchenMapper.toModel(kitchenFinded);
        KitchenModel savedKithen = kitchenRepository.save(newKitchen);
        return kitchenMapper.toUpdateDTO(savedKithen);
    }

    public KitchenModel verifyKitchen(UUID id){
        return kitchenRepository.findById(id).orElseThrow(()-> new BusinessException("Kitchen", id));
    }

}
