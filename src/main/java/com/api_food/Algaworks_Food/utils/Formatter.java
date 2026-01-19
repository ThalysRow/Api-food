package com.api_food.Algaworks_Food.utils;

import com.api_food.Algaworks_Food.domain.model.AddressModel;
import org.springframework.stereotype.Component;

@Component
public class Formatter {

    public static String string(String string) {

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

    public AddressModel addressFieldsFormatter(AddressModel data){
        data.setZipcode(string(data.getZipcode()));
        data.setStreet(string(data.getStreet()));
        data.setNumber(string(data.getNumber()));
        data.setComplement(string(data.getComplement()));
        data.setNeighborhood(string(data.getNeighborhood()));

        return data;
    }
}