package tpAPI.tp5.meteo_api_object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class WeatherData {
    private int  weather, tmin, tmax, probarain;

    public WeatherData(){}



    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public int getTmin() {
        return tmin;
    }

    public void setTmin(int tmin) {
        this.tmin = tmin;
    }

    public int getTmax() {
        return tmax;
    }

    public void setTmax(int tmax) {
        this.tmax = tmax;
    }

    public int getProbarain() {
        return probarain;
    }

    public void setProbarain(int probarain) {
        this.probarain = probarain;
    }
}
