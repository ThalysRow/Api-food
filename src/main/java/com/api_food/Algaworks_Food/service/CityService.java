package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.CityCreateDTO;
import com.api_food.Algaworks_Food.dto.list.CityListDTO;
import com.api_food.Algaworks_Food.dto.update.CityUpdateDTO;
import com.api_food.Algaworks_Food.exception.custom.BusinessException;
import com.api_food.Algaworks_Food.exception.custom.CityNotFoundException;
import com.api_food.Algaworks_Food.mapper.CityMapper;
import com.api_food.Algaworks_Food.model.CityModel;
import com.api_food.Algaworks_Food.model.StateModel;
import com.api_food.Algaworks_Food.repository.CityRepository;
import com.api_food.Algaworks_Food.repository.StateRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityMapper cityMapper;
    private final CityRepository cityRepository;
    private final StringFormatter stringFormatter;
    private final StateRepository stateRepository;

    public CityService(CityMapper cityMapper, CityRepository cityRepository, StringFormatter stringFormatter, StateRepository stateRepository) {
        this.cityMapper = cityMapper;
        this.cityRepository = cityRepository;
        this.stringFormatter = stringFormatter;
        this.stateRepository = stateRepository;
    }

    public CityCreateDTO addCity(CityCreateDTO city){

        StateModel state = stateRepository.findById(city.getState().getId())
                .orElseThrow(()-> new BusinessException(String.format("State with id '%d', does not exist", city.getState().getId()), new Throwable()));

        String nameFormated = stringFormatter.stringFormated(city.getName());

        CityModel newCity = cityMapper.toCreateModel(city);
        newCity.setName(nameFormated);
        newCity.setState(state);

        CityModel saveCity = cityRepository.save(newCity);

        return cityMapper.toCreateDTO(saveCity);

    }

    public List<CityListDTO> listCities(){
        return cityRepository.findAll().stream().map(cityMapper::toCreateListDTO).toList();
    }

    public CityListDTO findCity(int id){
        CityModel cityFinded = cityRepository.findById(id).orElseThrow(()-> new CityNotFoundException(id));
        return cityMapper.toCreateListDTO(cityFinded);
    }

    public void deleteCity(int id){
        CityListDTO city = this.findCity(id);
        cityRepository.deleteById(city.getId());
    }

    public CityUpdateDTO updateCity(int id, CityUpdateDTO city){
        CityListDTO cityFinded = this.findCity(id);
        StateModel state = stateRepository.findById(city.getState().getId())
                .orElseThrow(()-> new BusinessException(String.format("State with id '%d', does not exist.", city.getState().getId()), new Throwable()));

        String nameFormated = stringFormatter.stringFormated(city.getName());

        CityModel updateCity = cityMapper.toUpdateModel(cityFinded);
        updateCity.setName(nameFormated);
        updateCity.setState(state);
        CityModel saveCity = cityRepository.save(updateCity);
        return cityMapper.toUpdateDTO(saveCity);


    }
}
