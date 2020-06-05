package com.mina.countries.service;

import com.mina.countries.model.Country;
import com.mina.countries.model.CountryEntity;
import com.mina.countries.model.CountryFactory;
import com.mina.countries.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CountryFactory countryFactory;


    public List<Country> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        countryRepository.findAll().forEach(countryEntity -> {
            countries.add(countryFactory.toCountry(countryEntity));
        });
        return countries;
    }


    public Optional<Country> getCountryById(int id) {
        Optional<CountryEntity> country = countryRepository.findById(id);
        return country.map(countryEntity -> countryFactory.toCountry(countryEntity));
    }

    public List<Country> search(String nameEn, String nameDe, String countryCode, String licensePlate) {
        List<Country> countries = new ArrayList<>();
        countryRepository.search(nameEn, nameDe, countryCode, licensePlate).forEach(countryEntity -> {
            countries.add(countryFactory.toCountry(countryEntity));
        });
        return countries;
    }

    public Country addCountry(Country countryEntity) {
        return countryFactory.toCountry(
                countryRepository.save(countryFactory.toCountryEntity(countryEntity)));
    }

    public void deleteCountryById(int id) {
        countryRepository.deleteById(id);
    }

    public Optional<Country> updateCountryById(int id, Country countryEntity) {
        Optional<CountryEntity> country = countryRepository.findById(id);

        if (country.isPresent()) {
            Country result = new Country(country.get().getId(),
                    countryEntity.getNameEN(),
                    countryEntity.getNameDE(),
                    countryEntity.getCountryCode(),
                    countryEntity.getLicensePlate());
            countryRepository.save(countryFactory.toCountryEntity(result));
            return Optional.ofNullable(result);
        }
        return Optional.empty();
    }
}
