package com.example.weatherapp;

import com.example.weatherapp.dto.OpenWeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherAppIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("removal")
    @MockBean
    private RestTemplate restTemplate;

    private OpenWeatherResponse buildFakeResponse() {
        OpenWeatherResponse fake = new OpenWeatherResponse();

        OpenWeatherResponse.Main main = new OpenWeatherResponse.Main();
        main.setTemp(15.0);
        main.setHumidity(80.0);

        OpenWeatherResponse.Wind wind = new OpenWeatherResponse.Wind();
        wind.setSpeed(3.5);

        OpenWeatherResponse.Sys sys = new OpenWeatherResponse.Sys();
        sys.setCountry("CA");
        OpenWeatherResponse.Weather weather = new OpenWeatherResponse.Weather();
        weather.setDescription("clear sky");

        fake.setName("Toronto");
        fake.setMain(main);
        fake.setWind(wind);
        fake.setSys(sys);
        fake.setWeather(List.of(weather));

        return fake;
    }
    @Test
    void fullStack_shouldReturnWeatherForValidCity() throws Exception {
        // mock the external API call
        when(restTemplate.getForObject(anyString(), any()))
                .thenReturn(buildFakeResponse());

        // build request body
        String requestBody = """
                {
                    "city": "Toronto"
                }
                """;

        // send request and assert response
        mockMvc.perform(post("/api/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Toronto"))
                .andExpect(jsonPath("$.country").value("CA"))
                .andExpect(jsonPath("$.temperature").value(15.0))
                .andExpect(jsonPath("$.description").value("clear sky"));
    }
    @Test
    void fullStack_shouldReturn400WhenCityIsMissing() throws Exception {
        String requestBody = """
                {
                    "city": ""
                }
                """;

        mockMvc.perform(post("/api/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
    @Test
    void fullStack_shouldReturn500WhenCityNotFound() throws Exception {
        when(restTemplate.getForObject(anyString(), any()))
                .thenThrow(HttpClientErrorException.NotFound.class);

        String requestBody = """
                {
                    "city": "invalidcity"
                }
                """;

        mockMvc.perform(post("/api/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("City not found: invalidcity"));
    }
}
