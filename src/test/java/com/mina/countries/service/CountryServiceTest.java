package com.mina.countries.service;

import com.mina.countries.factory.CountryEntityFactory;
import com.mina.countries.factory.CountryFactory;
import com.mina.countries.model.Country;
import com.mina.countries.model.CountryEntity;
import com.mina.countries.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {
    @InjectMocks
    private CountryService countryService;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private CountryEntityFactory countryEntityFactory;
    @Mock
    private CountryFactory countryFactory;

    private CountryEntity countryEntity;
    private Country country;

    @BeforeEach
    void setup() {
        countryEntity = new CountryEntity(1, "Germany", "Deutschland", "+49", "");
        country = new Country(1, "Germany", "Deutschland", "+49", "");
        Mockito.lenient().when(countryFactory.newCountry(countryEntity)).thenReturn(country);
    }


    @Test
    @DisplayName("test get all countries return results successfully")
    void getAllCountries() {
        //Given
        List<CountryEntity> countries = Arrays.asList(countryEntity);
        Mockito.when(countryRepository.findAll()).thenReturn(countries);

        //Action
        final List<Country> expected = countryService.getAllCountries();

        //VERIFY
        verify(countryRepository, times(1)).findAll();
        assertThat(expected).isNotNull();
        assertEquals(expected.size(), countries.size());
        assertAll(() -> assertEquals(expected.get(0).getNameEN(), "Germany"),
                () -> assertEquals(expected.get(0).getNameDE(), "Deutschland"),
                () -> assertEquals(expected.get(0).getCountryCode(), "+49"));

    }

    @Test
    @DisplayName("test get  country by id  return results successfully")
    void gerCountryByIdTest() {
        //Given
        Mockito.when(countryRepository.findById(1)).thenReturn(Optional.of(countryEntity));

        // Action
        Optional<Country> expected = countryService.getCountryById(1);

        //VERIFY
        assertThat(expected).isNotNull();
        verify(countryRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("test add Country return expected country successfully")
    public void addCountryTest() {
        //Given
        Mockito.when(countryEntityFactory.newCountryEntity(country)).thenReturn(countryEntity);
        Mockito.when(countryRepository.save(countryEntity)).thenReturn(countryEntity);

        // Action
        final Country expected = countryService.addCountry(country);

        //VERIFY
        verify(countryRepository, times(1)).save(countryEntity);
        assertThat(expected).isNotNull();
        assertEquals(expected, country);
        assertAll(() -> assertEquals(expected.getNameEN(), "Germany"),
                () -> assertEquals(expected.getNameDE(), "Deutschland"),
                () -> assertEquals(expected.getCountryCode(), "+49"));
    }

    @Test
    @DisplayName("test update Country bu id return expected updated country successfully")
    public void updateCountryByIdTest() {
        //GIVEN
        CountryEntity updatedCountryEntity = new CountryEntity(1, "Egypt", "Agypten", "+20", "");
        Country updatedCountry = new Country(1, "Egypt", "Agypten", "+20", "");
        Mockito.when(countryRepository.findById(1)).thenReturn(Optional.of(updatedCountryEntity));
        Mockito.when(countryEntityFactory.newCountryEntity(any())).thenReturn(updatedCountryEntity);
        Mockito.when(countryRepository.save(updatedCountryEntity)).thenReturn(updatedCountryEntity);

        //ACTION
        Optional<Country> expected = countryService.updateCountryById(1, updatedCountry);

        //VERIFY
        verify(countryRepository, times(1)).findById(1);
        verify(countryRepository, times(1)).save(updatedCountryEntity);
        assertAll(() -> assertEquals(expected.get().getNameEN(), "Egypt"),
                () -> assertEquals(expected.get().getNameDE(), "Agypten"),
                () -> assertEquals(expected.get().getCountryCode(), "+20"));
    }

    @Test
    public void searchTest() {
        //GIVEN
        List<CountryEntity> actual = Collections.singletonList(countryEntity);

        Mockito.when(countryRepository.search(countryEntity.getNameEN(), countryEntity.getNameDE(), countryEntity.getCountryCode(), countryEntity.getLicensePlate()))
                .thenReturn(actual);

        //ACTION
        List<Country> expected = countryService.search(country.getNameEN(), country.getNameDE(), country.getCountryCode(), country.getLicensePlate());

        //VERIFY
        verify(countryRepository, times(1))
                .search(countryEntity.getNameEN(), countryEntity.getNameDE(), countryEntity.getCountryCode(), countryEntity.getLicensePlate());
        assertEquals(expected.size(), actual.size());
        assertAll(() -> assertEquals(expected.get(0).getNameEN(), actual.get(0).getNameEN()),
                () -> assertEquals(expected.get(0).getNameDE(), actual.get(0).getNameDE()),
                () -> assertEquals(expected.get(0).getCountryCode(), actual.get(0).getCountryCode()));


    }

}