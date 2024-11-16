/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    private static final int PUERTO = 1100; //Si cambias aquí el puerto, recuerda cambiarlo en el cliente
    
    public static void start(Lockeable showable, Messageable messageable, Spyable spyable){
        Remote remote;
        try {
            Registry registry = LocateRegistry.createRegistry(PUERTO);
            
            remote = UnicastRemoteObject.exportObject(showable, 0);
            registry.bind("Calculadora", remote); // Registrar calculadora
            
            remote = UnicastRemoteObject.exportObject(messageable, 0);
            registry.bind("Cientifica", remote); // Registrar Cientifica
            
            remote = UnicastRemoteObject.exportObject(spyable, 0);
            registry.bind("Estandar", remote); // Registrar Cientifica
        } catch (RemoteException ex) {
            System.out.println("Server, Error in remoteException: " + ex.getMessage());
            System.exit(0);
        } catch (AlreadyBoundException ex) {
            System.out.println("Server, Error in AlreadyBoundException: " + ex.getMessage());
        }
    }

}
