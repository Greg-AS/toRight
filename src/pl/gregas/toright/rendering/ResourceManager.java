/*
 * This file is part of toRight project.
 *
 * Author: GregAS
 */
package pl.gregas.toright.rendering;

import pl.gregas.toright.*;
import pl.gregas.toright.entities.*;
import pl.gregas.toright.rendering.*;
import pl.gregas.toright.io.*;
import java.util.*;
import java.io.*;
import java.awt.image.*;

/**
* Smth about class, method
* @param  x
* @param  y
* @return  smth
*/
public class ResourceManager {
  private Map<String, Texture> textures = new HashMap<String, Texture>();
  private Map<String, World> worlds = new HashMap<String, World>();
  private MusicPlayer musicPlayer;
  private InputK keys;
  private String currentWorld;

  public ResourceManager(InputK keys) {
    this.keys = keys;
    System.out.println("-->ResourceManager: Starting loading textures");
    loadTextures();
    System.out.println("-->ResourceManager: Starting loading worlds");
    loadWorlds();
    System.out.println("-->ResourceManager: Starting loading sounds");
    loadSounds();
  }

  /**
  * Load all textures
  */
  private void loadTextures() {
    int lineIterator = 0;
    List<String> fileNames = new ArrayList<String>();
    String line = null;
    try {
      FileReader fileReader = new FileReader(Config.TEXTURES_LIST);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      while((line = bufferedReader.readLine()) != null) {
        fileNames.add(lineIterator, line);
        lineIterator++;
      }
      bufferedReader.close();
    } catch(FileNotFoundException ex) {
      System.err.println("Unable to open file '" +Config.TEXTURES_LIST+ "'");
      return;
    } catch(IOException ex) {
      System.err.println("Error reading file '" +Config.TEXTURES_LIST+ "'");
      return;
    }

    for(int i = 0; i < lineIterator; i++) {
      textures.put(fileNames.get(i), new Texture(fileNames.get(i)));
      System.out.println("Texture loaded:" +fileNames.get(i));
    }
    System.out.println("-->ResourceManager: All Textures loaded");
  }
  /**
  * Load all worlds
  */
  private void loadWorlds() {
    int lineIterator = 0;
    List<String> fileNames = new ArrayList<String>();
    String line = null;
    try {
      FileReader fileReader = new FileReader(Config.WORLDS_LIST);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      while((line = bufferedReader.readLine()) != null) {
        fileNames.add(lineIterator, line);
        lineIterator++;
      }
      bufferedReader.close();
    } catch(FileNotFoundException ex) {
      System.err.println("Unable to open file '" +Config.WORLDS_LIST+ "'");
      return;
    } catch(IOException ex) {
      System.err.println("Error reading file '" +Config.WORLDS_LIST+ "'");
      return;
    }

    for(int i = 0; i < lineIterator; i++) {
      worlds.put(fileNames.get(i), new World(fileNames.get(i), this, keys));
      System.out.println("World loaded:" +fileNames.get(i));
    }
    System.out.println("-->ResourceManager: All worlds loaded");
  }

  private void loadSounds() {
    int lineIterator = 0;
    List<String> fileNames = new ArrayList<String>();
    String line = null;
    try {
      FileReader fileReader = new FileReader(Config.MUSIC_LIST);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      while((line = bufferedReader.readLine()) != null) {
        fileNames.add(lineIterator, line);
        lineIterator++;
      }
      bufferedReader.close();
    } catch(FileNotFoundException ex) {
      System.err.println("Unable to open file '" +Config.MUSIC_LIST+ "'");
      return;
    } catch(IOException ex) {
      System.err.println("Error reading file '" +Config.MUSIC_LIST+ "'");
      return;
    }
    musicPlayer = new MusicPlayer();
    for(int i = 0; i < lineIterator; i++) {
      musicPlayer.load(fileNames.get(i), "../resources/audio/"+fileNames.get(i)+".wav");
      System.out.println("Sound loaded:" +fileNames.get(i));
    }
    System.out.println("-->ResourceManager: All Sounds loaded");
  }

  public BufferedImage getTexture(String name) {
    return textures.get(name).getImage();
  }
  public World getWorld(String name) {
    return worlds.get(name);
  }

  public MusicPlayer getMusicPlayer() {
    return musicPlayer;
  }
  public int getWorldsNum() {
    return worlds.size();
  }
  public void setCurrentWorld(String name) {
    this.currentWorld = name;
  }
  public World getCurrentWorld() {
    return worlds.get(currentWorld);
  }
  public void gameOver() {
    for(int i = 1; i <= worlds.size(); i++)  {
      worlds.get("level"+i+"").setEnded(false);
    }
  }
}
