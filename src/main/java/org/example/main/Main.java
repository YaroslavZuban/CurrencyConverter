package org.example.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Currency;
import org.example.reader.CurrencyRate;
import org.example.repository.CurrencyDatabase;
import org.example.service.CurrencyService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        try {
            new CurrencyService().run();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao executar os dados.", e);
        }
    }
}