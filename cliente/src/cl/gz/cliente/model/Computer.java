/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.cliente.model;

import cl.gz.cliente.configuration.Configuration;

/**
 *
 * @author Rednation
 */
public class Computer {
    
    private static Computer INSTANCE;
    
    /**
     * -1 BLOQUEADO
     * 1 DESBLOQUEADO
     */
    private int estado;
    
    private int numberComputer;
    
    private String hostName;
    
    private Computer(){
        int initComputerState = Integer.parseInt(Configuration.getInstance().getProperty("initComputerState"));
        this.estado = initComputerState;
        int numberComputer = Integer.parseInt(Configuration.getInstance().getProperty("numberComputer"));
        this.numberComputer = numberComputer;
        String hostName = Configuration.getInstance().getProperty("hostName");
        this.hostName = hostName;
    }
    
    public synchronized static Computer getInstance(){
        if(Computer.INSTANCE == null){
            Computer.INSTANCE = new Computer();
        }
        return Computer.INSTANCE;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getNumberComputer() {
        return numberComputer;
    }

    public void setNumberComputer(int numberComputer) {
        this.numberComputer = numberComputer;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    
}
