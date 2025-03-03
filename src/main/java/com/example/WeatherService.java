package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class WeatherService {
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "9d977ee8d8c4bbcae4bf262fbc0783f7"; // ここに取得したAPIキーを設定

    public static WeatherInfo getWeather(String location) throws Exception {
        String urlString = API_URL + "?q=" + location + "&appid=" + API_KEY + "&units=metric&lang=ja";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // レスポンスを取得
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // JSONレスポンスを解析
        JSONObject jsonResponse = new JSONObject(response.toString());
        double temp = jsonResponse.getJSONObject("main").getDouble("temp");
        String weather = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");

        return new WeatherInfo(temp, weather);
    }

    public static class WeatherInfo {
        private final double temperature;
        private final String weather;

        public WeatherInfo(double temperature, String weather) {
            this.temperature = temperature;
            this.weather = weather;
        }

        public double getTemperature() {
            return temperature;
        }

        public String getWeather() {
            return weather;
        }
    }
}
