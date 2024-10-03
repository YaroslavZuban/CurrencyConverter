package org.example.reader;

import org.example.config.Config;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CurrencyRate {
    public static final String GET_API = "https://www.cbr-xml-daily.ru/latest.js";

    public static String getCurrencyReader() throws IOException {
        URL url = new URL(GET_API);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode != HttpsURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }

        br.close();
        return response.toString();
    }
}