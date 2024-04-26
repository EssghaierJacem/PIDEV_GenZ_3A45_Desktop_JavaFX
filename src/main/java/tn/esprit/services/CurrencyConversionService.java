package tn.esprit.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyConversionService {

    private static final String API_KEY = "744222dec9afed98010a572f";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    private static final Logger LOGGER = Logger.getLogger(CurrencyConversionService.class.getName());

    public float convertCurrency(float amount, String baseCurrency, String targetCurrency) throws IOException {
        // Build the request URL
        String urlString = API_URL + baseCurrency;
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            LOGGER.log(Level.SEVERE, "HTTP error code: {0}", responseCode);
            throw new IOException("HTTP error: " + responseCode);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONObject jsonResponse = new JSONObject(response.toString());

        if (!jsonResponse.has("conversion_rates")) {
            LOGGER.log(Level.SEVERE, "Conversion rates not found in the response");
            throw new JSONException("Conversion rates not found in the response");
        }

        JSONObject conversionRates = jsonResponse.getJSONObject("conversion_rates");

        if (!conversionRates.has(targetCurrency)) {
            LOGGER.log(Level.SEVERE, "Target currency rate not found in the response");
            throw new JSONException("Target currency rate not found in the response");
        }

        // Calculate the converted amount
        float rate = conversionRates.getFloat(targetCurrency);
        return amount * rate;
    }
}
