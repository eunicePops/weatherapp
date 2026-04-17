package com.example.weatherapp.service;

import com.example.weatherapp.dto.OpenWeatherResponse;
import com.example.weatherapp.dto.WeatherResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService;

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
    void shouldReturnWeatherForValidCity() {
        when(restTemplate.getForObject(anyString(), any()))
                .thenReturn(buildFakeResponse());

        WeatherResponse response = weatherService.getWeather("Toronto");

        assertEquals("Toronto", response.getCity());
        assertEquals("CA", response.getCountry());
        assertEquals(15.0, response.getTemperature());
        assertEquals("clear sky", response.getDescription());
    }

    @Test
    void shouldThrowExceptionForInvalidCity() {
        when(restTemplate.getForObject(anyString(), any()))
                .thenThrow(new RuntimeException("City not found: invalidcity"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                weatherService.getWeather("invalidcity")
        );

        assertTrue(exception.getMessage().contains("invalidcity"));
    }
}