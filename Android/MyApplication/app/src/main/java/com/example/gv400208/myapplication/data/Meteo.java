package com.example.gv400208.myapplication.data;

/**
 * Created by am404527 on 17/03/2017.
 */

public class Meteo {

    private Coord coord;
    private Main main;
    private Wind wind;
    private String name;
    private Weather[] weather;
    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Meteo(){

    }
}
