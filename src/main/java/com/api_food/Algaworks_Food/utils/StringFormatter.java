package com.api_food.Algaworks_Food.utils;

import com.api_food.Algaworks_Food.dto.create.AddressCreateDTO;
import com.api_food.Algaworks_Food.dto.create.RestaurantCreateDTO;
import com.api_food.Algaworks_Food.dto.update.RestaurantUpdateDTO;
import com.api_food.Algaworks_Food.model.AddressModel;
import org.springframework.stereotype.Component;

@Component
public class StringFormatter {

    public String stringFormated(String string) {

        if(string == null || string.trim().isEmpty()){
            return "";
        }

        String format1 = string.trim();
        String[] format2 = format1.split(" ");

        for (int i = 0; i < format2.length; i++) {
            String text = format2[i];
            String first = text.substring(0, 1).toUpperCase();
            String rest = text.substring(1).toLowerCase();
            format2[i] = first + rest;
        }

        return String.join(" ", format2);
    }

    public RestaurantCreateDTO restaurantFieldsFormatter(RestaurantCreateDTO data){

        data.setName(stringFormated(data.getName()));
        data.getAddress().setZipcode(stringFormated(data.getAddress().getZipcode()));
        data.getAddress().setStreet(stringFormated(data.getAddress().getStreet()));
        data.getAddress().setNumber(stringFormated(data.getAddress().getNumber()));
        data.getAddress().setComplement(stringFormated(data.getAddress().getComplement()));
        data.getAddress().setNeighborhood(stringFormated(data.getAddress().getNeighborhood()));

        return data;
    }

    public RestaurantUpdateDTO restaurantFieldsFormatter(RestaurantUpdateDTO data){
        data.setName(stringFormated(data.getName()));
        data.getAddress().setZipcode(stringFormated(data.getAddress().getZipcode()));
        data.getAddress().setStreet(stringFormated(data.getAddress().getStreet()));
        data.getAddress().setNumber(stringFormated(data.getAddress().getNumber()));
        data.getAddress().setComplement(stringFormated(data.getAddress().getComplement()));
        data.getAddress().setNeighborhood(stringFormated(data.getAddress().getNeighborhood()));

        return data;
    }

    public AddressModel addressFieldsFormatter(AddressModel data){
        data.setZipcode(stringFormated(data.getZipcode()));
        data.setStreet(stringFormated(data.getStreet()));
        data.setNumber(stringFormated(data.getNumber()));
        data.setComplement(stringFormated(data.getComplement()));
        data.setNeighborhood(stringFormated(data.getNeighborhood()));

        return data;
    }
}