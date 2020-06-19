package com.mina.countries.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mina.countries.factory.CountryFactory;
import com.mina.countries.model.Country;
import com.mina.countries.service.CountryService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CountriesController.class)
public class CountriesControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CountryService countryService;

    @MockBean
    CountryFactory countryFactory;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getAllCountriesInfo() throws Exception {
        mockMvc.perform(get("/countries/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));

        verify(countryService, times(1)).getAllCountries();
    }

    @Test
    public void getCountryByIdTest() throws Exception {
        Country country = new Country(1, "Germany", "Deutschland", "49", "");
        Mockito.when(countryService.getCountryById(country.getId())).thenReturn(java.util.Optional.of(country));
        mockMvc.perform(get("/countries/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{}"))
                .andExpect(jsonPath("name_en", Matchers.is("Germany")))
                .andExpect(jsonPath("name_de", Matchers.is("Deutschland")));
    }

    @Test
    public void createNewCountryTest() throws Exception {
        Country country = new Country(0, "new Country", "neu land", "ss", "ss");
        Mockito.when(countryService.addCountry(country)).thenReturn(country);
        mockMvc.perform(post("/countries/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(country)))
                .andExpect(status().isOk());
    }

}