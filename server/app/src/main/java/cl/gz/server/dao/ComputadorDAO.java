/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.server.dao;

import cl.gz.server.modelo.Computador;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author rednation
 */
public class ComputadorDAO {
//    private static List<Computador> computadores = new ArrayList<>();
    private static ObservableList<Computador> computadores = FXCollections.observableArrayList();

    public static synchronized ObservableList<Computador> getComputadores() {
        ordenarLista();
        return computadores;
    }

    public static synchronized void addComputador(Computador computador) {
        Computador computadorAntiguo = isDuplicate(computador);
        if( computadorAntiguo == null ){
            computadores.add(computador);
        }else{
//            computadorAntiguo.computadorDesconectado();
//            computadores.remove(computadorAntiguo);
//            computadores.add(computador);
            computadorAntiguo.setSocketClienteRepetido(computador.getSocketCliente());
            computadorAntiguo.setEstado( computador.getEstado().get() );
//            computadorAntiguo.setObjectStream(computador.getSocketCliente());
        }
        ordenarLista();
    }
    
    static synchronized void ordenarLista(){
        Collections.sort(computadores);
    }
    
    static synchronized Computador isDuplicate(Computador computador){
        Computador computadorAntiguo = null;
        
        //Modificar esto ya que podrian haber dos computadores con el mismo nombre de red quizas
        //pero ip distintas
        for(Computador compu: getComputadores()){
            if(compu.compareTo(computador) == 0){
                computadorAntiguo = compu;
                break;
            }
        }
        
        return computadorAntiguo;
    }
    
    public static synchronized Computador find(String nombreComputador){
        Computador computadorAntiguo = null;
        
        for (int i = 0; i < getComputadores().size(); i++) {
            Computador computador = getComputadores().get(i);
            
            if(computador.getNombreComputador().get().equals(nombreComputador) ){
                computadorAntiguo = computador;
            }
            
        }
        
        return computadorAntiguo;
    }
}
