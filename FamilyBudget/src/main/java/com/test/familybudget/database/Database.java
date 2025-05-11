package com.test.familybudget.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:familybudget.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(DB_URL);
                createTables();
            } catch (ClassNotFoundException e) {
                throw new SQLException("SQLite JDBC driver not found", e);
            }
        }
        return connection;
    }

    private static void createTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Создание таблицы категорий
            statement.execute("CREATE TABLE IF NOT EXISTS categories (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "type TEXT NOT NULL)"); // доход или расход

            // Создание таблицы транзакций
            statement.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "amount REAL NOT NULL," +
                    "category_id INTEGER," +
                    "date TEXT NOT NULL," +
                    "description TEXT," +
                    "FOREIGN KEY (category_id) REFERENCES categories(id))");
        }
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }
} 