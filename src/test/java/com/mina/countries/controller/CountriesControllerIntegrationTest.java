package com.mina.countries.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CountriesControllerIntegrationTest {

    @LocalManagementPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void getAllCountries() throws Exception {
        ResponseEntity<List> response=testRestTemplate.getForEntity("http://localhost:"+port+"/countries/", List.class);
        assertThat(response.getStatusCode(),equalTo(HttpStatus.OK));

    }
}
