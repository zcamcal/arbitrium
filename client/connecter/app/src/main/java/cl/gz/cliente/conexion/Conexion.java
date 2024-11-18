/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.cliente.conexion;

import cl.gz.cliente.configuration.Configuration;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Zachariel Camacho Canales
 */
public class Conexion extends Thread{
    
    /**
     * clientSocket permitira generar la conexion al servidor.
     */
    private static Socket clientSocket;
    
    /**
     * Es usado momentaneamente ya que luego se remplazara por una lectura a 
     * un archivo de configuracion que entregara la ip y el puerto.
     * @deprecated 
     */
    private static Scanner sc = new Scanner(System.in);
    
    /**
     * clase extiende a thread, se encargara de mandar datos al servidor.
     */
    private static Send send;
    
    /**
     * clase que extiende a Thread, se encarga de escuchar datos del servidor.
     */
    private static Listen listen;
    
    /**
     * ip que se usa para conectarse al seervidor.
     */
    private static String ip;
    
    private static ServerSocket singletonSocket;
    
    /**
     * puerto usado por el servidor.
     */
    private static int puerto;
    
    public Conexion(String nombreThread){
        initSingleton();
        this.setName(nombreThread);
        iniciarConexion();
    }
    
    public static void initSingleton(){
        int SINGLETON = Integer.parseInt(Configuration.getInstance().getProperty("SINGLETON"));
        try {
            Conexion.singletonSocket = new ServerSocket(SINGLETON);
        } catch (IOException ex) {
            System.out.println("Solo puede iniciar una instancia de cliente");
            System.exit(0);
        }
    }
    
    public static void iniciarConexion(){
        
        ip = "127.0.0.1";
        puerto = 9090;
        
        conectar(ip, puerto);
        
    }
    
    /**
     * permite conectar al servidor utilizando el socket entregado, ademas instancia la clase Listen.
     * @param ip del servidor a conectar
     * @param puerto del servidor a conectar
     */
    public static void conectar(String ip, int puerto){
        
        while(true){
            try {
                //TODO: si no hay servidor, que el cliente siga intentando hasta conectar.
                Conexion.clientSocket = new Socket(ip, puerto);
                
                Checker.setSocket(clientSocket);
                
                //Enviamos el estado actual
                Send.setDataOutputStream(Conexion.clientSocket);

                listen = new Listen("listen", clientSocket);
                listen.start();
                
                break;
            } catch (IOException ex) {
                System.out.println("Error al crear el socket " + ex.getMessage());
                try {
                    Thread.currentThread().sleep(10000);
                } catch (InterruptedException ex1) {
                    System.out.println("Error al dormir el hilo dentro del metodo conectar");
                }
                desconectar();
            }
        }
        
    }
    
    public static void reconectar(){
        try {
            Conexion.clientSocket.close();
        } catch (IOException ex) {
            System.out.println("error en reconectar " + ex);
        }
        conectar(ip, puerto);
    }
    
    public static void desconectar(){
        try {
            if(Conexion.clientSocket != null){
                Conexion.clientSocket.close();
            }
        } catch (IOException ex) {
            System.out.println("Error al desconectar: " + ex.getMessage());
        }
    }
    
    public static Listen getListen(){
        return Conexion.listen;
    }
    
    synchronized static Socket getSocket(){
        return Conexion.clientSocket;
    }
    
}
