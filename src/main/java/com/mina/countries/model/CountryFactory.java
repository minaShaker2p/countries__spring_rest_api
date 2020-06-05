package com.mina.countries.model;

import org.springframework.stereotype.Component;

@Component
public class CountryFactory {

    public CountryEntity toCountryEntity(Country country) {
        CountryEntity entity = new CountryEntity(country.getId(),
                country.getNameEN(),
                country.getNameDE(),
                country.getCountryCode(),
                country.getLicensePlate());
        return entity;
    }

    public Country toCountry(CountryEntity entity) {
        Country country = new Country(entity.getId(),
                entity.getNameEN(),
                entity.getNameDE(),
                entity.getCountryCode(),
                entity.getLicensePlate());
        return country;
    }
}
