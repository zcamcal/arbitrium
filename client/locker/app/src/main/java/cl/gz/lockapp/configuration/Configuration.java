/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.lockapp.configuration;

/**
 *
 * @author alison
 */
public class Configuration {
    
    public static void initBasicSystemPropertys(){
        
        String timerChecker = "10000";
        System.setProperty("timerChecker", timerChecker);
        
        String numComputer = "0";
        System.setProperty("numComputer", "0");
        
        String portRMI = "";
        
    }
    
    public static void setSystemProperty(String key, String value){
        System.setProperty(key, value);
    }
    
    public static void getSystemProperty(){
        
    }
    
}
