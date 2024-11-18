/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.alerts;

import cl.gz.controller.controller.ControladorMaestro;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 *16/07/2019
 * Fastdevelopment
 * clase creada para mostrar un error de alerta.
 */
public class ErrorAlert {
    
    public static void show(String mensaje){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText(mensaje);
        alert.initOwner(ControladorMaestro.getStage());

        alert.showAndWait();
    }
    
}
