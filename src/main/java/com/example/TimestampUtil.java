package com.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampUtil {
    public static String getFormattedTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH時mm分");
        return now.format(formatter);
    }
}
