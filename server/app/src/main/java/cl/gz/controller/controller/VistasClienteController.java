/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.controller;

import cl.gz.server.modelo.Computador;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author sistemas
 */
public class VistasClienteController implements Initializable {
    
    private List<ClienteController> computadoresControllers = new ArrayList<>();
    
    private List<Computador> computadores = new ArrayList<>();
    
    private Parent parent;

    @FXML
    private HBox hBoxArriba;
    @FXML
    private HBox hBoxAbajo;
    
    private final int SIZE_ROWS = 5;
    
    private int numeroDeLaVista;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    /**
     * permitire agregar un computador y vincularlo al controlador del cliente
     * @return false if cannot add more to this view.
     */
    public boolean addComputador(Computador computador){
        
        FXMLLoader fxmlLoader = ControladorMaestro.getFXMLLoader("Cliente");
        Parent parent = ControladorMaestro.getParent(fxmlLoader);
        ClienteController clienteController = (ClienteController) ControladorMaestro.getController(fxmlLoader);
        clienteController.setComputador(computador);
        boolean puedeAgregar = false;
        if(!addNodeToHbox(parent)){
            puedeAgregar = true;
            computadoresControllers.add(clienteController);
        }
        clienteController.setPropertys();
        return puedeAgregar;
    }
    
    /**
     * Se encargara de limitar las vistas cuantos pc podran contener por cada fila.
     * @param hboxPorLlenar cual de las filas se llenara
     * @param parent nodo que se agregara al hbox
     * @return true en caso de que se alcance el maximo que soportara una fila
     */
    public boolean addNodeToHbox(Parent parent){
        boolean lleno = false;
        if(hBoxArriba.getChildren().size() < SIZE_ROWS){
            hBoxArriba.getChildren().add(parent);
        }else{
            if(hBoxAbajo.getChildren().size() < SIZE_ROWS){
                hBoxAbajo.getChildren().add(parent);
            }else{
                lleno = true;
            }
        }
        return lleno;
    }
    
    public void limpiarVista(){
        hBoxArriba.getChildren().clear();
        hBoxAbajo.getChildren().clear();
    }
    
    /*public void cambiarEstadoComputador(Computador computador){
        byte estado = this.computador.getEstado();
        System.out.println("Computador " + this.computador.getNombreComputador() + ", estado: " + estado);
        switch (estado) {
            case -1: {
                this.estado.getStylesheets().add("cl/gz/bibliotecacontrol/css/estados.css");
            }
            break;
            case 0: {
                this.estado.getStylesheets().add(getClass().getResource("cl/gz/bibliotecacontrol/css/desconectado.css").toExternalForm());
            }
            break;
            case 1: {
                this.estado.getStylesheets().add("cl/gz/bibliotecacontrol/css/disponible.css");
            }
            break;
        }
    }*/
    
    public List<ClienteController> getComputadoresControllers(){
        return this.computadoresControllers;
    }
    
    public void setComputadoresControllers(List<ClienteController> computadoresControllers){
        this.computadoresControllers = computadoresControllers;
    }
    
    public void setNumeroDeLaLista(int numero){
        this.numeroDeLaVista = numero;
    }
    
    public int getNumeroDeLaLista(){
        return this.numeroDeLaVista;
    }
    
    public void setParent(Parent parent){
        this.parent = parent;
    }
    
    public Parent getParent(){
        return parent;
    }
    
    public String toString(){
        return "" + this.numeroDeLaVista;
    }

    public HBox gethBoxArriba() {
        return hBoxArriba;
    }

    public HBox gethBoxAbajo() {
        return hBoxAbajo;
    }
    
    public void borrarComputadores(){
        this.computadoresControllers.clear();
    }
    
    public List<Computador> getComputadores(){
        return this.computadores;
    }
    
}
