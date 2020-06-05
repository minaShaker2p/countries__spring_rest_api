package com.mina.countries.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"altNameDe",
        "isoNumeric",
        "isoAlpha2",
        "isoAlpha3",
        "fibS10",
        "stang",
        "domain",
        "unlocode",
        "ioc",
        "longitude",
        "latitude",
        "active"
})
public class CountryCSV {

    private String id;
    private String nameEn;
    private String nameDe;
    private String altNameDe;
    private String isoNumeric;
    private String isoAlpha2;
    private String isoAlpha3;
    private String fibS10;
    private String stang;
    private String domain;
    private String countryCode;
    private String ioc;
    private String licensePlate;
    private String unlocode;
    private String longitude;
    private String latitude;
    private String active;

}
