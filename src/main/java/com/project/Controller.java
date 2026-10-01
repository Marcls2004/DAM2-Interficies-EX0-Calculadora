package com.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Clase Controladora de la Calculadora.
 * Se encarga de recibir los clics de la interfaz visual (Scene Builder)
 * y aplicar la lógica matemática para mostrar los resultados en pantalla.
 */
public class Controller {

    // ==========================================
    // COMPONENTES DE LA INTERFAZ (VINCULADOS A SCENE BUILDER)
    // La anotación @FXML indica que estas variables están unidas a los elementos del FXML.
    // ==========================================
    
    @FXML
    private Button button0; // Botón del número 0

    @FXML
    private Button button1; // Botón del número 1

    @FXML
    private Button button2; // Botón del número 2

    @FXML
    private Button button3; // Botón del número 3

    @FXML
    private Button button4; // Botón del número 4

    @FXML
    private Button button5; // Botón del número 5

    @FXML
    private Button button6; // Botón del número 6

    @FXML
    private Button button7; // Botón del número 7

    @FXML
    private Button button8; // Botón del número 8

    @FXML
    private Button button9; // Botón del número 9

    @FXML
    private Button buttonEqual; // Botón de resultado (=)

    @FXML
    private Button buttonMulti; // Botón de multiplicación (*)

    @FXML
    private Button buttonPlus; // Botón de suma (+)

    @FXML
    private Button buttonReset; // Botón de borrado completo (C)

    @FXML
    private Button buttonRest; // Botón de resta (-)

    @FXML
    private Button buttonSplit; // Botón de división (/)

    @FXML
    private Label txtScreen; // Elemento de texto superior que actúa como pantalla de la calculadora


    // ==========================================
    // VARIABLES DE ESTADO (LÓGICA INTERNA)
    // ==========================================
    
    private String operador = "";         // Guarda el símbolo de la operación activa ("+", "-", "*", "/")
    private double primerNumero = 0;      // Guarda el primer número introducido antes de pulsar un operador
    private boolean empezarNuevoNumero = true; // Controla si al pulsar un número se debe borrar la pantalla (ej: después de pulsar "+")

    
    // ==========================================
    // MÉTODOS DE EVENTOS (ACCIONES DE LOS BOTONES)
    // ==========================================

    /**
     * Se ejecuta cuando el usuario pulsa cualquier botón numérico (del 0 al 9).
     * @param event Información del evento del clic.
     */
    @FXML
    void onPlusNumber(ActionEvent event) {
        // Si acabamos de pulsar un operador o es el inicio, limpiamos la pantalla para el nuevo número
        if (empezarNuevoNumero) {
            txtScreen.setText("");
            empezarNuevoNumero = false; // Cambiamos el estado para que los siguientes dígitos se acumulen
        }
        
        // Obtenemos el botón físico exacto que ha disparado el clic (un casting a Button)
        Button botonPulsado = (Button) event.getSource();
        
        // Recuperamos el texto del botón (ej: "5") y lo concatenamos a lo que ya muestre la pantalla
        txtScreen.setText(txtScreen.getText() + botonPulsado.getText());
    }

    /**
     * Se ejecuta cuando el usuario pulsa botones de acción (+, -, *, /, = o C).
     * @param event Información del evento del clic.
     */
    @FXML
    void onOperacion(ActionEvent event) {
        // Identificamos el botón de operación que se ha pulsado
        Button botonPulsado = (Button) event.getSource();
        
        // 1. CASO BOTÓN RESET (C): Limpieza total de la calculadora
        if (botonPulsado == buttonReset) {
            txtScreen.setText("");         // Vacía la pantalla visual
            primerNumero = 0;              // Resetea el primer operando
            operador = "";                 // Elimina el operador guardado
            empezarNuevoNumero = true;     // Prepara la pantalla para un nuevo inicio
            return;                        // Finaliza la ejecución de este clic
        }

        // 2. CASO OPERADORES ARITMÉTICOS (+, -, *, /): Guardar el estado actual
        if (botonPulsado != buttonEqual) {
            // Comprobamos que haya algún número escrito en la pantalla para poder operar
            if (!txtScreen.getText().isEmpty()) {
                primerNumero = Double.parseDouble(txtScreen.getText()); // Convierte el texto de la pantalla a formato numérico decimal
                operador = botonPulsado.getText(); // Almacena el carácter del botón pulsado como operador activo
                empezarNuevoNumero = true;         // Indica que el siguiente número que se pulse requerirá limpiar la pantalla
            }
        } 
        // 3. CASO BOTÓN IGUAL (=): Procesar y mostrar el resultado final
        else {
            // Validación de seguridad: Si no hay operador guardado o la pantalla está vacía, no hace nada
            if (operador.isEmpty() || txtScreen.getText().isEmpty()) return;

            // Convertimos el texto actual de la pantalla en el segundo número de la operación
            double segundoNumero = Double.parseDouble(txtScreen.getText());
            
            // Llamamos al método interno 'calcular' pasándole los datos guardados
            double resultado = calcular(primerNumero, segundoNumero, operador);
            
            // Formateo visual estético: Si el resultado es un número entero perfecto (ej: 5.0), 
            // eliminamos el decimal residual (.0) convirtiéndolo a entero (int) para que se vea limpio en pantalla (5)
            if (resultado % 1 == 0) {
                txtScreen.setText(String.valueOf((int) resultado));
            } else {
                txtScreen.setText(String.valueOf(resultado)); // Si tiene decimales reales (ej: 5.25), se muestra completo
            }
            
            // Reseteamos el operador y preparamos el estado para una nueva operación consecutiva
            operador = "";
            empezarNuevoNumero = true;
        }
    }

    // ==========================================
    // MÉTODO AUXILIAR DE PROCESAMIENTO
    // ==========================================

    /**
     * Realiza matemáticamente la operación seleccionada.
     * @param n1 Primer operando.
     * @param n2 Segundo operando.
     * @param op Símbolo de la operación matemática.
     * @return El resultado numérico de la operación.
     */
    private double calcular(double n1, double n2, String op) {
        switch (op) {
            case "+": return n1 + n2; // Suma básica
            case "-": return n1 - n2; // Resta básica
            case "*": return n1 * n2; // Multiplicación básica
            case "/": 
                // Validación matemática de seguridad: Evitar la división por cero
                if (n2 == 0) {
                    txtScreen.setText("Error"); // Muestra el mensaje de aviso en la interfaz
                    return 0;                   // Retorna cero por defecto para evitar cuelgues
                }
                return n1 / n2; // División si el segundo número es válido
            default: return 0; // Si llega un operador desconocido, devuelve 0 por seguridad
        }
    }
}
