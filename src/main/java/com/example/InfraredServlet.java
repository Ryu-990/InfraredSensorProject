package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/infrared")
public class InfraredServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sensor = request.getParameter("sensor");

        if ("triggered".equals(sensor)) {
            try (Connection conn = DatabaseConnector.getConnection()) {
                String sql =  "INSERT INTO user_activity ( timestamp,location, temperature, weather) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                	String location="Tokyo";
                	// 天気情報の取得
                    WeatherService.WeatherInfo weatherInfo = WeatherService.getWeather(location);
                    Double Tem=weatherInfo.getTemperature();
                    String weather=weatherInfo.getWeather();
                    stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                    stmt.setString(2, location);
                    stmt.setDouble(3, Tem);
                    stmt.setString(4, weather);
          
                    stmt.executeUpdate();
                    NotificationService.sendEmail("hazako0211@gmail.com",Tem,weather);//宛先
                }

               

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Recorded and notified.");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error: " + e.getMessage());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid sensor data.");
        }
    }
}
/*CREATE TABLE sensor_logs (
id INT AUTO_INCREMENT PRIMARY KEY,
timestamp TIMESTAMP NOT NULL
)
DB作ってアカウント設定することと、STMPサーバーの設定
CREATE TABLE user_activity (
    id INT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(100) NOT NULL,
    timestamp DATETIME NOT NULL,
    temperature DOUBLE,
    weather VARCHAR(100)
);

*/
