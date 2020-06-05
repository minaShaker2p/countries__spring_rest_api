package com.mina.countries.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name_en")
    private String nameEN;
    @JsonProperty("name_de")
    private String nameDE;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("license_plate")
    private String licensePlate;
}
