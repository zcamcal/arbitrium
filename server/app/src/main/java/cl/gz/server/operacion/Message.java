/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.server.operacion;

import cl.gz.server.modelo.Computador;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Rednation
 */
public class Message {

    /**
     * bloquea el equipo cliente.
     *
     * @param computador para obtener sockets.
     * @return boolean true si es que el equipo cliente se bloqueo con exito.
     */
    public static boolean bloquear(Computador computador) {
        synchronized (computador) {
            boolean bloqueadoExitoso = false;
            try {
                if (computador.getSocketCliente() != null) {
                    
                    computador.getObjectOutputStream().writeUTF("bloquear");
                    computador.flushOutputStream();

                    //retorna true en caso de que el cliente se haya bloqueado con exito
                    bloqueadoExitoso = computador.getObjectInputStream().readBoolean()/*LEER RAZON COMENTADA EN EL METODO DESBLOQUEAR*/;
                } else {
                    //Error el socket es nulo
                }

            } catch (IOException ex) {
                System.out.println("sendMessageTo " + computador + ", error: " + ex.getMessage());
            }
            return bloqueadoExitoso;
        }
    }

    /**
     * desbloquea el equipo cliente
     *
     * @param computador
     * @return 
     */
    public static boolean desbloquear(Computador computador) {
        synchronized (computador) {
            boolean desbloqueadoExitoso = false;
            try {
                if (computador.getSocketCliente() != null) {
                    
                    computador.getObjectOutputStream().writeUTF("desbloquear");
                    computador.flushOutputStream();

                    //retorna true en caso de que el cliente se haya bloqueado con exito
                    desbloqueadoExitoso = computador.getObjectInputStream().readBoolean()/* aca se deberia recibir si fue correcto el desbloqueo*/;
                } else {
                    //Error el socket es nulo
                }

            } catch (IOException ex) {
                System.out.println("sendMessageTo " + computador + ", error: " + ex.getMessage());
            }
            return desbloqueadoExitoso;
        }
    }

    /**
     * mandar mensajes mensaje
     *
     * @param computador para mandar el mensaje
     * @param message que se requiera enviar a los computadores
     */
    public static void sendMessage(Computador computador, String message) {
        synchronized (computador) {
            try {
                if (computador.getSocketCliente() != null) {
                    
                    computador.getObjectOutputStream().writeUTF("mensaje");
                    computador.flushOutputStream();

                    computador.getObjectOutputStream().writeUTF(message);
                    computador.flushOutputStream();
                    
                } else {
                    //Error el socket es nulo
                }
            } catch (IOException ex) {
                System.out.println("sendMessageTo " + computador + ", error: " + ex.getMessage());
            }
        }
    }

    /**
     * manda una solicitud que tendra de vuelta la screenShot del cliente
     *
     * @param computador
     */
    public static void takeScreenShot(Computador computador) {

        synchronized (computador) {
            try {

                if (computador.getSocketCliente() != null) {

                    computador.getObjectOutputStream().writeUTF("screenShot");
                    computador.flushOutputStream();

                    try {
                        
                        byte[] screenshot = (byte[]) computador.getObjectInputStream().readUnshared();
                        ByteArrayInputStream bais = new ByteArrayInputStream(screenshot);
                        BufferedImage img = ImageIO.read(bais);
                        computador.setScreenShot(img);
                        bais.reset();
                        bais.close();
                        img.flush();
                        img = null;
                        screenshot = null;
                        
                    } catch (ClassNotFoundException ex) {
                        System.out.println("error no se que");
                    }
                } else {
                    //Error el socket del cliente es nulo
                }

            } catch (IOException ex) {
                System.out.println("takeScreenShot " + computador + ", error: " + ex);
                computador.computadorDesconectado();
            }
        }

    }

    /**
     * preguntar el estado
     *
     * @param computador para preguntar el estado
     * @return estado 0 if error happen.
     */
    public static int askState(Computador computador) {
        synchronized (computador) {
            int estado;
            try {
                if (computador.getSocketCliente() != null) {

                    computador.getObjectOutputStream().writeUTF("state");
                    computador.flushOutputStream();

                    estado = ((Boolean)computador.getObjectInputStream().readUnshared()) == true ? -1 : 1;
                    
                } else {
                    estado = 0;
                    //Error el socket es nulo
                }
            } catch (IOException | NullPointerException | ClassNotFoundException ex) {
                estado = 0;
                System.out.println("askState " + computador + ", error: " + ex.getMessage());
                //El equipo perdio conexion por lo cual su estado es 0 = apagado.
                    computador.setEstado(0);

                    //el equipo perdio conexion por lo cual el Socket muere.
                    computador.setSocketCliente(null);
            }
            return estado;
        }
    }

}
