package com.example.weatherapp.service;

import com.example.weatherapp.dto.OpenWeatherResponse;
import com.example.weatherapp.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(String city) {
        try {
            String url = apiUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";

            OpenWeatherResponse raw = restTemplate.getForObject(url, OpenWeatherResponse.class);

            WeatherResponse response = new WeatherResponse();
            response.setCity(raw.getName());
            response.setCountry(raw.getSys().getCountry());
            response.setTemperature(raw.getMain().getTemp());
            response.setHumidity(raw.getMain().getHumidity());
            response.setWindSpeed(raw.getWind().getSpeed());
            response.setDescription(raw.getWeather().get(0).getDescription());

            return response;

        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("City not found: " + city);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new RuntimeException("Invalid API key");
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage());
        }
    }
}

