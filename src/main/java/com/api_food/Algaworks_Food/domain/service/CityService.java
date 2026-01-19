package com.api_food.Algaworks_Food.domain.service;

import com.api_food.Algaworks_Food.api.dto.input.CityInput;
import com.api_food.Algaworks_Food.api.dto.output.CityOutput;
import com.api_food.Algaworks_Food.domain.exception.custom.BusinessException;
import com.api_food.Algaworks_Food.domain.exception.custom.CityNotFoundException;
import com.api_food.Algaworks_Food.domain.exception.custom.EntityInUseException;
import com.api_food.Algaworks_Food.domain.mapper.CityMapper;
import com.api_food.Algaworks_Food.domain.model.CityModel;
import com.api_food.Algaworks_Food.domain.model.StateModel;
import com.api_food.Algaworks_Food.domain.repository.CityRepository;
import com.api_food.Algaworks_Food.utils.Formatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityMapper cityMapper;
    private final CityRepository cityRepository;
    private final StateService stateService;


    @Transactional
    public CityOutput addCity(CityInput cityInput) {

        StateModel state = stateService.verifyStateField(cityInput.getState().getId());

        String name = Formatter.string(cityInput.getName());

        CityModel city = CityModel.createCity(name, state);

        cityRepository.saveAndFlush(city);

        return  cityMapper.toCityOutput(city);

    }

    public List<CityOutput> listCities(){
        return cityRepository.findAll().stream().map(cityMapper::toCityOutput).toList();
    }

    public CityOutput findCity(int id){
        CityModel cityFound = this.returnCityModel(id);
        return cityMapper.toCityOutput(cityFound);
    }

    @Transactional
    public void deleteCity(int id){
        CityModel city = this.returnCityModel(id);

        if (cityRepository.isCityInUseByRestaurant(id) || cityRepository.isCityInUseByOrder(id)) {
            throw new EntityInUseException("City", id, "restaurants or orders");
        }

        cityRepository.deleteById(id);
    }

    @Transactional
    public CityOutput updateCity(int id, CityInput cityInput){
        CityModel cityFound = this.returnCityModel(id);
        StateModel state = stateService.verifyStateField(cityInput.getState().getId());

        String name = Formatter.string(cityInput.getName());

        cityFound.setName(name);
        cityFound.setState(state);
        cityRepository.saveAndFlush(cityFound);

        return  cityMapper.toCityOutput(cityFound);
    }

    public CityModel returnCityModel(int id){
        return cityRepository.findById(id).orElseThrow(()-> new CityNotFoundException(id));
    }

    public CityModel verifyCityField(int id){
        return cityRepository.findById(id).orElseThrow(()-> new BusinessException("City", id));
    }
}
