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
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {
    private final CityMapper cityMapper;
    private final CityRepository cityRepository;
    private final StringFormatter stringFormatter;
    private final StateService stateService;

    public CityService(CityMapper cityMapper, CityRepository cityRepository, StringFormatter stringFormatter, StateService stateService) {
        this.cityMapper = cityMapper;
        this.cityRepository = cityRepository;
        this.stringFormatter = stringFormatter;
        this.stateService = stateService;
    }

    @Transactional
    public CityCreateDTO addCity(CityCreateDTO city){

        StateModel state = stateService.verifyStateField(city.getId());

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
        CityModel cityFound = this.returnCityModel(id);
        return cityMapper.toCreateListDTO(cityFound);
    }

    @Transactional
    public void deleteCity(int id){
        CityListDTO city = this.findCity(id);
        cityRepository.deleteById(city.getId());
    }

    @Transactional
    public CityUpdateDTO updateCity(int id, CityUpdateDTO city){
        CityModel cityFound = this.returnCityModel(id);
        StateModel state = stateService.verifyStateField(city.getState().getId());

        String nameFormated = stringFormatter.stringFormated(city.getName());

        cityFound.setName(nameFormated);
        cityFound.setState(state);
        CityModel saveCity = cityRepository.save(cityFound);
        return cityMapper.toUpdateDTO(saveCity);
    }

    public CityModel returnCityModel(int id){
        return cityRepository.findById(id).orElseThrow(()-> new CityNotFoundException(id));
    }

    public CityModel verifyCityField(int id){
        return cityRepository.findById(id).orElseThrow(()-> new BusinessException("City", id));
    }
}
