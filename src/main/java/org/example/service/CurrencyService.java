package org.example.service;

import org.example.repository.CurrencyDatabase;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyService {
    private static final String regex = "^(\\d+(?:\\.\\d{1,2})?) ([A-Z]{3})$";

    private String amount;
    private String currencyCode;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void run() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Exit application enter 0.");

        while (true) {
            System.out.print("Enter the amount you want to convert: ");

            String currency = scanner.nextLine().toUpperCase();

            if (currency.equals("0")) {
                System.exit(0);
            }

            if (!isValidCurrency(currency)) {
                System.out.println("Invalid currency.");
                continue;
            }

            if (!isExtractingCurrency(currency)) {
                System.out.println("Input does not match the expected format.");
                continue;
            }

            System.out.println("Translation result: " + convertToRubles());
        }
    }

    public String convertToRubles() throws SQLException {
        if (!checkCurrency()) {
            throw new RuntimeException("Invalid currency.");
        }

        double rateCurrencyCode = CurrencyDatabase.getPriceCurrency(currencyCode);
        double result = Double.parseDouble(amount) / rateCurrencyCode;

        return result + " рублей";
    }

    public boolean checkCurrency() throws SQLException {
        List<String> currencyCodeList = CurrencyDatabase.selectCurrencyName();

        return currencyCodeList.contains(currencyCode);
    }

    public boolean isExtractingCurrency(String currency) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(currency);

        if (!matcher.matches()) {
            return false;
        }

        this.amount = matcher.group(1);
        this.currencyCode = matcher.group(2);

        return true;
    }

    public boolean isValidCurrency(String currency) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(currency);

        return matcher.matches();
    }
}
