/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.controller;

import cl.gz.server.modelo.Computador;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Rednation
 */
public class ReservasController implements Initializable {

    @FXML
    private TableView tablaReservas;
    
    @FXML
    private TableColumn computador;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        computador.setCellValueFactory(new PropertyValueFactory<>("nombreComputador"));
    }
    
    
    
    
}
