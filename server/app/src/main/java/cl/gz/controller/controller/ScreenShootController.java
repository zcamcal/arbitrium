/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.controller;

import cl.gz.server.modelo.Computador;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sistemas
 */
public class ScreenShootController implements Initializable {

    @FXML
    private ImageView screenShot;
    
    private Stage stage;
    @FXML
    private VBox vBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public synchronized void setScreenShot(Computador computador) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ScreenShootController.this.screenShot.setImage(computador.getScreenShot().get());
                }
            });
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
        configureScreen();
    }
    
    /**
     * configura la ventana para impedir ser obstruida por la barra de tareas.
     */
    private void configureScreen(){
        //obtenemos un rectangulo que es la pantalla con los valores descontando la barra de tareas.
        //para que la ventana no obstruya o sea obstruida por la barra de tareas.
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setMaxHeight(primaryScreenBounds.getHeight());
        this.stage.setMaxWidth(primaryScreenBounds.getWidth());
    }
    
    /**
     * genera responsividad sobre la imagen.
     */
    public void setResponsive(){
        
        //listener que escucha el alto de la ventana.
        this.stage.heightProperty().addListener((observable) -> {
            //en caso que cambie el alto, se actualizara el vbox que contiene a la imagen
            this.vBox.setPrefHeight(this.stage.getHeight());
        });
        
        //listener que escucha el ancho de la ventana.
        this.stage.widthProperty().addListener((observable) -> {
            //en caso que cambie el ancho, se actualizara el vbox que contiene a la imagen
            this.vBox.setPrefWidth(this.stage.getHeight());
        });
        
        //listener que escucha el alto de el vBox.
        this.vBox.heightProperty().addListener((observable) -> {
            //en caso de cambio, este le dice a la imagen que cresca.
            screenShot.setFitHeight(this.vBox.getHeight() - 100);
        });
        
        //listener que escucha el ancho de el vBox.
        this.vBox.widthProperty().addListener((observable) -> {
            //en caso de cambio, este le dice a la imagen que cresca.
            screenShot.setFitWidth(this.vBox.getWidth() - 100);
        });
        
    }
    
}
