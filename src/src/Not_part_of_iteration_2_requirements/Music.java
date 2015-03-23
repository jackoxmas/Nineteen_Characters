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
 * @author John-Michael Reed
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

    private final String binding_filename = "/Binding_Sound.mp3";
    private final String talking_filename = "/Greeting.mp3";
    private final String spell_filename = "/Spell_Sound.mp3";

    public void playBindSound() {
        final URL bind_resource = getClass().getResource(binding_filename);
        if (bind_resource == null) {
            System.out.println("Could not find " + binding_filename);
            return;
        }
        AudioClip sound = new AudioClip(bind_resource.toString());
        Runnable soundPlay2 = new Runnable() {
            @Override
            public void run() {
                sound.play();
            }
        };
        soundPool.execute(soundPlay2);
    }

    public void playSpellSound() {
        final URL resource = getClass().getResource(spell_filename);
        if (resource == null) {
            System.out.println("Could not find " + spell_filename);
            return;
        }
        AudioClip sound = new AudioClip(resource.toString());
        Runnable soundPlay2 = new Runnable() {
            @Override
            public void run() {
                sound.play();
            }
        };
        soundPool.execute(soundPlay2);
    }

    public void playTalkingSound() {
        final URL resource = getClass().getResource(talking_filename);
        if (resource == null) {
            System.out.println("Could not find " + talking_filename);
            return;
        }
        AudioClip sound = new AudioClip(resource.toString());
        Runnable soundPlay2 = new Runnable() {
            @Override
            public void run() {
                sound.play();
            }
        };
        soundPool.execute(soundPlay2);
    }

    private final String drop_filename = "/Drop_Item.mp3";

    public void playDropItemSound() {
        final URL resource = getClass().getResource(drop_filename);
        if (resource == null) {
            System.out.println("Could not find " + drop_filename);
            return;
        }
        AudioClip sound = new AudioClip(resource.toString());
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
