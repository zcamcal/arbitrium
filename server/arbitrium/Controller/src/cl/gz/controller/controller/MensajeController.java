/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.controller;

import cl.gz.server.modelo.Computador;
import cl.gz.server.operacion.Message;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sistemas
 */
public class MensajeController implements Initializable {

    @FXML
    private TextArea mensaje;
    
    private List<ClienteController> clientes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setList(List<ClienteController> clientes){
        this.clientes = clientes;
    }

    /**
     * manda mensaje personalizado a los clientes
     */
    @FXML
    public void btnEnviarMensajeEvent(){
        //aqui hay que capturar la variable del textInput con el mensaje
        String message = mensaje.getText();
        
        for (int i = 0; i < clientes.size(); i++) {
            
            Computador computador = clientes.get(i).getComputador();
            Message.sendMessage(computador, message);
            clientes.get(i).setCheckBoxUnselected();
            
        }
        
        ControladorMaestro.getAplicacionController().clearClientesController();
        Stage stage = (Stage) mensaje.getScene().getWindow();
        mensaje.setText("");
        stage.close();
    }
    
    public void clearList(){
        this.clientes.clear();
        this.clientes = null;
    }
    
}
