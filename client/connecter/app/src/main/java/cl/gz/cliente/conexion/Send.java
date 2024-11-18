/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.cliente.conexion;

import cl.gz.cliente.model.Computer;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author rednation
 */
public class Send {
    
    private static Socket clientSocket;
    
    private static ObjectOutputStream dout;
    
    public static void sendImageToServer(byte[] screenShot){
        try {
            
            dout.writeUnshared(screenShot);
            dout.flush();
            dout.reset();
            
        } catch (IOException ex) {
            System.out.println("Error al enviar imagen al servidor, " + ex.getMessage());
        }
    }
    
    public static void sendBloqueoExitoso(boolean bloqueoExitoso){
        try {
            dout.writeBoolean(bloqueoExitoso);
            dout.flush();
            Computer.getInstance().setEstado(-1);
        } catch (IOException ex) {
            System.out.println("Error al enviar si el bloqueo fue exitoso al servidor, " + ex.getMessage());
        }
        
    }
    
    public static void sendDesbloqueoExitoso(boolean desbloqueoExitoso){
        try {
            dout.writeBoolean(desbloqueoExitoso);
            dout.flush();
            Computer.getInstance().setEstado(1);
        } catch (IOException ex) {
            System.out.println("Error al enviar si el bloqueo fue exitoso al servidor, " + ex.getMessage());
        }
        
    }
    
    public static void sendState(){
        try {
            dout.writeUnshared( Computer.getInstance().getEstado() );
            dout.flush();
            dout.reset();
        } catch (IOException ex) {
            System.out.println("Error al enviar si el bloqueo fue exitoso al servidor, " + ex.getMessage());
        }
    }
    
    static void setDataOutputStream(Socket clientSocket){
        try {
            Send.clientSocket = clientSocket;
            Send.dout = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error en setDataOutputStream " + ex.getMessage());
        }
    }
    
}
