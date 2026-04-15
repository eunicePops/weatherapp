package com.example.weatherapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WeatherRequest {

    @NotBlank(message = "City name cannot be blank")
    private String city;
}