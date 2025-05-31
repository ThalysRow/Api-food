package com.api_food.Algaworks_Food.utils;

import org.springframework.stereotype.Component;

@Component
public class StringFormatter {

    public String stringFormated(String string) {
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
}