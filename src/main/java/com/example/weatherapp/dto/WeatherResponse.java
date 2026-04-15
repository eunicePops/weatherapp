package com.example.weatherapp.dto;

import lombok.Data;

@Data
public class WeatherResponse {
    private String city;
    private String country;
    private double temperature;
    private String description;
    private double humidity;
    private double windSpeed;
}