/*
 * This file is part of toRight project.
 *
 * Author: GregAS
 */
package pl.gregas.toright;

import pl.gregas.toright.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.*;

/**
* Class contains all global constants, variables and keycodes.
* @param TITLE title of game
* @param K_UP keycode of up arrow
* @param K_DOWN keycode of down arrow
* @param K_LEFT keycode of left arrow
* @param K_RIGHT keycode of right arrow
* @param K_SPACE keycode of space key
* @param K_ESC keycode of esc key
* @param TEXTURES_LIST path to textures list file
* @param WORLDS_LIST path to worlds list file
* @param MUSIC_LIST path to music list file
* @param FR_WIDTH width of application frame
* @param FR_HEIGHT height of application frame
* @param FPS terget frames per second
* @param BLOCK_SIZE_X width of particular block in game
* @param BLOCK_SIZE_Y height of particular block in game
* @param PLAYER_SIZE_X width of player block
* @param PLAYER_SIZE_Y height of player block
* @param X_PROP proportion x of application window
* @param Y_PROP proportion y of application window
* @param VELOCITY speed of movement horizontally
* @param BLOCK_VELOCITY speed of moving blocks movement horizontally
* @param MOB_VELOCITY speed of mobs movement horizontally
* @param COLISION distance to chceck does collision occurs
* @param DEBUG parameter od debug mode (1 = true, 0 = false)
* @param GRAVITY multiplier of gravity force
* @param START_LIVES lives at the beggining of game
* @param HUD_POS_X x position of hud
* @param HUD_POS_Y y position of hud
* @param VOLUME volume of playing music
* @param ANIMATION_FRAMES maximum quantity of frames in files
* @param STARTING_LEVEL starting world number
 */
public class Config {
  /* PUBLIC CONSTANTS */
  public static final String TITLE = "ToRight";
  /* KEY CODES */
  public static final int K_UP = 40;
  public static final int K_DOWN = 38;
  public static final int K_LEFT = 37;
  public static final int K_RIGHT = 39;
  public static final int K_SPACE = 32;
  public static final int K_ESC = 27;
  /* PUBLIC VARIABLES */
  public static String TEXTURES_LIST;
  public static String WORLDS_LIST;
  public static String MUSIC_LIST;
  public static int FR_WIDTH;
  public static int FR_HEIGHT;
  public static int FPS;
  public static int BLOCK_SIZE_X;
  public static int BLOCK_SIZE_Y;
  public static int PLAYER_SIZE_X;
  public static int PLAYER_SIZE_Y;
  public static int X_PROP = 16;
  public static int Y_PROP = 9;
  public static double VELOCITY;
  public static int BLOCK_VELOCITY;
  public static int MOB_VELOCITY;
  public static double COLISION;
  public static int DEBUG;
  public static double GRAVITY;
  public static int START_LIVES;
  public static int HUD_POS_X;
  public static int HUD_POS_Y;
  public static int VOLUME;
  public static int ANIMATION_FRAMES;
  public static int STARTING_LEVEL;

  /**
  * Loads all parameters form files to variables in class. Is called at the beginning of program by @see ToRight#main method.
  */
  public static void load() {
    String cfgPath = "../resources/config.properties";
    try {
      Properties p = new Properties();
      p.load(new FileInputStream(cfgPath));
      //////////////
      if(Integer.parseInt(p.getProperty("43proportions"))==1) {
        X_PROP = 4;
        Y_PROP = 3;
      }
      TEXTURES_LIST = p.getProperty("textures_path");
      WORLDS_LIST = p.getProperty("worlds_path");
      MUSIC_LIST = p.getProperty("music_path");
      FR_WIDTH = Integer.parseInt(p.getProperty("resolution"));
      FR_HEIGHT = (FR_WIDTH*Y_PROP)/X_PROP;
      FPS = Integer.parseInt(p.getProperty("fps"));
      STARTING_LEVEL = Integer.parseInt(p.getProperty("starting_level"));
      BLOCK_SIZE_X = FR_WIDTH/Integer.parseInt(p.getProperty("block_size_x"));
      BLOCK_SIZE_Y = FR_HEIGHT/Integer.parseInt(p.getProperty("block_size_y"));
      PLAYER_SIZE_X = FR_WIDTH/Integer.parseInt(p.getProperty("player_size_x"));
      PLAYER_SIZE_Y = FR_HEIGHT/Integer.parseInt(p.getProperty("player_size_y"));
      ANIMATION_FRAMES = Integer.parseInt(p.getProperty("animation_frames"));
      VELOCITY = (FR_WIDTH/1000)+Double.parseDouble(p.getProperty("velocity"));
      BLOCK_VELOCITY = (FR_WIDTH/1000)+Integer.parseInt(p.getProperty("block_velocity"));
      MOB_VELOCITY = (FR_WIDTH/1000)+Integer.parseInt(p.getProperty("mob_velocity"));
      COLISION = Double.parseDouble(p.getProperty("colision"));
      GRAVITY = Double.parseDouble(p.getProperty("gravity"));
      DEBUG = Integer.parseInt(p.getProperty("debug"));
      START_LIVES = Integer.parseInt(p.getProperty("start_lives"));
      HUD_POS_X = Integer.parseInt(p.getProperty("hud_position_x"));
      HUD_POS_Y = Integer.parseInt(p.getProperty("hud_position_y"));
      VOLUME = Integer.parseInt(p.getProperty("volume"));
      //////////////
      } catch(FileNotFoundException ex) {
        System.err.println("Unable to open file '" + cfgPath + "'");
        return;
      } catch(IOException ex) {
        System.err.println("Error reading file '" + cfgPath + "'");
        return;
      }
    }
    public static void save() {
      //TODO
    }
  }
