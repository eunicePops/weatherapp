package com.example.weatherapp.controller;

import com.example.weatherapp.dto.WeatherRequest;
import com.example.weatherapp.dto.WeatherResponse;
import com.example.weatherapp.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("removal")
    @MockBean
    private WeatherService weatherService;

    @Test
    void shouldReturn200WithWeatherData() throws Exception {
        WeatherResponse fakeResponse = new WeatherResponse();
        fakeResponse.setCity("Toronto");
        fakeResponse.setCountry("CA");
        fakeResponse.setTemperature(15.0);
        fakeResponse.setDescription("clear sky");

        when(weatherService.getWeather("Toronto")).thenReturn(fakeResponse);

        WeatherRequest request = new WeatherRequest();
        request.setCity("Toronto");

        mockMvc.perform(post("/api/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Toronto"))
                .andExpect(jsonPath("$.country").value("CA"))
                .andExpect(jsonPath("$.temperature").value(15.0))
                .andExpect(jsonPath("$.description").value("clear sky"));
    }

    @Test
    void shouldReturn400WhenCityIsMissing() throws Exception {
        WeatherRequest request = new WeatherRequest();
        request.setCity(null);

        mockMvc.perform(post("/api/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn500WhenServiceThrowsException() throws Exception {
        when(weatherService.getWeather(anyString()))
                .thenThrow(new RuntimeException("City not found: invalidcity"));

        WeatherRequest request = new WeatherRequest();
        request.setCity("invalidcity");

        mockMvc.perform(post("/api/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}
