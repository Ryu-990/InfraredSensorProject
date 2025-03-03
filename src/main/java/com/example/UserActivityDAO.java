package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserActivityDAO {

    public static void saveActivity(String location, String timestamp) {
        String query = "INSERT INTO user_activity (location, timestamp, temperature, weather) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // 天気情報の取得
            WeatherService.WeatherInfo weatherInfo = WeatherService.getWeather(location);

            statement.setString(1, location);
            statement.setString(2, timestamp);
            statement.setDouble(3, weatherInfo.getTemperature());
            statement.setString(4, weatherInfo.getWeather());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
