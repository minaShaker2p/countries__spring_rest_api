package com.mina.countries.service;

import com.mina.countries.factory.CountryEntityFactory;
import com.mina.countries.factory.CountryFactory;
import com.mina.countries.model.Country;
import com.mina.countries.model.CountryEntity;
import com.mina.countries.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {

    @Autowired
    private final CountryRepository countryRepository;

    @Autowired
    private final CountryFactory countryFactory;

    @Autowired
    private final CountryEntityFactory countryEntityFactory;


    public List<Country> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        countryRepository.findAll().forEach(countryEntity -> {
            countries.add(countryFactory.newCountry(countryEntity));
        });
        return countries;
    }


    public Optional<Country> getCountryById(int id) {
        Optional<CountryEntity> country = countryRepository.findById(id);
        return country.map(countryEntity -> countryFactory.newCountry(countryEntity));
    }

    public List<Country> search(String nameEn, String nameDe, String countryCode, String licensePlate) {
        List<Country> countries = new ArrayList<>();
        countryRepository.search(nameEn, nameDe, countryCode, licensePlate).forEach(countryEntity -> {
            countries.add(countryFactory.newCountry(countryEntity));
        });
        return countries;
    }

    public Country addCountry(Country countryEntity) {
        return countryFactory.newCountry(
                countryRepository.save(countryEntityFactory.newCountryEntity(countryEntity)));
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
            countryRepository.save(countryEntityFactory.newCountryEntity(result));
            return Optional.ofNullable(result);
        }
        return Optional.empty();
    }
}
