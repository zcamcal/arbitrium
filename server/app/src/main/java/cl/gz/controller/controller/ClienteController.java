/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.controller;

import cl.gz.server.modelo.Computador;
import cl.gz.server.operacion.Message;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sistemas
 */
public class ClienteController implements Initializable {

    private Computador computador;
    
    @FXML
    private VBox colorEstado;

    @FXML
    private CheckBox chkSeleccionado;
    
    @FXML
    private ImageView screenShot;
    
    @FXML
    private Button btnBloquear;
    
    private SimpleIntegerProperty estado = new SimpleIntegerProperty(this, "estado");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (chkSeleccionado.isSelected()) {
                    ControladorMaestro.getAplicacionController().addClientesController(ClienteController.this);
                } else {
                    ControladorMaestro.getAplicacionController().removeFromClientesController(ClienteController.this);
                }
            }
        };

        chkSeleccionado.setOnAction(event);
        
        cambiarEstado();
        
    }

    public void setPropertys(){
        
        this.chkSeleccionado.textProperty().bind(computador.getNombreComputador());
        
//        this.lblNombreComputador.textProperty().bind(computador.getNombreComputador());
        
        this.screenShot.imageProperty().bind(computador.getScreenShot());
        
        this.estado.bind(computador.getEstado());
        
        this.estado.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                cambiarEstado();
            }
        });
        
    }
    
    /**
     * ATENTO CON ESTE METODO
     * @deprecated 
     */
    public void cambiarEstado() {
        
        Platform.runLater(() -> {
            int estado = this.computador.getEstado().get();
            switch (estado) {
                case -1: {
                    this.colorEstado.setStyle("-fx-effect: dropshadow( gaussian, rgba(230, 240, 40, 0.8), 30, 0.5, 0.0, 0.0 );"
                            + "-fx-border-color: rgba(230, 240, 40, 0.8);");
                    btnBloquear.setDisable(false);
                    btnBloquear.setText("DESBLOQUEAR");
                }
                break;
                case 0: {
                    this.colorEstado.setStyle("-fx-effect: dropshadow( gaussian, rgba(229, 50, 53, 0.8), 30, 0.5, 0.0, 0.0 );"
                            + "-fx-border-color: rgba(229, 50, 53, 0.8);");
                    btnBloquear.setDisable(true);
                }
                break;
                case 1: {
                    this.colorEstado.setStyle("-fx-effect: dropshadow( gaussian, rgba(63, 191, 63, 0.8), 30, 0.5, 0.0, 0.0 );"
                            + "-fx-border-color: rgba(63, 191, 63, 0.8);");
                    btnBloquear.setDisable(false);
                    btnBloquear.setText("BLOQUEAR");
                }
                break;
            }
        });

    }
    
    /**
     * activa el checkbox de este clienteController
     * @param selected true para marcar el checkbox con un ticket
     */
    public void setCheckBoxSelected(boolean selected){
        if(!chkSeleccionado.isSelected()){
            this.chkSeleccionado.setSelected(selected);
            ControladorMaestro.getAplicacionController().addClientesController(this);
        }
    }
    
    public void setCheckBoxUnselected(){
        this.chkSeleccionado.setSelected(false);
    }

    public Computador getComputador() {
        return this.computador;
    }
    
    public void setComputador(Computador computador) {
        this.computador = computador;
    }
    
    public ImageView getScreenShot() {
        return screenShot;
    }

    public Button getBtnBloquear() {
        return btnBloquear;
    }

    public void setBtnBloquear(String textButton) {
        this.btnBloquear.setText(textButton);
    }
    
    public String toString() {
        return this.computador.getNombreComputador().get();
    }

    @FXML
    private void btnScreenShotViewEvent(MouseEvent event) {
        
        FXMLLoader fxmlLoader = ControladorMaestro.getFXMLLoader("ScreenShoot");
        Parent parent = ControladorMaestro.getParent(fxmlLoader);
        ScreenShootController screenShot = (ScreenShootController) ControladorMaestro.getController(fxmlLoader);

        Scene scene = new Scene(parent);

        Stage newStage = new Stage();
        newStage.setTitle(this.getComputador().getNombreComputador().get());
        newStage.setScene(scene);

        newStage.initModality(Modality.WINDOW_MODAL);
        
        newStage.initOwner(ControladorMaestro.getStage());

        newStage.show();
        
        screenShot.setScreenShot(computador);
        screenShot.setStage(newStage);
        screenShot.setResponsive();
        
    }
    
    /**
     * metodo encargado de manejar el bloqueo en el equipo cliente y 
     * representarlo de manera visual.
     * @param event click
     */
    @FXML
    private void btnBloquearEvent(MouseEvent event) {
        if(btnBloquear.getText().equals("BLOQUEAR")){
            boolean bloqueoExitoso = Message.bloquear(this.computador);
            if(bloqueoExitoso){
                btnBloquear.setText("DESBLOQUEAR");
                this.computador.setEstado((byte)-1);
            }
        }else{
            boolean desbloqueoExitoso = Message.desbloquear(this.computador);
            if(desbloqueoExitoso){
                btnBloquear.setText("BLOQUEAR");
                this.computador.setEstado((byte)1);
            }
        }
    }

}
