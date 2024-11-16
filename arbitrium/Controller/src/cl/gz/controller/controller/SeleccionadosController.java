/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author sistemas
 */
public class SeleccionadosController implements Initializable {

    @FXML
    private ListView<ClienteController> listSeleccionados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void addToListView(List<ClienteController> clientes) {
        listSeleccionados.getItems().addAll(clientes);
    }
    
    public void clear(){
        listSeleccionados.getItems().clear();
    }

}
