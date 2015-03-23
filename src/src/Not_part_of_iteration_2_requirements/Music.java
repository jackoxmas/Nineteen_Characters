/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.Not_part_of_iteration_2_requirements;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

/**
 * Plays music for school project
 *
 * @author John
 */
public class Music extends Application implements Runnable {

    private ExecutorService soundPool = Executors.newFixedThreadPool(3);
    private final String punch_filename = "/Punch.mp3";
    private final String music_filename = "/Music.mp3";

    public void run() {
        Application.launch();
    }
    
    public void playAttackSound() {
        final URL punch_resource = getClass().getResource(punch_filename);
            if (punch_resource == null) {
                System.out.println("Could not find " + punch_filename);
                return;
            }
            AudioClip sound = new AudioClip(punch_resource.toString());
            Runnable soundPlay2 = new Runnable() {
                @Override
                public void run() {
                    sound.play();
                }
            };
            soundPool.execute(soundPlay2);
    }

    @Override
    public synchronized void start(Stage primaryStage) {
        try {
            Thread.sleep(30); // Pause before music starts
        } catch (Exception e) {

        }
        final URL resource = getClass().getResource(music_filename);
        if (resource == null) {
            System.out.println("Could not find " + music_filename);
            return;
        }
        AudioClip music_sound = new AudioClip(resource.toString());

        Runnable soundPlay = new Runnable() {
            @Override
            public void run() {
                music_sound.play();
            }
        };
        soundPool.execute(soundPlay);
    }
}
