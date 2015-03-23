/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.Not_part_of_iteration_2_requirements;

import java.net.URL;

/*
import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
*/
/**
 * Plays music for school project
 *
 * @author John [Music code taken from
 * http://www.java2s.com/Code/Java/JavaFX/Playmp3file.htm]
 */
public class Music /*extends Application*/ implements Runnable {

    public void run() {
        //Application.launch();
    }
/*
    @Override
    public void start(Stage primaryStage) {
        try {
            Thread.sleep(30);
        } catch (Exception e) {

        }
        URL location = Music.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println("Put music file in:" + location.getFile());
        final URL resource = getClass().getResource("/Music.mp3");
        AudioClip sound = new AudioClip(resource.toString());
        sound.play();
    }
    */
}
