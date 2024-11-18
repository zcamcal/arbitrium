/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.windows;

import cl.gz.controller.controller.ControladorMaestro;
import cl.gz.controller.controller.ReservasController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Singleton
 * @author sistemas
 */
public class Pedidos {
    
    private static Pedidos instance;
    
    private ReservasController reservasController;
    
    private Stage reservasStage;
    
    private Pedidos(){
        init();
    }
    
    public static Pedidos getInstance(){
        if(Pedidos.instance == null){
            Pedidos.instance = new Pedidos();
        }
        return Pedidos.instance;
    }
    
    public void init(){
        FXMLLoader fxmlLoader = ControladorMaestro.getFXMLLoader("Reservas");
        Parent parent = ControladorMaestro.getParent(fxmlLoader);
        ReservasController reservasController = (ReservasController) ControladorMaestro.getController(fxmlLoader);

        Scene scene = new Scene(parent);

        Stage newStage = new Stage();
        newStage.setTitle("Reserva de computadores");
        newStage.setScene(scene);

        newStage.initModality(Modality.WINDOW_MODAL);

        newStage.initOwner(ControladorMaestro.getStage());

        
        this.reservasController = reservasController;
        
        this.reservasStage = newStage;
        
    }
    
    public void show(){
        this.reservasStage.show();
    }
    
}
