package com.example.weatherapp.dto;
import lombok.Data;
import java.util.List;

@Data
public class OpenWeatherResponse {
    private String name;

    private Main main;
    private Wind wind;
    private Sys sys;
    private List<Weather> weather;

    @Data
    public static class Main {
        private double temp;
        private double humidity;
    }

    @Data
    public static class Wind {
        private double speed;
    }
    @Data
    public static class Sys {
        private String country;
    }

    @Data
    public static class Weather {
        private String description;
    }
}
