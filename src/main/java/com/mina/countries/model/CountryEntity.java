package com.mina.countries.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "country")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private int id;

    @Column(name = "name_en", nullable = false)
    private String nameEN;

    @Column(name = "name_de", nullable = false)
    private String nameDE;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "license_plate")
    private String licensePlate;

    public CountryEntity(String nameEN, String nameDE, String countryCode, String licensePlate) {
        this.nameEN = nameEN;
        this.nameDE = nameDE;
        this.countryCode = countryCode;
        this.licensePlate = licensePlate;
    }
}
