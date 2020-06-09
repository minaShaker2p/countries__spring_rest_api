package com.mina.countries.factory;

import com.mina.countries.model.Country;
import com.mina.countries.model.CountryEntity;
import org.springframework.stereotype.Component;

@Component
public class CountryFactory {

    public Country newCountry(CountryEntity entity) {

        Country country = null;

        if (entity != null)
            country = new Country(entity.getId(),
                    entity.getNameEN(),
                    entity.getNameDE(),
                    entity.getCountryCode(),
                    entity.getLicensePlate());
        return country;
    }
}
