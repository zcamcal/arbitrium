/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.lockapp.controller;

import cl.gz.lockapp.operation.Lock;
import cl.gz.lockapp.operation.Message;
import cl.gz.lockapp.operation.ScreenShot;
import cl.gz.rmi.Server;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author alison
 */
public class Controller {
    
    private static Lock lock;
    
    private static Message message;
    
    private static ScreenShot screenShot;
    
    private static final String ROUTE_VIEWS = "fxml/";
    
    public static void setLockInstance(Stage primaryStage){
        Controller.lock = Lock.getInstance(primaryStage);
    }
    
    public static Lock getLockInstance(){
        return lock;
    }
    
    public static FXMLLoader getFXML(String fxml){
        FXMLLoader fxmlLoader = new FXMLLoader(new Controller().getClass().getClassLoader().getResource(ROUTE_VIEWS + fxml + ".fxml"));
        return fxmlLoader;
    }
    
    public static Parent getParent(FXMLLoader fxmlLoader){
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException ex) {
            parent = null;
            System.out.println("Class Controller, Error in getParent: " + ex.getMessage());
        }
        return parent;
    }
    
    public static Object getController(FXMLLoader fxmlLoader){
        Object object = fxmlLoader.getController();
        return object;
    }
    
    public static void startLockApp(){
        
        Controller.message = Message.getInstance();
        
        Controller.screenShot = ScreenShot.getInstance();
        
        Server.start(Controller.lock, Controller.message, Controller.screenShot);
        
    }
    
}
