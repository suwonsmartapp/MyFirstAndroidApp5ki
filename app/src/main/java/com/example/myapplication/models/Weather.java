package com.example.myapplication.models;

/**
 * Created by junsuk on 2017. 2. 7..
 */

public class Weather {
    private int imageRes;
    private String weather;
    private String country;
    private String temperature;

    public Weather(int imageRes, String country, String temperature) {
        this.imageRes = imageRes;
        this.country = country;
        this.temperature = temperature;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Weather{");
        sb.append("country='").append(country).append('\'');
        sb.append(", imageRes=").append(imageRes);
        sb.append(", weather='").append(weather).append('\'');
        sb.append(", temperature='").append(temperature).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
