/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.server.handler;

import cl.gz.server.conexion.ClientChecker;
import cl.gz.server.conexion.ClientScreenShots;
import cl.gz.server.conexion.Server;

/**
 *
 * @author Rednation
 */
public class Handler {
    
    public static Thread server;
    
    public static Thread clientChecker;
    
    public static Thread clientScreenShot;
    public static ClientScreenShots client;
    
    public static void startServerThreads(){
        
        Server server = new Server();
        Handler.server = new Thread(server);
        
        ClientChecker clientCheker = new ClientChecker();
        Handler.clientChecker = new Thread(clientCheker);
        
        ClientScreenShots clientScreenShot = new ClientScreenShots();
        client = clientScreenShot;
        
        Handler.clientScreenShot = new Thread(clientScreenShot);
        
        Handler.server.start();
//        Handler.clientChecker.start();
        
    }
    
    public static void stopService(){
        Handler.server.interrupt();
        Handler.clientChecker.interrupt();
        Handler.clientScreenShot.interrupt();
    }
    
}
