/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.windows;

import cl.gz.controller.controller.ClienteController;
import cl.gz.controller.controller.ControladorMaestro;
import cl.gz.controller.controller.SeleccionadosController;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Singleton
 * @author Zachariel
 */
public class Seleccionados {
    
    private static Seleccionados instance;
    
    private SeleccionadosController seleccionadosController;
    
    private Stage seleccionadosStage;
    
    private Seleccionados(){
        init();
    }
    
    public static Seleccionados createInstance(){
        if(instance == null){
            instance = new Seleccionados();
        }
        return Seleccionados.instance;
    }
    
    private void init(){
        FXMLLoader fxmlLoader = ControladorMaestro.getFXMLLoader("Seleccionados");
        Parent parent = ControladorMaestro.getParent(fxmlLoader);
        SeleccionadosController seleccionadosController = (SeleccionadosController) ControladorMaestro.getController(fxmlLoader);

        Scene scene = new Scene(parent);

        Stage newStage = new Stage();
        newStage.setTitle("Computadores Seleccionados");
        newStage.setScene(scene);

        newStage.initModality(Modality.WINDOW_MODAL);

        newStage.initOwner(ControladorMaestro.getStage());

        this.seleccionadosStage = newStage;
        
        this.seleccionadosController = seleccionadosController;
        
        setEvents();
        
    }
    
    private void show(){
        this.seleccionadosStage.show();
    }
    
    private void close(){
        this.seleccionadosStage.close();
    }
    
    public void showSeleccionados(List<ClienteController> clientes){
        this.seleccionadosController.addToListView(clientes);
        show();
    }
    
    private void clearSeleccionados(){
        this.seleccionadosController.clear();
    }
    
    private void setEvents(){
        this.seleccionadosStage.setOnCloseRequest((event) -> {
            clearSeleccionados();
        });
    }
    
}
