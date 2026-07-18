package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.api.dto.input.KitchenInput;
import com.api_food.Algaworks_Food.api.dto.output.KitchenOutput;
import com.api_food.Algaworks_Food.api.dto.output.PageResponseOutput;
import com.api_food.Algaworks_Food.domain.exception.custom.BusinessException;
import com.api_food.Algaworks_Food.domain.exception.custom.EntityInUseException;
import com.api_food.Algaworks_Food.domain.exception.custom.KitchenNotFoundException;
import com.api_food.Algaworks_Food.domain.mapper.KitchenMapper;
import com.api_food.Algaworks_Food.domain.model.KitchenModel;
import com.api_food.Algaworks_Food.domain.repository.KitchenRepository;
import com.api_food.Algaworks_Food.utils.Formatter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KitchenService {
    private final KitchenMapper kitchenMapper;
    private final KitchenRepository kitchenRepository;


    private KitchenModel returnKitchenModel(UUID kitchenId) {
        return kitchenRepository.findById(kitchenId).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    }

    @Transactional
    public KitchenOutput addKitchen(KitchenInput input){

        String name = Formatter.string(input.getName());

        KitchenModel kitchen = KitchenModel.addKitchen(name);

        kitchenRepository.saveAndFlush(kitchen);

        return kitchenMapper.toOutput(kitchen);
    }

    public PageResponseOutput<KitchenOutput> listKitchens(Pageable pageable){
        return PageResponseOutput.of(kitchenRepository.findAll(pageable).map(kitchenMapper::toOutput));
    }

    public KitchenOutput findKitchenById(UUID id){
       return kitchenMapper.toOutput(this.returnKitchenModel(id));
    }

    @Transactional
    public void deleteKitchen(UUID id){

        KitchenModel kitchen = this.returnKitchenModel(id);

        if (kitchen.getRestaurants() != null && !kitchen.getRestaurants().isEmpty()){
            throw new EntityInUseException("kitchen", kitchen.getId(), "restaurants");
        }

        kitchenRepository.deleteById(kitchen.getId());
    }

    @Transactional
    public KitchenOutput updateKitchen(UUID id, KitchenInput input){

        input.setName(Formatter.string(input.getName()));

        KitchenModel kitchen = this.returnKitchenModel(id);

        kitchen.setName(input.getName());
        kitchenRepository.saveAndFlush(kitchen);

        return kitchenMapper.toOutput(kitchen);

    }

    public KitchenModel verifyKitchen(UUID id){
        return kitchenRepository.findById(id).orElseThrow(()-> new BusinessException("Kitchen", id));
    }

}
