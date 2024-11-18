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
 * @author Rednation
 */
public interface Messageable extends Remote {
    
    void showMessage(String message) throws RemoteException;
    
}
