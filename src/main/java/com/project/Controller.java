package com.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Button button0;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    @FXML
    private Button buttonEqual;

    @FXML
    private Button buttonMulti;

    @FXML
    private Button buttonPlus;

    @FXML
    private Button buttonReset;

    @FXML
    private Button buttonRest;

    @FXML
    private Button buttonSplit;

    @FXML
    private Label txtScreen;


    // Variables globales para la lógica matemática
    private String operador = "";
    private double primerNumero = 0;
    private boolean empezarNuevoNumero = true;

    @FXML
    void onPlusNumber(ActionEvent event) {
        // Si acabamos de pulsar un operador, limpiamos la pantalla para el nuevo número
        if (empezarNuevoNumero) {
            txtScreen.setText("");
            empezarNuevoNumero = false;
        }
        
        // Obtenemos el botón numérico que se ha pulsado
        Button botonPulsado = (Button) event.getSource();
        
        // Añadimos el número del botón a lo que ya esté escrito en la pantalla
        txtScreen.setText(txtScreen.getText() + botonPulsado.getText());
    }

    @FXML
    void onOperacion(ActionEvent event) {
        Button botonPulsado = (Button) event.getSource();
        
        // 1. Si se pulsa el botón de Reset (C)
        if (botonPulsado == buttonReset) {
            txtScreen.setText("");
            primerNumero = 0;
            operador = "";
            empezarNuevoNumero = true;
            return;
        }

        // 2. Si se pulsa cualquier operador (+, -, *, /) que NO sea el igual
        if (botonPulsado != buttonEqual) {
            if (!txtScreen.getText().isEmpty()) {
                primerNumero = Double.parseDouble(txtScreen.getText());
                operador = botonPulsado.getText(); // Guarda "+", "-", "*" o "/"
                empezarNuevoNumero = true;
            }
        } 
        // 3. Si se pulsa el botón de Igual (=)
        else {
            if (operador.isEmpty() || txtScreen.getText().isEmpty()) return;

            double segundoNumero = Double.parseDouble(txtScreen.getText());
            double resultado = calcular(primerNumero, segundoNumero, operador);
            
            // Si el resultado es un número entero (ej: 5.0), quitamos el decimal (.0) para que se vea limpio (5)
            if (resultado % 1 == 0) {
                txtScreen.setText(String.valueOf((int) resultado));
            } else {
                txtScreen.setText(String.valueOf(resultado));
            }
            
            operador = "";
            empezarNuevoNumero = true;
        }
    }

    // Método auxiliar para realizar el cálculo matemático básico
    private double calcular(double n1, double n2, String op) {
        switch (op) {
            case "+": return n1 + n2;
            case "-": return n1 - n2;
            case "*": return n1 * n2;
            case "/": 
                if (n2 == 0) {
                    txtScreen.setText("Error");
                    return 0;
                }
                return n1 / n2;
            default: return 0;
        }
    }
}
