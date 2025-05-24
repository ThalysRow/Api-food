package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.Mapper.CityMapper;
import com.api_food.Algaworks_Food.dto.CityDTO;
import com.api_food.Algaworks_Food.model.CityModel;
import com.api_food.Algaworks_Food.model.StateModel;
import com.api_food.Algaworks_Food.repository.CityRepository;
import com.api_food.Algaworks_Food.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

        StateModel state = stateRepository.findById(city.getState().getId()).orElseThrow(()-> new RuntimeException("State not found"));

        CityModel newCity = cityMapper.toEntity(city);
        newCity.setName(city.getName());
        newCity.setState(state);

        CityModel saveCity = cityRepository.save(newCity);
        return cityMapper.toDTO(saveCity);
    }

    public List<CityDTO> listCities(){
        return cityRepository.findAll().stream().map(cityMapper::toDTO).toList();
    }

    public CityDTO findCity(int id){
        CityModel cityFinded = cityRepository.findById(id).orElseThrow(()-> new RuntimeException("City not found"));
        return cityMapper.toDTO(cityFinded);
    }

    public void deleteCity(int id){
        CityModel city = cityRepository.findById(id).orElseThrow(()-> new RuntimeException("City not found"));
        cityRepository.deleteById(city.getId());
    }

    public CityDTO updateCity(int id, CityDTO city){
        CityModel cityFinded = cityRepository.findById(id).orElseThrow(()-> new RuntimeException("City not found"));

        cityFinded.setName(city.getName());

        StateModel state = stateRepository.findById(city.getState().getId()).orElseThrow(()-> new RuntimeException("State not found"));

        cityFinded.setState(state);

        CityModel saveCity = cityRepository.save(cityFinded);
        return cityMapper.toDTO(saveCity);


    }
}
