package cl.gz.lockapp.operation;

import static cl.gz.lockapp.controller.Controller.getController;
import static cl.gz.lockapp.controller.Controller.getFXML;
import static cl.gz.lockapp.controller.Controller.getParent;
import cl.gz.lockapp.controller.LockController;
import cl.gz.lockapp.sistema.WindowsAPI;
import cl.gz.rmi.Lockeable;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * clase encargada de manejar todo en relacion al bloqueo del computador.
 *
 * @author rednation
 */
public class Lock implements Lockeable{

    private Stage primaryStage;
    
    private Parent lockParent;
    
    private LockController lockController;
    
    private static Lock instance;
    
    private Lock(Stage stage){
        setStage(stage);
        buildLock();
        startConfig();
    }
    
    public static Lock getInstance(Stage stage){
        if(instance == null){
            instance = new Lock(stage);
        }
        return instance;
    }
    
    private void buildLock(){
        
        FXMLLoader fxmlLoader = getFXML("lock");
        this.lockParent = getParent(fxmlLoader);
        this.lockController = (LockController) getController(fxmlLoader);
        
        Scene scene = new Scene(this.lockParent);
        
        primaryStage.setScene(scene);
        
        this.lockController.init();
        
    }
    
    @Override
    public boolean unlockComputer() {
        final boolean unlocked;
        if (this.primaryStage.isShowing()) {
            
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    
                    WindowsAPI.unblockWindowsKey();
                    //cerramos la aplicacion visual.
                    Lock.this.primaryStage.close();
                    
                }
            });
        } else {
            
        }
        return true;
    }
    
    @Override
    public boolean lockComputer() {
        if (!this.primaryStage.isShowing()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    
                    WindowsAPI.blockWindowsKey();
                    Lock.this.primaryStage.show();
                    
                }
            });
        } else {
            
        }
        return true;
    }
    
    /**
     * getEstado.
     * @return true if the state of this computer is locked.
     */
    @Override
    public boolean getEstadoComputer(){
        return this.primaryStage.isShowing();
    }
    
    private void setStage(Stage stage){
        this.primaryStage = stage;
    }
    
    public Stage getStage(){
        return this.primaryStage;
    }
    
    public LockController getLockController(){
        return this.lockController;
    }

    private void startConfig(){
        //esto impide que si se cierra el stage, este automaticamente se muera.
        Platform.setImplicitExit(false);
        
        //MAS QUE OBVIO NO ES NO??
        this.primaryStage.centerOnScreen();

        //esto permite que el stage se posicione sobre toda la pantalla inclusive la barra de tareas
        //y ademas elimine la barra propia de la ventana, eso permite que no la cierren facilmente.
        this.primaryStage.initStyle(StageStyle.UNDECORATED);

        //expandimos la pantalla al maximo.
        this.primaryStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        this.primaryStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        this.primaryStage.setMaximized(true);

        //impedimos que por alguna razon puedan achicar el tamaño.
        this.primaryStage.setResizable(false);

        //dejaremos la pantalla seleccionada por defecto
        this.primaryStage.requestFocus();

        //poisicionamos la ventana siempre sobre todo.
        this.primaryStage.setAlwaysOnTop(true);
        
        this.primaryStage.requestFocus();
        
        //le damos un evento ante las peticiones de cierre para que en caso
        //de que ocurra un evento este sea consumido sin consecuencias.
        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });

        //Por el momento funciona y escucha los cambios entre la perdida del focus o no.
        this.primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean onHidden, Boolean onShown) {
                //en cualquier caso pediremos que sea la aplicacion que se este escuchando.
                if(onHidden){
                    Lock.this.primaryStage.requestFocus();
                }else{
                    Lock.this.primaryStage.requestFocus();
                }
            }
        });
        
    }
    
}
