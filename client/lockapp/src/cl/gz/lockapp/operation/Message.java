/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.lockapp.operation;

import cl.gz.lockapp.controller.Controller;
import cl.gz.lockapp.controller.PanelMensajeController;
import cl.gz.rmi.Messageable;
import java.rmi.RemoteException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Rednation
 */
public class Message implements Messageable {

    private static Message message;

    private Message() {

    }

    public static Message getInstance() {
        if (Message.message == null) {
            Message.message = new Message();
        }
        return Message.message;
    }

    public void newMessage(String message) {

        FXMLLoader fxmlLoader = Controller.getFXML("PanelMensaje");
        Parent parent = Controller.getParent(fxmlLoader);
        PanelMensajeController panelMensajeController = (PanelMensajeController) Controller.getController(fxmlLoader);

        panelMensajeController.showMessage(message);

        Platform.runLater(() -> {
            Stage newStage = new Stage();
            panelMensajeController.setStage(newStage);

            Scene scene = new Scene(parent);

            newStage.setScene(scene);
            newStage.show();
            newStage.centerOnScreen();
            newStage.setTitle("Mensaje De Biblioteca");
            if(!Lock.getInstance(null).getEstadoComputer()){
                newStage.setAlwaysOnTop(true);
                newStage.requestFocus();
            }
        });

    }

    @Override
    public void showMessage(String message) throws RemoteException {
        newMessage(message);
    }

}
