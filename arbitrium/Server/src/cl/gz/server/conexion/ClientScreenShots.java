/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.server.conexion;

import cl.gz.server.modelo.Computador;
import cl.gz.server.operacion.Message;
import java.util.List;

/**
 *
 * @author Rednation
 */
public class ClientScreenShots implements Runnable {
    
    private List<Computador> computadores;

    @Override
    public void run() {
        
        while(true){
            System.out.println("screenshot hilo");
            if(computadores != null){
                for (int i = 0; i < computadores.size(); i++) {
                
                    Message.takeScreenShot( computadores.get(i) );
                
                }
            }
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException ex) {
                System.out.println("Error en clientScreenShot: " + ex.getMessage());
            }
        }
        
    }
    
    public void takeScreenShot(Computador computador){
        
    }
    
    public void setComputadores(List<Computador> computadores ){
        this.computadores.clear();
        this.computadores = computadores;
    }
    
}
