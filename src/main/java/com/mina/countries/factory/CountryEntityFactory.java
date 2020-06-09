package com.mina.countries.factory;

import com.mina.countries.model.Country;
import com.mina.countries.model.CountryEntity;
import org.springframework.stereotype.Component;

@Component
public class CountryEntityFactory {

    public CountryEntity newCountryEntity(Country country) {
        CountryEntity entity = null;
        if (country != null)
            entity = new CountryEntity(country.getId(),
                    country.getNameEN(),
                    country.getNameDE(),
                    country.getCountryCode(),
                    country.getLicensePlate());
        return entity;
    }
}
