package org.example.repository;

import org.example.config.Config;
import org.example.model.Currency;

import java.sql.*;
import java.util.*;

public class CurrencyDatabase {
    private static final String URL = Config.getProperty("db.url");
    private static final String USERNAME = Config.getProperty("db.user");
    private static final String PASSWORD = Config.getProperty("db.password");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void insertCurrency(Currency currency) throws SQLException {
        String query = "INSERT INTO public.exchange_rates(currency_code, rate) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Map<String, Double> rates = currency.getRates();

            for (String key : rates.keySet()) {
                double rate = rates.get(key);

                preparedStatement.setString(1, key);
                preparedStatement.setDouble(2, rate);

                preparedStatement.executeUpdate();
            }
        }
    }

    public static List<String> selectCurrencyName() throws SQLException {
        String query = "SELECT distinct currency_code FROM public.exchange_rates ORDER BY currency_code ASC";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<String> names = new ArrayList<>();

            while (resultSet.next()) {
                names.add(resultSet.getString("currency_code"));
            }

            return names;
        }
    }


    public static double getPriceCurrency(String currencyCode) throws SQLException {
        String query = "SELECT er.rate " +
                "FROM public.exchange_rates er " +
                "WHERE true" +
                "  and  er.currency_code = ?" +
                "  and er.created_at = (SELECT MAX(er2.created_at)" +
                "                       FROM public.exchange_rates er2 " +
                "                       WHERE er2.currency_code = ?" +
                "                       ) " +
                "limit 1";


        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1,currencyCode);
            preparedStatement.setString(2, currencyCode);

            ResultSet resultSet = preparedStatement.executeQuery();
            double rate = 0;

            while (resultSet.next()) {
                 rate = resultSet.getDouble("rate");
            }

            return rate;
        }
    }
}