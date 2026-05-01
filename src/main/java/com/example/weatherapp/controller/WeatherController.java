package com.example.weatherapp.controller;


import com.example.weatherapp.dto.WeatherRequest;
import com.example.weatherapp.dto.WeatherResponse;
import com.example.weatherapp.service.WeatherService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "Weather", description = "Endpoints for fetching weather data")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping
    @Operation(
            summary = "Get weather by city",
            description = "Returns current weather data for the specified city"
    )
    public ResponseEntity<WeatherResponse> getWeather(@Valid @RequestBody WeatherRequest request) {
        WeatherResponse response = weatherService.getWeather(request.getCity());
        return ResponseEntity.ok(response);
    }
}
