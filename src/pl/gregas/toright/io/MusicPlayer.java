/*
 * This file is part of toRight project.
 *
 * Author: GregAS
 */
package pl.gregas.toright.io;

import pl.gregas.toright.*;
import pl.gregas.toright.rendering.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import java.util.*;
import java.io.*;


/**
*
 */
public class MusicPlayer {
  private String path;
  private Map<String, String> sounds;
  private boolean isPlaying;
  private AudioInputStream audio;
  private Clip clip;

  public MusicPlayer() {
    sounds = new HashMap<String, String>();

  }

  public void load(String name, String path) {
    sounds.put(name, path);
  }

  public void play(String name) {
    try{
      audio = AudioSystem.getAudioInputStream(new File(sounds.get(name)));
      clip = AudioSystem.getClip();
      clip.open(audio);
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(Config.VOLUME);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
	    clip.start();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void stop() {
    if (clip.isRunning()) clip.stop();
    isPlaying = false;
  }

  public boolean isPlaying() {
    return isPlaying;
  }
  public void setPlaying(boolean bool) {
    isPlaying  = bool;
  }

}
