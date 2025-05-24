package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.Mapper.CityMapper;
import com.api_food.Algaworks_Food.dto.CityDTO;
import com.api_food.Algaworks_Food.model.CityModel;
import com.api_food.Algaworks_Food.model.StateModel;
import com.api_food.Algaworks_Food.repository.CityRepository;
import com.api_food.Algaworks_Food.repository.StateRepository;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    private final CityMapper cityMapper;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    public CityService(CityMapper cityMapper, CityRepository cityRepository, StateRepository stateRepository) {
        this.cityMapper = cityMapper;
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    public CityDTO addCity(CityDTO city){

        int stateId = city.getState_id().getId();
        StateModel state =  stateRepository.findById(stateId).orElseThrow(()-> new RuntimeException("State not found"));

        CityModel newCity = new CityModel();
        newCity.setName(city.getName());
        newCity.setState(state);
        cityRepository.save(newCity);
        return cityMapper.toDTO(newCity);
    }
}
