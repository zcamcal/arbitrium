/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.lockapp.main;

import cl.gz.lockapp.controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import cl.gz.lockapp.configuration.Configuration;

/**
 *
 * @author rednation
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        Configuration.initBasicSystemPropertys();
        
        Controller.setLockInstance(primaryStage);
        Controller.startLockApp();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
