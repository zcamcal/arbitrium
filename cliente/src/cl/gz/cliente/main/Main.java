package cl.gz.cliente.main;

import cl.gz.cliente.conexion.Checker;
import cl.gz.cliente.conexion.Conexion;
import cl.gz.cliente.configuration.Configuration;
import cl.gz.cliente.sistema.IdleTime;
import cl.gz.rmi.Cliente;
import cl.gz.cliente.model.Computer;

/**
 *
 * @author Rednation
 */
public class Main {
    
    public static void main(String[] args){
        
        Configuration.getInstance().readAllProperties();
        Computer.getInstance();
        
        Cliente.setInterface();
        
        new Thread(new Checker()).start();
        
        new Thread(new IdleTime()).start();
        
        String lock = Configuration.getInstance().getProperty("lock");
        
        if(lock.equals("1")){
            Cliente.internLockComputer();
        }
        
        new Conexion("Conexion");
        
    }
    
}
