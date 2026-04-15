package com.example.weatherapp.controller;


import com.example.weatherapp.dto.WeatherRequest;
import com.example.weatherapp.dto.WeatherResponse;
import com.example.weatherapp.service.WeatherService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping
    public ResponseEntity<WeatherResponse> getWeather(@Valid @RequestBody WeatherRequest request) {
        WeatherResponse response = weatherService.getWeather(request.getCity());
        return ResponseEntity.ok(response);
    }
}
