/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gz.lockapp.operation;

import cl.gz.rmi.Spyable;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Rednation
 */
public class ScreenShot implements Spyable {
    
    private static ScreenShot instance;
    
    private ScreenShot(){
        
    }
    
    public static ScreenShot getInstance(){
        if(ScreenShot.instance == null){
            ScreenShot.instance = new ScreenShot();
        }
        return ScreenShot.instance;
    }

    @Override
    public byte[] takeScreenShot() {
        byte[] imageArray;
        Robot robot;
        try {
            robot = new Robot();
            BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

            try {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                ImageIO.write(screenShot, "JPG", baos);

                imageArray = baos.toByteArray();
                baos.close();

            } catch (IOException ex) {
                System.out.println("Error al enviar imagen al servidor, " + ex.getMessage());
                imageArray = null;
            }

        } catch (AWTException ex) {
            System.out.println("Error en takeScreenShot, " + ex.getMessage());
            imageArray = null;
        }
        
        return imageArray;
    }

}
