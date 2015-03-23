/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
/**
 * Plays music for school project
 * @author John [Music code taken from http://www.java2s.com/Code/Java/JavaFX/Playmp3file.htm]
 */
public class Music extends Application implements Runnable {

  public void run() {
    launch();
  }

  @Override
  public void start(Stage primaryStage) {
    final URL resource = getClass().getResource("/Music.mp3");
    final Media media = new Media(resource.toString());
    final MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.play();
  }
}
