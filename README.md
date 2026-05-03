# WeatherApp 🌤️

![CI](https://github.com/eunicepops/weatherapp/actions/workflows/ci.yml/badge.svg)

A REST API built with Spring Boot that returns real-time weather data for any city using the OpenWeatherMap API.

## Tech Stack

- Java 21
- Spring Boot 3.5.1
- Maven
- OpenWeatherMap API
- SpringDoc OpenAPI (Swagger UI)

## Features

- Get current weather by city name
- Input validation with meaningful error messages
- Global exception handling
- Auto-generated API documentation via Swagger UI
- CI pipeline with GitHub Actions

## Getting Started

### Prerequisites
- Java 21
- Maven

### Run Locally

1. Clone the repo
```bash
   git clone https://github.com/eunicepops/weatherapp.git
   cd weatherapp
```

2. Set your API key
```bash
   export WEATHER_API_KEY=your_openweathermap_api_key
```

3. Start the app
```bash
   mvn spring-boot:run
```

4. Open Swagger UI

http://localhost:8080/swagger-ui.html

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/weather` | Get weather by city name |

### Example Request
```json
{
  "city": "Toronto"
}
```

### Example Response
```json
{
  "city": "Toronto",
  "country": "CA",
  "temperature": 15.0,
  "description": "clear sky",
  "humidity": 80.0,
  "windSpeed": 3.5
}
```

## Running Tests

```bash
mvn test
```

## Live Demo

API is live on Render:

- Swagger UI: https://weatherapp-1jpu.onrender.com/swagger-ui.html
- Base URL: https://weatherapp-1jpu.onrender.com