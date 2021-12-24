package tpAPI.tp5.meteo_api_object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class WeatherQuery {
    private WeatherData [] forecast;

    public WeatherQuery() {
    }

    public WeatherData[] getForecast() {
        return forecast;
    }

    public void setForecast(WeatherData[] forecast) {
        this.forecast = forecast;
    }
}
