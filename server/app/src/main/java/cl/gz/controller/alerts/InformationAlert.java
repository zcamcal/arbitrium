/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.alerts;

import cl.gz.controller.controller.ControladorMaestro;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * 16/07/2019
 * Fastdevelopment
 * clase creada para generar un pop up de informacion de alerta.
 */
public class InformationAlert {
    
    public static void show(String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.initOwner(ControladorMaestro.getStage());
        alert.showAndWait();
    }
    
    public static void show(String mensaje, String nueva){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.initOwner(ControladorMaestro.getStage());
        alert.showAndWait();
    }
    
}
