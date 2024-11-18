/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.cliente.conexion;

import cl.gz.rmi.Cliente;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import cl.gz.cliente.configuration.Configuration;

/**
 *
 * @author rednation
 */
public class Listen extends Thread {

    private Socket clientSocket;
    
    private ObjectInputStream din;

    public Listen(String threadName, Socket clientSocket) {
        this.setName(threadName);
        this.clientSocket = clientSocket;
        setDataInputStream(clientSocket);
    }

    @Override
    public void run() {
        
        while (true) {
            try {
                String accion = din.readUTF();
                switch(accion){
                    case "bloquear": Cliente.lockComputer();
                    break;
                    case "desbloquear": Cliente.unlockComputer();
                    break;
                    case "screenShot": Cliente.sendScreenShot();
                    break;
                    case "state": Send.sendState();
                    break;
                    case "mensaje": Cliente.sendMessageToClient(din.readUTF());
                    break;
                    case "changeConfig": Configuration.getInstance().setPropierty(din.readUTF(), din.readUTF());
                    break;
                    case "updateConfig": Configuration.getInstance().updatePropertiesToFile();
                    break;
                    case "getProperty": Configuration.getInstance().getProperty(din.readUTF());
                    break;
                }
            } catch (IOException | NullPointerException ex) {
                Conexion.reconectar();
                break;
            }

        }

    }
    
    private void setDataInputStream(Socket clientSocket){
        try {
            this.din = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Error en setDataInputStream " + ex.getMessage());
        }
    }

}
