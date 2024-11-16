/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.cliente.configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author alison
 */
public class Configuration {

    private static Configuration INSTANCE;

    private Properties properties;

    private final String ROUTE = "C:\\Windows\\System32\\TTYRES.INI";
    private final String DEFAULT_ROUTE = "cl/gz/cliente/properties/config.properties";

    private Configuration() {
        this.properties = new Properties();
    }

    public static synchronized Configuration getInstance() {
        if (Configuration.INSTANCE == null) {
            Configuration.INSTANCE = new Configuration();
        }
        return Configuration.INSTANCE;
    }

    public void readAllProperties() {
        InputStream is;
        try {
            this.properties.load(new FileReader(ROUTE));
        } catch (FileNotFoundException ex) {
            loadDefault();
        } catch (IOException ex) {
            System.out.println("Error en readAllProperties, Clase: ReadProperties"
                    + "Error en IOException: " + ex.getMessage());
        }
        System.out.println(properties.getProperty("numberComputer"));
    }

    private void loadDefault() {
        Properties defaultProperties = new Properties();
        try {
            defaultProperties.load(getClass().getClassLoader().getResourceAsStream(DEFAULT_ROUTE));
            this.properties = new Properties(defaultProperties);
            updatePropertiesToFile();
        } catch (IOException ex) {
            System.out.println("Error en readAllProperties, Clase: ReadProperties"
                    + "Error en IOException: " + ex.getMessage());
        }
    }

    public void setPropierty(String key, String value) {
        this.properties.setProperty(key, value);
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    public void updatePropertiesToFile() {
        try {
            this.properties.store(new FileWriter(ROUTE), "OEMDriver");
        } catch (FileNotFoundException ex) {
            System.out.println("Error en updatePropertiesToFile, Clase: CONFIGURATION"
                    + "Error en FileNotFound: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error en updatePropertiesToFile, Clase: CONFIGURATION"
                    + "Error en IOException: " + ex.getMessage());
        }
    }
    
}
