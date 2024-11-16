package cl.gz.server.modelo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * clase creada para ser usada con javafx y poder usar la propiedad bind del
 * mismo.
 *
 * @author Rednation
 */
public class Computador implements Comparable<Computador> {

    private SimpleStringProperty nombreComputador;

    private SimpleStringProperty usuarioComputador;

    private SimpleStringProperty numeroComputador;

    private Socket socketCliente;

    private ObjectInputStream ois;

    private ObjectOutputStream oos;

    private SimpleIntegerProperty estado;

    private SimpleObjectProperty<Image> screenShot;

    public Computador(Socket socketCliete) {
        this.socketCliente = socketCliete;
        setObjectStream(socketCliente);
        nombreComputador = new SimpleStringProperty(this, "nombreComputador");
        usuarioComputador = new SimpleStringProperty(this, "usuarioComputador");
        numeroComputador = new SimpleStringProperty(this, "numeroComputador");
        estado = new SimpleIntegerProperty(this, "estado");
        screenShot = new SimpleObjectProperty<>(this, "screenShot");
    }

    public SimpleStringProperty getNombreComputador() {
        return nombreComputador;
    }

    /**
     * recibe el nombre del computador que se esta creando.
     *
     * @param nombreComputador String
     */
    public void setNombreComputador(String nombreComputador) {
        this.nombreComputador.set(nombreComputador);
    }

    public SimpleStringProperty getUsuarioComputador() {
        return usuarioComputador;
    }

    /**
     * recibe el rut del usuario que lo esta usando.
     *
     * @param usuarioComputador String
     */
    public void setUsuarioComputador(String usuarioComputador) {
        this.usuarioComputador.set(usuarioComputador);
    }

    public SimpleStringProperty getNumeroComputador() {
        return numeroComputador;
    }

    public void setNumeroComputador(String numeroComputador) {
        this.numeroComputador.set(numeroComputador);
    }

    public Socket getSocketCliente() {
        return socketCliente;
    }

    public void setSocketCliente(Socket socketCliente) {
        this.socketCliente = socketCliente;
        setObjectStream(socketCliente);
    }

    public void setSocketClienteRepetido(Socket socketCliente) {
        this.socketCliente = socketCliente;
        try {
            this.oos = new ObjectOutputStream(socketCliente.getOutputStream());
            this.ois = new ObjectInputStream(socketCliente.getInputStream());
        } catch (IOException ex) {
            System.out.println("SocketClienteRepetido " + ex.getMessage());
        }
    }

    public SimpleIntegerProperty getEstado() {
        return estado;
    }

    /**
     * recibe el estado en el que se encuentra el computador.
     *
     * @param estado int
     */
    public void setEstado(int estado) {
        this.estado.set(estado);
    }

    public SimpleObjectProperty<Image> getScreenShot() {
        return screenShot;
    }

    /**
     * recibe la screenShot tomada al equipo.
     *
     * @param screenShot BufferedImage
     */
    public void setScreenShot(BufferedImage screenShot) {
        Image image = SwingFXUtils.toFXImage(screenShot, null);
        this.screenShot.set(image);
    }

    public ObjectInputStream getObjectInputStream() {
        return ois;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return oos;
    }
    
    public void flushOutputStream(){
        try {
            oos.flush();
        } catch (IOException ex) {
            System.out.println("Error flush " + ex.getMessage());
        }
    }
    
    public void setObjectStream(Socket cliente) {
        try {
            if (cliente != null) {
                this.oos = new ObjectOutputStream(cliente.getOutputStream());
                this.ois = new ObjectInputStream(cliente.getInputStream());
            } else {
                this.oos = null;
                this.ois = null;
            }
        } catch (IOException ex) {
            System.out.println("Error en setObjectStream " + this.nombreComputador + ", " + ex.getMessage());
        }
    }

    public void computadorDesconectado() {
        this.socketCliente = null;
        this.estado.set(0);
    }

    @Override
    public int compareTo(Computador computador) {
        return this.getNombreComputador().get().compareTo(computador.getNombreComputador().get());
    }

    @Override
    public String toString() {
        return this.nombreComputador.get() + ", estado: " + this.estado.get();
    }

}
