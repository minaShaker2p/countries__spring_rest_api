package com.mina.countries.controller;

import com.mina.countries.factory.CountryFactory;
import com.mina.countries.model.Country;
import com.mina.countries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
class CountriesController {

    @Autowired
    CountryService countryService;

    @Autowired
    CountryFactory countryFactory;

    @GetMapping("/countries")
    List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/countries/{id}")
    Optional<Country> getCountryById(@PathVariable("id") int id) {
        if (countryService.getCountryById(id).isPresent())
            return countryService.getCountryById(id);
        else
            throw new IllegalArgumentException("User Not Found");
    }

    @GetMapping("/countries/search")
    @ResponseBody
    List<Country> search(@RequestParam(name = "name_en", required = false) String nameEn,
                         @RequestParam(name = "name_de", required = false) String nameDe,
                         @RequestParam(name = "country_code", required = false) String countryCode,
                         @RequestParam(name = "license_plate", required = false) String licensePlate) {
        return countryService.search(nameEn, nameDe, countryCode, licensePlate);
    }

    @PostMapping("/countries")
    Country addCountry(@RequestBody Country country) {
        return countryService.addCountry(country);
    }

    @DeleteMapping("/countries/{id}")
    void deleteCountryById(@PathVariable(name = "id") int id) {
        if (countryService.getCountryById(id).isPresent())
            countryService.deleteCountryById(id);
        else
            throw new IllegalArgumentException("User Not Found");
    }


    @PutMapping("/countries/{id}")
    Country updateCountryById(@PathVariable(name = "id") int id,
                              @RequestBody Country country) {
        if (countryService.getCountryById(id).isPresent())
            return countryService.updateCountryById(id, country).get();
        else
            throw new IllegalArgumentException("User Not Found");

    }


}
