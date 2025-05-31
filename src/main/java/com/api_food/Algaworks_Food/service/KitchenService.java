package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.KitchenCreateDTO;
import com.api_food.Algaworks_Food.dto.list.KitchenListDTO;
import com.api_food.Algaworks_Food.dto.update.KitchenUpdateDTO;
import com.api_food.Algaworks_Food.exception.EntityInUseException;
import com.api_food.Algaworks_Food.exception.EntityNotFoundException;
import com.api_food.Algaworks_Food.mapper.KitchenMapper;
import com.api_food.Algaworks_Food.model.KitchenModel;
import com.api_food.Algaworks_Food.repository.KitchenRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;

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
       KitchenModel kitchenFinded = kitchenRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Kitchen not found"));
       return kitchenMapper.toCreateListDTO(kitchenFinded);
    }

    public void deleteKitchen(UUID id){

        KitchenModel kitchen = kitchenRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Kitchen not found"));

        if (kitchen.getRestaurants() != null && !kitchen.getRestaurants().isEmpty()){
            throw new EntityInUseException("Kitchen cannot be deleted, it is in use by restaurants.");
        }

        kitchenRepository.deleteById(kitchen.getId());
    }

    public KitchenUpdateDTO updateKitchen(UUID id, KitchenUpdateDTO kitchen){
        KitchenListDTO kitchenFinded = this.findKitchenById(id);
        String nameFormated = stringFormatter.stringFormated(kitchen.getName());
        kitchenFinded.setName(nameFormated);

        KitchenModel newKitchen = kitchenMapper.toModel(kitchenFinded);
        KitchenModel savedKithen = kitchenRepository.save(newKitchen);
        return kitchenMapper.toUpdateDTO(savedKithen);
    }
}
