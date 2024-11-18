/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.rmi;

import cl.gz.cliente.conexion.Send;
import cl.gz.cliente.model.Computer;
import cl.gz.cliente.sistema.WindowsAPI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Cliente {

    private static final String IP = "127.0.0.1"; // Puedes cambiar a localhost
    private static final int PUERTO = 1100; //Si cambias aquí el puerto, recuerda cambiarlo en el servidor
    
    private static Lockeable lockeable;
    
    private static Messageable messageable;
    
    private static Spyable spyable;
    
    public static void setInterface(){
        try {
            Registry registry = LocateRegistry.getRegistry(IP, PUERTO);
            Lockeable interfaz = (Lockeable) registry.lookup("Calculadora"); //Buscar en el registro...
            Cliente.lockeable = interfaz;
            Messageable messageable = (Messageable) registry.lookup("Cientifica"); //Buscar en el registro...
            Cliente.messageable = messageable;
            Spyable spyable = (Spyable) registry.lookup("Estandar"); //Buscar en el registro...
            Cliente.spyable = spyable;
        } catch (RemoteException ex) {
            System.out.println("Cliente, setInterface Error: " + ex.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("Cliente, setInterface Error: " + ex.getMessage());
        }
    }
    
    public static void lockComputer(){
        boolean isLocked = false;
        try {
            WindowsAPI.blockWindowsKey();
            isLocked = Cliente.lockeable.lockComputer();
        } catch (RemoteException | NullPointerException ex) {
            isLocked = false;
            setInterface();
            System.out.println("Cliente, lockComputer error: " + ex.getMessage());
        }finally{
            Send.sendBloqueoExitoso(isLocked);
        }
    }
    
    public static void internLockComputer(){
        boolean isLocked = false;
        try {
            WindowsAPI.blockWindowsKey();
            isLocked = Cliente.lockeable.lockComputer();
            Computer.getInstance().setEstado(-1);
        } catch (RemoteException | NullPointerException ex) {
            isLocked = false;
            setInterface();
            System.out.println("Cliente, lockComputer error: " + ex.getMessage());
        }finally{
//            Conexion.reconectar();
        }
    }

    public static void unlockComputer(){
        boolean isUnlocked = false;
        try {
            WindowsAPI.unblockWindowsKey();
            isUnlocked = Cliente.lockeable.unlockComputer();
        } catch (RemoteException | NullPointerException ex) {
            isUnlocked = false;
            setInterface();
            System.out.println("Cliente, unlockComputer error: " + ex.getMessage());
        }finally{
            Send.sendDesbloqueoExitoso(isUnlocked);
        }
    }
    
    public static boolean getStateOfComputer(){
        boolean isLocked;
        try {
            isLocked = Cliente.lockeable.getEstadoComputer();
            Computer.getInstance().setEstado( isLocked == true ? -1 : 1 );
        } catch (RemoteException | NullPointerException ex) {
            setInterface();
            isLocked = false;
            System.out.println("Cliente, getStateOfComputer error: " + ex.getMessage());
        }
        return isLocked;
    }
    
    public static boolean sendMessageToClient(String message){
        
        try {
            Cliente.messageable.showMessage(message);
        } catch (RemoteException | NullPointerException ex) {
            setInterface();
            System.out.println("Cliente, sendMessageToClient Error: " + ex.getMessage());
        }
        return true;
    }  
    
    public static boolean sendScreenShot(){
        boolean screenshot;
        try {
            byte[] imageArray = Cliente.spyable.takeScreenShot();
            Send.sendImageToServer(imageArray);
            screenshot = true;
        } catch (RemoteException | NullPointerException ex) {
            screenshot = false;
            setInterface();
            System.out.println("Error en metodo: sendScreenShot, CLASE Cliente"
                    + "Error " + ex.getMessage());
            Send.sendImageToServer(null);
        }
        
        return screenshot;
    }
    
    public static void reconnectInterface(){
        
    }
    
}
