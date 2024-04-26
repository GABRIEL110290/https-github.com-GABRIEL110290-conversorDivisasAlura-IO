package com.example.conversordivisasalura;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class HelloController {

    public TextField conversionAmount;
    @FXML
    private ComboBox<String> comboBoxFrom;

    @FXML
    private ComboBox<String> comboBoxTo;

    @FXML
    private TextField conversionResult;

    // Clave de API válida proporcionada por ExchangeRate-API
    private final String API_KEY = "5c7d2b006da0e4876edf7d0d";

    @FXML
    protected void initialize() {
        // Inicializar los ComboBox con las divisas
        comboBoxFrom.getItems().addAll("CRC", "USD", "COP", "ARS", "MXN", "EUR", "NIO");
        comboBoxTo.getItems().addAll("CRC", "USD", "COP", "ARS", "MXN", "EUR", "NIO");
    }

    @FXML
    protected void onConvertButtonClick() {
        // Obtener las divisas seleccionadas
        String divisaOrigen = comboBoxFrom.getValue();
        String divisaDestino = comboBoxTo.getValue();

        // Verificar que el campo de cantidad no esté vacío
        String amountText = conversionAmount.getText();
        if (!amountText.isEmpty()) {
            try {
                // Convertir el texto ingresado en un número
                double amount = Double.parseDouble(amountText);

                // Realizar la solicitud a la API de ExchangeRate-API para obtener la tasa de cambio
                URL url = new URL("https", "v6.exchangerate-api.com", "/v6/" + API_KEY + "/latest/" + divisaOrigen);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = inputReader.readLine()) != null) {
                    response.append(line);
                }
                inputReader.close();

                // Parsjear la respuesta JSON
                JSONParser parser = new JSONParser();
                JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());
                JSONObject rates = (JSONObject) jsonResponse.get("conversion_rates");
                double exchangeRate = (double) rates.get(divisaDestino);

                // Calcular la conversión
                double convertedAmount = amount * exchangeRate;

                // Mostrar el resultado en el TextField
                String resultadoConversion = String.format("%.2f %s = %.2f %s", amount, divisaOrigen, convertedAmount, divisaDestino);
                conversionResult.setText(resultadoConversion);
            } catch (NumberFormatException e) {
                // Manejar el caso en que el texto ingresado no sea un número
                System.err.println("El valor ingresado no es un número válido.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Manejar el caso en que el campo esté vacío
            System.err.println("Debe ingresar un valor antes de convertir.");
        }
    }
}



