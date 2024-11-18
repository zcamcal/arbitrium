/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author alison
 */
public interface Lockeable extends Remote {
    
    boolean unlockComputer() throws RemoteException;
    
    boolean lockComputer() throws RemoteException;
    
    boolean getEstadoComputer() throws RemoteException;
    
}
