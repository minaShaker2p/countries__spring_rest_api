drop table  country IF EXISTS;

create TABLE country (
 country_id  BIGINT IDENTITY NOT NULL PRIMARY KEY,
    name_en VARCHAR(50),
    name_de VARCHAR(50),
    country_code VARCHAR(20),
    license_plate VARCHAR(20)

)
