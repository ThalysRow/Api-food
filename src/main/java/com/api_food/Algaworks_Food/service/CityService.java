package com.api_food.Algaworks_Food.service;

import com.api_food.Algaworks_Food.dto.create.CityCreateDTO;
import com.api_food.Algaworks_Food.dto.list.CityListDTO;
import com.api_food.Algaworks_Food.dto.list.StateListDTO;
import com.api_food.Algaworks_Food.dto.update.CityUpdateDTO;
import com.api_food.Algaworks_Food.exception.EntityNotFoundException;
import com.api_food.Algaworks_Food.mapper.CityMapper;
import com.api_food.Algaworks_Food.mapper.StateMapper;
import com.api_food.Algaworks_Food.model.CityModel;
import com.api_food.Algaworks_Food.model.StateModel;
import com.api_food.Algaworks_Food.repository.CityRepository;
import com.api_food.Algaworks_Food.utils.StringFormatter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityMapper cityMapper;
    private final CityRepository cityRepository;
    private final StateService stateService;
    private final StringFormatter stringFormatter;
    private final StateMapper stateMapper;

    public CityService(CityMapper cityMapper, CityRepository cityRepository, StateService stateService, StringFormatter stringFormatter, StateMapper stateMapper) {
        this.cityMapper = cityMapper;
        this.cityRepository = cityRepository;
        this.stateService = stateService;
        this.stringFormatter = stringFormatter;
        this.stateMapper = stateMapper;
    }

    public CityCreateDTO addCity(CityCreateDTO city){

        StateListDTO state = stateService.findStateById(city.getState().getId());
        StateModel saveState = stateMapper.toUpdateModel(state);
        String nameFormated = stringFormatter.stringFormated(city.getName());

        CityModel newCity = cityMapper.toCreateModel(city);
        newCity.setName(nameFormated);
        newCity.setState(saveState);

        CityModel saveCity = cityRepository.save(newCity);

        return cityMapper.toCreateDTO(saveCity);

    }

    public List<CityListDTO> listCities(){
        return cityRepository.findAll().stream().map(cityMapper::toCreateListDTO).toList();
    }

    public CityListDTO findCity(int id){
        CityModel cityFinded = cityRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("City not found"));
        return cityMapper.toCreateListDTO(cityFinded);
    }

    public void deleteCity(int id){
        CityListDTO city = this.findCity(id);
        cityRepository.deleteById(city.getId());
    }

    public CityUpdateDTO updateCity(int id, CityUpdateDTO city){
        CityListDTO cityFinded = this.findCity(id);
        StateListDTO state = stateService.findStateById(city.getState().getId());
        StateModel saveState = stateMapper.toUpdateModel(state);
        String nameFormated = stringFormatter.stringFormated(city.getName());

        CityModel updateCity = cityMapper.toUpdateModel(cityFinded);
        updateCity.setName(nameFormated);
        updateCity.setState(saveState);
        CityModel saveCity = cityRepository.save(updateCity);
        return cityMapper.toUpdateDTO(saveCity);


    }
}
