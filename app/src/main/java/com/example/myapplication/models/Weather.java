package com.example.myapplication.models;

/**
 * Created by junsuk on 2017. 2. 7..
 */

public class Weather {
    private int imageRes;
    private String location;
    private String temperature;

    public Weather(int imageRes, String location, String temperature) {
        this.imageRes = imageRes;
        this.location = location;
        this.temperature = temperature;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Weather{");
        sb.append("imageRes=").append(imageRes);
        sb.append(", location='").append(location).append('\'');
        sb.append(", temperature='").append(temperature).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
