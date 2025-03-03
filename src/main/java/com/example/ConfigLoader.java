package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties mailProperties = new Properties();
    private static final Properties dbProperties = new Properties();

    static {
        // メール設定の読み込み
        try (InputStream mailInput = ConfigLoader.class.getClassLoader().getResourceAsStream("config/mail.properties")) {
            if (mailInput != null) {
                mailProperties.load(mailInput);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load mail configuration: " + e.getMessage());
        }

        // データベース設定の読み込み
        try (InputStream dbInput = ConfigLoader.class.getClassLoader().getResourceAsStream("config/db.properties")) {
            if (dbInput != null) {
                dbProperties.load(dbInput);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load database configuration: " + e.getMessage());
        }
    }

    public static String getMailProperty(String key) {
        return mailProperties.getProperty(key);
    }

    public static String getDbProperty(String key) {
        return dbProperties.getProperty(key);
    }
}
