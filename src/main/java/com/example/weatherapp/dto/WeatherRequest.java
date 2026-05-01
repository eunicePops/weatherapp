package com.example.weatherapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Request body for weather lookup")
public class WeatherRequest {

    @Schema(description = "Name of the city", example = "Toronto")
    @NotBlank(message = "City name cannot be blank")
    private String city;
}