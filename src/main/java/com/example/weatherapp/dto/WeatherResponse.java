package com.example.weatherapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;


import lombok.Data;

@Schema(description = "Weather data response")
@Data
public class WeatherResponse {


    private String city;
    private String country;
    private double temperature;
    private String description;
    private double humidity;
    private double windSpeed;
}