/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.lockapp.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rednation
 */
public class PanelMensajeController implements Initializable {

    @FXML
    private TextArea mensajeRecivido;
    
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    public void showMessage(String message){
        mensajeRecivido.setText(message);
    }

    @FXML
    private void btnOkEvent(MouseEvent event) {
        stage.close();
        stage = null;
    }

}
