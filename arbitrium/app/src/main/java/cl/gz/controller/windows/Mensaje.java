/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.windows;

import cl.gz.controller.controller.ClienteController;
import cl.gz.controller.controller.ControladorMaestro;
import cl.gz.controller.controller.MensajeController;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author sistemas
 */
public class Mensaje {

    private static Mensaje instance;

    private MensajeController mensajeController;

    private Stage mensajeStage;

    private Mensaje() {
        init();
    }

    public static Mensaje getInstance() {
        if (Mensaje.instance == null) {
            Mensaje.instance = new Mensaje();
        }
        return Mensaje.instance;
    }

    public void init() {
        FXMLLoader fxmlLoader = ControladorMaestro.getFXMLLoader("Mensaje");
        Parent parent = ControladorMaestro.getParent(fxmlLoader);
        MensajeController mensajeController = (MensajeController) ControladorMaestro.getController(fxmlLoader);

        Scene scene = new Scene(parent);

        Stage newStage = new Stage();
        newStage.setTitle("Mensaje a los Seleccionados");
        newStage.setScene(scene);

        newStage.initModality(Modality.WINDOW_MODAL);

        newStage.initOwner(ControladorMaestro.getStage());

        this.mensajeController = mensajeController;

        this.mensajeStage = newStage;
    }

    private void show() {
        this.mensajeStage.show();
    }
    
    public void sendMessage(List<ClienteController> clientes){
        this.mensajeController.setList(clientes);
        show();
    }

}
