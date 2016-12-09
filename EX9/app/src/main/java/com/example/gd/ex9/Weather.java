package com.example.gd.ex9;

/**
 * Created by gd on 16/11/28.
 */
public class Weather {

    private String weather;
    private String date;
    private String temperature;

    public Weather(String weather, String date, String temperature) {
        this.weather = weather;
        this.date = date;
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
