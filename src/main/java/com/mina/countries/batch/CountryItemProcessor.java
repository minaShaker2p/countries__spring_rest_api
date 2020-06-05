package com.mina.countries.batch;

import com.mina.countries.model.CountryCSV;

import org.springframework.batch.item.ItemProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryItemProcessor implements ItemProcessor<CountryCSV, CountryCSV> {

    private static final Logger log = LoggerFactory.getLogger(CountryItemProcessor.class);


    @Override
    public CountryCSV process(CountryCSV country) throws Exception {
        final String nameEN = country.getNameEn().toUpperCase();
        final String nameDE = country.getNameDe().toUpperCase();
        final String licensePlate = country.getLicensePlate().toUpperCase();

//        Country transformedCountry = new Country(country.getId(),
//                nameEN, nameDE, country.getCountryCode(), country.getLicensePlate());
     //   log.info("Converting (" + country + ") into (" + transformedCountry + ")");
        return new CountryCSV();
    }
}
