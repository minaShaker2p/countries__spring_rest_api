package com.mina.countries.repository;

import com.mina.countries.model.CountryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<CountryEntity, Integer> {


    @Query(value = "SELECT * FROM country u WHERE u.name_en LIKE :name_en" +
            " OR u.name_de LIKE :name_de " +
            "OR u.country_code LIKE :country_code " +
            "OR u.license_plate LIKE :license_plate ",
            nativeQuery = true)
    List<CountryEntity> search(@Param("name_en") String nameEN, @Param("name_de") String nameDE, @Param("country_code") String countryCode, @Param("license_plate") String licensePlate);

    CountryEntity findByNameEN(String nameEN);
}
