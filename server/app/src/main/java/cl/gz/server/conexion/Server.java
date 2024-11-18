/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.server.conexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Rednation
 */
public class Server implements Runnable {

    private ServerSocket socketServidor;
    
    private int puerto;
    
    @Override
    public void run() {
        
        try {
            socketServidor = new ServerSocket(9090);
        } catch (IOException ex) {
            System.out.println("error en la creacion del server socket, error: " + ex.getMessage());
        }
        
        //variable que sera instanciada dentro del while cada vez que se reciba una nueva conexion.
        Listener listener;
        
        //while en true para que siempre se ejecute su bloque interior y asi poder escuchar peticiones entrantes.
        while(true){
            
            try {
                //se instancia el socket atravez de la peticion al servidor
                Socket socketCliente = socketServidor.accept();
                
                //se instancia un nuevo Listener y se le pasa como argumento el socket que se conecto
                listener = new Listener(socketCliente);
                //se genera un nuevo hilo que ejecute la clase listener, es posible gracias a que listener implementa runnable
                new Thread(listener).start();
                
            } catch (IOException ex) {
                System.out.println("error escuchando la conexion entrante, error: " + ex.getMessage());
            }
            
        }
        
    }
    
    
    
}
