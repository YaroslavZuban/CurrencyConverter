package org.example.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Currency;
import org.example.reader.CurrencyRate;
import org.example.repository.CurrencyDatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            String input = scanner.nextLine();

            if (input.equals("1")) {
                String rates = CurrencyRate.getCurrencyReader();

                ObjectMapper objectMapper = new ObjectMapper();
                Currency currencyData = objectMapper.readValue(rates, Currency.class);

                CurrencyDatabase.insertCurrency(currencyData);

                System.out.println("Done");
                System.out.println(CurrencyDatabase.selectCurrencyName());
                System.out.println("______________________________________________");
                System.out.println();
            }

            if (input.equals("2")) {
                System.out.println(CurrencyDatabase.selectCurrencyName());
            }

            if (input.equals("3")) {
                System.exit(1);
            }

        }
    }
}