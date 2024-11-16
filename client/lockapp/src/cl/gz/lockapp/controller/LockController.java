/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.lockapp.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author Giuseppe-PC
 */
public class LockController implements Initializable {

    @FXML
    private MediaView mediaView;
    
    private File fileMP4;
    
    private final String ROUTE = "cl/gz/lockapp/resources/ralph.mp4";
    
    private MediaPlayer mediaPlayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void init(){
        Media media = new Media(getClass().getClassLoader().getResource(ROUTE).toExternalForm());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaView.setMediaPlayer(mediaPlayer);
        setMaximize();
        setEventLoop();
        mediaPlayer.play();
    }

    @FXML
    private void onErrorPutImage(MediaErrorEvent event) {
        System.out.println("nose");
    }
    
    private void setEventLoop(){
        this.mediaPlayer.setAutoPlay(true);
        this.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        this.mediaPlayer.setOnEndOfMedia(() -> {
            this.mediaPlayer.play();
        });
    }
    
    private void setMaximize(){
        this.mediaView.setFitHeight(766);
        this.mediaView.setFitWidth(1360);
    }
    
}
