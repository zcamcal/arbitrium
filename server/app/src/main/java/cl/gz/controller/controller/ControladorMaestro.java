/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.controller.controller;

import cl.gz.server.dao.ComputadorDAO;
import cl.gz.server.modelo.Computador;
import cl.gz.server.operacion.Message;
import cl.gz.server.conexion.Server;
import cl.gz.server.handler.Handler;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author rednation
 */
public class ControladorMaestro {
    
    private static Scanner sc = new Scanner(System.in);
    
    private static final String RUTA_VISTAS = "fxml/";
    
    /**
     * sera utilizado para guardar la instancia del stage que se crea al momento de iniciar la aplicacion
     */
    private static Stage stage;
    
    /**
     * variable de clase que guardara la instancia de el controlador de la vista actual sobre la stage.
     */
    private static PrincipalController principalController;
    
    /**
     * Metodo para posicionar una escena sobre el stage en el cual estara montada la aplicacion.
     * @param ruteFXML ruta del archivo fxml que se desea usar para posicionar
     */
    public static void setSceneOnStage(String ruteFXML){
        FXMLLoader fxmlLoader = getFXMLLoader(ruteFXML);
        Parent parent = getParent(fxmlLoader);
        Scene scene = new Scene(parent);
        principalController = fxmlLoader.getController();
        stage.setScene(scene);
        stage.show();
        stage.setAlwaysOnTop(false);
        stage.centerOnScreen();
    }
    
    /**
     * obtiene el parent a travez de el metodo load otorgado por el fxmlLoader.
     * @param fxmlLoader de la vista que se utilizara
     * @return Parent 
     */
    public static Parent getParent(FXMLLoader fxmlLoader){
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException ex) {
            parent = null;
            System.out.println("Error en controladorMaestro, getParent, Error: " + ex.getMessage());
        }
        return parent;
    }
    
    /**
     * obtiene el controlador a travez del fxmlLoader.
     * @param fxmlLoader
     * @return 
     */
    public static Object getController(FXMLLoader fxmlLoader){
        return fxmlLoader.<Object>getController();
    }
    
    /**
     * se encarga de obtener un cargador del fxml para poder trabajar con el.
     * @param ruteFXML
     * @return 
     */
    public static FXMLLoader getFXMLLoader(String ruteFXML){
        String rute = RUTA_VISTAS + ruteFXML + ".fxml";
        URL url = ControladorMaestro.class.getClassLoader().getResource(rute);
        return new FXMLLoader(url);
    }
    
    public static Stage getStage() {
        return ControladorMaestro.stage;
    }

    public static void setStage(Stage stage) {
        ControladorMaestro.stage = stage;
        setEventOnClose();
    }
    
    public static void setAplicacionController(PrincipalController principalController){
        ControladorMaestro.principalController = principalController;
    }
    
    public static PrincipalController getAplicacionController(){
        return ControladorMaestro.principalController;
    }
    
    public static void actualizarStage(Runnable runnable){
        Platform.runLater(runnable);
    }
    
    public static void iniciarServer(){
        Handler.startServerThreads();
    }
    
    public static void setEventOnClose(){
        ControladorMaestro.stage.setOnCloseRequest((event) -> {
           System.exit(0);
        });
    }
    
}
