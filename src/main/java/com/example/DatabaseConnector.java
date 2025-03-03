package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            // 設定ファイルからデータベース情報を取得
            String url = ConfigLoader.getDbProperty("db.url");
            String username = ConfigLoader.getDbProperty("db.username");
            String password = ConfigLoader.getDbProperty("db.password");
            String driver = ConfigLoader.getDbProperty("db.driver");

            // JDBCドライバのロード
            Class.forName(driver);

            // データベース接続の確立
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
}
