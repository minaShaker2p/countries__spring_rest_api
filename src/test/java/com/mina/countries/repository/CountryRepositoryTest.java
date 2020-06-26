package com.mina.countries.repository;

import com.mina.countries.model.CountryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CountryRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CountryRepository countryRepository;
    private CountryEntity country;

    @BeforeEach
    void setup() {
        country = new CountryEntity("testEN", "testDE", "testCC", "");
    }

    @Test
    void injectionComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(countryRepository).isNotNull();
    }

    @Test
    void testAddNewCountry() {
        //Given
        countryRepository.save(country);
        //Action
        Iterable<CountryEntity> countryTest = countryRepository.findAll();
        //Verify
        assertThat(countryTest).isNotNull();
    }

    @Test
    void testFindCountryByEnglishName() {
        // Given
        countryRepository.save(country);

        //Action
        CountryEntity countryTest = countryRepository.findByNameEN(country.getNameEN());

        //Verify
        assertAll(() -> assertEquals(countryTest.getNameEN(), country.getNameEN()),
                () -> assertEquals(countryTest.getNameDE(), country.getNameDE()),
                () -> assertEquals(countryTest.getLicensePlate(), country.getLicensePlate()),
                () -> assertEquals(countryTest.getCountryCode(), country.getCountryCode()));
    }

    @Test
    void testDeleteCountry() {
        //Given
        CountryEntity countryTest = countryRepository.save(country);

        // Action
        countryRepository.deleteById(countryTest.getId());

        //Verify
        assertThat(countryRepository.findByNameEN(countryTest.getNameEN())).isNull();
    }

    @Test
    void testUpdateCountry() {
        //Given
        CountryEntity countryTest = countryRepository.save(country);

        // Action
        countryTest.setLicensePlate("A7A");
        CountryEntity updatedCountry = countryRepository.save(countryTest);

        //Verify
        assertAll(() -> assertEquals(updatedCountry.getNameEN(), countryTest.getNameEN()),
                () -> assertEquals(updatedCountry.getNameDE(), countryTest.getNameDE()),
                () -> assertEquals(updatedCountry.getLicensePlate(), "A7A"),
                () -> assertEquals(updatedCountry.getCountryCode(), countryTest.getCountryCode()));
    }

    @Test
    @DisplayName("Test search by nameEn or nameDE return results successfully")
    void testSearchByNameReturnResultSuccessfully() {
        //Given
        CountryEntity country1 = new CountryEntity("testEN1", "testDE1", "testCC1", "");
        CountryEntity country2 = new CountryEntity("testEN2", "testDE2", "testCC2", "");
        countryRepository.save(country1);
        countryRepository.save(country2);

        //Action
        List<CountryEntity> actualResult = countryRepository.search(country1.getNameEN(), country1.getNameDE(), null, null);

        //Verify
        assertEquals(actualResult.size(), 1);
        assertAll(() -> assertEquals(country1.getNameEN(), actualResult.get(0).getNameEN()),
                () -> assertEquals(country1.getNameDE(), actualResult.get(0).getNameDE()),
                () -> assertEquals(country1.getLicensePlate(), actualResult.get(0).getLicensePlate()),
                () -> assertEquals(country1.getCountryCode(), actualResult.get(0).getCountryCode()));
    }

    @Test
    @DisplayName("Test search by nameEn or nameDE  does not match return empty result")
    void testSearchByNameDoesNotMatchReturnEmptyResults() {
        //Given
        CountryEntity country1 = new CountryEntity("testEN1", "testDE1", "testCC1", "");
        CountryEntity country2 = new CountryEntity("testEN2", "testDE2", "testCC2", "");
        countryRepository.save(country1);
        countryRepository.save(country2);

        //Action
        List<CountryEntity> actualResult = countryRepository.search("Germany", "Deutschland", null, null);

        //Verify
        assertEquals(actualResult.size(), 0);
    }

}