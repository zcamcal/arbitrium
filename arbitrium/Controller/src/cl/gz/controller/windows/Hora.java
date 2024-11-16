/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.windows;

import cl.gz.controller.controller.ControladorMaestro;
import cl.gz.controller.controller.PedirHoraController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author sistemas
 */
public class Hora {
    
    private static Hora instance;
    
    private Stage horaStage;
    
    private PedirHoraController horaController;
    
    private Hora(){
        init();
    }
    
    public static Hora getInstance(){
        if(Hora.instance == null){
            Hora.instance = new Hora();
        }
        return Hora.instance;
    }
    
    public void init(){
        FXMLLoader fxmlLoader = ControladorMaestro.getFXMLLoader("PedirHora");
        Parent parent = ControladorMaestro.getParent(fxmlLoader);
        PedirHoraController pedirHoraController = (PedirHoraController) ControladorMaestro.getController(fxmlLoader);

        Scene scene = new Scene(parent);

        Stage newStage = new Stage();
        newStage.setTitle("Seleccion de hora para reservas de computador");
        newStage.setScene(scene);

        newStage.initModality(Modality.WINDOW_MODAL);

        newStage.initOwner(ControladorMaestro.getStage());
        
        this.horaStage = newStage;
    
        this.horaController = pedirHoraController;
        
    }
    
    public void show(){
        this.horaStage.show();
    }
    
}
