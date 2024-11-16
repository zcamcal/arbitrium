/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.server.conexion;

import cl.gz.server.dao.ComputadorDAO;
import cl.gz.server.modelo.Computador;
import cl.gz.server.operacion.Message;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Rednation
 */
public class Listener implements Runnable {

    private Socket socketCliente;

    private InetAddress inetAddress;

    Listener(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    @Override
    public void run() {

        //obtiene la instancia a travez del socket cliente
        this.inetAddress = this.socketCliente.getInetAddress();

        /*
            Se obtiene el nombre del computador y luego se usa un subString sobre 
            el fqdn obtenido para quedar solo con el nombre del equipo
         */
//        String nombreComputador = this.inetAddress.getHostName().substring(0, this.inetAddress.getHostName().indexOf("."));
        String nombreComputador = this.inetAddress.getHostName();

        //int numPC;
        
        /*if (nombreComputador.matches("vre_blt02[0-9][0-9]")) {
            numPC = (Integer.parseInt(nombreComputador.substring(nombreComputador.length() - 3, nombreComputador.length() - 1)) + 58);
        } else {
            numPC = Integer.parseInt(nombreComputador.substring(nombreComputador.length() - 3, nombreComputador.length() - 1));
        }*/

        Computador computadorAntiguo = null;
        
        if( (computadorAntiguo = ComputadorDAO.find(nombreComputador)) == null ){
            Computador computador = new Computador(this.socketCliente);

            computador.setNombreComputador(nombreComputador);
    //        computador.setNumeroComputador( String.valueOf(numPC) );
    //        System.out.println("numpc " + numPC + ", string " + String.valueOf(numPC));

            int estado = Message.askState(computador);

            computador.setEstado(estado);

            ComputadorDAO.addComputador(computador);

        }else{
            computadorAntiguo.setSocketClienteRepetido(socketCliente);
            int estado = Message.askState(computadorAntiguo);
            computadorAntiguo.setEstado(estado);
        }

    }

}
