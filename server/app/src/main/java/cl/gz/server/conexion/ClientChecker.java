/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.server.conexion;

import cl.gz.server.dao.ComputadorDAO;
import cl.gz.server.modelo.Computador;
import cl.gz.server.operacion.Message;
import java.util.List;

/**
 *
 * @author Rednation
 */
public class ClientChecker implements Runnable{

    @Override
    public void run() {

        List<Computador> computadores = ComputadorDAO.getComputadores();
        while (true) {
            for (int i = 0; i < computadores.size(); i++) {
                Message.askState(computadores.get(i));
            }
            try {
                int reconnectTime = 10_000;
                Thread.sleep(reconnectTime);
            } catch (InterruptedException ex) {
                System.out.println("Error en clase comprobarConexion, " + ex.getMessage());
            }
        }

    }
    
}
