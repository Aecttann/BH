package com.aectann.bh;

import android.location.Location;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Alexander on 23.05.2017.
 */

public class Weather {
    public static String s2;
//    public static String rain;
//    public static String clouds;

    static Location location = null;
    static float lat= (float)Main.lat(location);
    static float longt= (float)Main.longtitude(location);

    public static void main(String[] args){
           }

    public static String getweather(){
        String owmApiKey = "fcc78c588cc672722e31ebbe7202c0d3";
        boolean isMetric = true;
        OpenWeatherMap.Units units = (isMetric)
                ? OpenWeatherMap.Units.METRIC
                : OpenWeatherMap.Units.IMPERIAL;
        OpenWeatherMap owm = new OpenWeatherMap(units, owmApiKey);
        CurrentWeather cwd = null;


        float str = lat + longt;
        try {
            cwd = owm.currentWeatherByCityName("Kharkiv");

            System.out.println(str);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        s2 = " Ваша температура " + cwd.getMainInstance().getMinTemperature() + "°C";
//        System.out.println(cwd.hasRainInstance());
//        if(cwd.hasRainInstance() == true){
//            rain = "Идёт дождь.";
//        }
//        else
//            rain = "Дождя нет.";
//        if(cwd.hasCloudsInstance() == true){
//            clouds = "Облачно.";
//        }
//        else
//            clouds = "Безоблачно.";
        return s2;
    }
}