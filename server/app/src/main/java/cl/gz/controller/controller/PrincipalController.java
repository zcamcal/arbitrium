/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.controller;

import cl.gz.controller.alerts.ErrorAlert;
import cl.gz.controller.windows.Hora;
import cl.gz.controller.windows.Mensaje;
import cl.gz.controller.windows.Pedidos;
import cl.gz.controller.windows.Seleccionados;
import cl.gz.server.dao.ComputadorDAO;
import cl.gz.server.modelo.Computador;
import cl.gz.server.operacion.Message;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sistemas
 */
public class PrincipalController implements Initializable {

    private ObservableList<Computador> computadores;

    ListProperty lp;

    @FXML
    private BorderPane borderPane;

    private List<VistasClienteController> vistas = new ArrayList<>();

    private final int COMPUTADORES_POR_VISTA = 10;

    private Parent vistaActual;

    private int numeroDeVistaActual;

    private List<ClienteController> clientesSeleccionados = new ArrayList<>();

    private int oldSize;
    
    private Seleccionados seleccionados;
    
    private Pedidos pedidos;
    
    private Hora hora;
    
    private Mensaje mensaje;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        seleccionados = Seleccionados.createInstance();
        
        pedidos = Pedidos.getInstance();
        
        hora = Hora.getInstance();
        
        mensaje = Mensaje.getInstance();
        
        computadores = ComputadorDAO.getComputadores();
        oldSize = computadores.size();

        lp = new SimpleListProperty(computadores);

        lp.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (computadores.size() > oldSize) {
                    oldSize = computadores.size();
                    setVistas(computadores);
                }
            }
        });
        actualizarScreenShots();

    }

    /**
     * Avisara si se borro con exito las vistas.
     *
     * @return true si se borraron
     */
    public boolean eliminarLasVistas() {
        this.vistaActual = null;
        this.vistas.clear();
        return true;
    }

    public List<VistasClienteController> getVistas() {
        return this.vistas;
    }

    public void addClientesController(ClienteController clienteController) {
        clientesSeleccionados.add(clienteController);
    }

    public List<ClienteController> getClientesSeleccionados() {
        return this.clientesSeleccionados;
    }

    public void removeFromClientesController(ClienteController clienteController) {
        this.clientesSeleccionados.remove(clienteController);
    }

    public void clearClientesController() {
        this.clientesSeleccionados.clear();
    }

    /**
     * Se encarga de crear las vistas necesarias con los computadores que
     * existen actualmente conectados al servidor. PostData: se podria trabajar
     * con un aviso en tonces se crean las vistas primero, para luego remplazar
     * por las que ya existen. NO TOCAR ESTE CODIGO O MUERE TODA LA APLICACION
     *
     * @param computadores lista que contiene todos los computadores conectados
     * al servidor
     */
    public synchronized void setVistas(List<Computador> computadores) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                eliminarLasVistas();

                Integer largoLista = computadores.size();

                //iteracionPorVistas se calcula en base a los computadores por vista que habra / por largoLista
                int iteracionPorVistas = (int) Math.ceil(((double) largoLista / (double) COMPUTADORES_POR_VISTA));

                for (int i = 0, computador = 0; i < iteracionPorVistas; i++) {

                    VistasClienteController vista = crearVistaComputador(i);
                    vistas.add(vista);
                    vista.limpiarVista();

                    for (; computador < largoLista; computador++) {
                        //dentro del if se agregan los computadores
                        if (vista.addComputador(computadores.get(computador))) {
                            continue;
                        } else {
                            break;
                        }
                    }
                    //si es que estamos creando la vista en al que el usuario se encontraba
                    //actualmente, entonces la posicionamos sobre el centro pero la nueva vista.
                    //el break es para cambiar el ciclo y llenar la siguiente vista
                    if (i == numeroDeVistaActual) {
                        setVistaOnCenter(vista.getParent());
                    }

                }

            }
        });

    }

    public VistasClienteController crearVistaComputador(int numeroDeLaLista) {
        FXMLLoader fxmlLoader = ControladorMaestro.getFXMLLoader("VistasCliente");
        Parent parent = ControladorMaestro.getParent(fxmlLoader);
        VistasClienteController vistasClienteController = (VistasClienteController) ControladorMaestro.getController(fxmlLoader);
        vistasClienteController.setNumeroDeLaLista(numeroDeLaLista);
        vistasClienteController.setParent(parent);
        return vistasClienteController;
    }

    public void setVistaOnCenter(Parent parent) {
        borderPane.setCenter(parent);
    }

    public void setVistaActual(Parent parent) {
        this.vistaActual = parent;
    }




    @FXML
    private void btnBloquearEvent(MouseEvent event) {
        for (int i = 0; i < this.clientesSeleccionados.size(); i++) {
            Computador computador = this.clientesSeleccionados.get(i).getComputador();
            boolean bloqueoExitoso = Message.bloquear(computador);
            if (bloqueoExitoso) {
                this.clientesSeleccionados.get(i).setBtnBloquear("DESBLOQUEAR");
                this.clientesSeleccionados.get(i).getComputador().setEstado((byte) -1);
            }
            this.clientesSeleccionados.get(i).setCheckBoxUnselected();
        }
        //Limpiamos la lista con los seleccionados
        clearClientesController();
    }

    @FXML
    private void btnDesbloquearEvent(MouseEvent event) {
        for (int i = 0; i < this.clientesSeleccionados.size(); i++) {
            Computador computador = this.clientesSeleccionados.get(i).getComputador();
            boolean desbloqueoExitoso = Message.desbloquear(computador);
            if (desbloqueoExitoso) {
                this.clientesSeleccionados.get(i).setBtnBloquear("BLOQUEAR");
                this.clientesSeleccionados.get(i).getComputador().setEstado((byte) 1);
            }
            this.clientesSeleccionados.get(i).setCheckBoxUnselected();
        }
        //limpiamos la lista con los seleccionados
        clearClientesController();
    }

    @FXML
    private void btnSeleccionarTodosEvent(MouseEvent event) {
        for (int vista = 0; vista < vistas.size(); vista++) {
            VistasClienteController vistaController = vistas.get(vista);
            for (int j = 0; j < vistaController.getComputadoresControllers().size(); j++) {
                ClienteController clienteController = vistaController.getComputadoresControllers().get(j);
                clienteController.setCheckBoxSelected(true);
            }
        }
    }

    @FXML
    private void btnVistaAnteriorEvent(MouseEvent event) {
        if (numeroDeVistaActual > 0) {
            Parent parent = vistas.get(--numeroDeVistaActual).getParent();
            setVistaOnCenter(parent);
            setVistaActual(parent);
        }
    }

    @FXML
    private void btnVistaSiguienteEvent(MouseEvent event) {
        if (numeroDeVistaActual < vistas.size() - 1) {
            Parent parent = vistas.get(++numeroDeVistaActual).getParent();
            setVistaOnCenter(parent);
            setVistaActual(parent);
        }
    }

    @FXML
    private void btnPedidosEvent(MouseEvent event) {
        this.pedidos.show();
    }
    
    @FXML
    private void btnSeleccionadosEvent(MouseEvent event) {
        this.seleccionados.showSeleccionados(clientesSeleccionados);
    }

    @FXML
    private void btnPedirHoraEvent(MouseEvent event) {
        this.hora.show();
    }
    
    @FXML
    private void btnMensajeSeleccionadosEvent(MouseEvent event) {
        if (this.clientesSeleccionados.size() > 0) {
            this.mensaje.sendMessage(clientesSeleccionados);
        } else {
            ErrorAlert.show("Debe seleccionar al menos un computador");
        }
    }
    
    /**
     * actualiza las screenshots de cada computador
     */
    public synchronized void actualizarScreenShots() {
        new Thread(() -> {

            while (true) {
                
                new Thread(() -> {
                    if (this.vistas.size() >= 1) {
                        try{
                            VistasClienteController cliente = this.vistas.get(numeroDeVistaActual);
                            for (int i = 0; i < cliente.getComputadoresControllers().size(); i++) {

                                Computador computador = cliente.getComputadoresControllers().get(i).getComputador();

                                if(computador.getSocketCliente() != null /*&& computador.getEstado().get() != -1*/){
                                    Message.takeScreenShot( computador );
                                }

                            }

                        }catch(NullPointerException ex){
                            //consume
                        }
                    }
                }).start();
                
                try {
                    Thread.currentThread().sleep(1_000);
                } catch (InterruptedException ex) {
                    System.out.println("Error en actualizar screenshot " + ex.getMessage());
                }
            }
            

        }).start();

    }

}
