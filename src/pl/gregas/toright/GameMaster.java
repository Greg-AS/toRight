/*
 * This file is part of toRight project.
 *
 * Author: GregAS
 */
package pl.gregas.toright;

import pl.gregas.toright.*;
import pl.gregas.toright.rendering.*;
import pl.gregas.toright.io.*;
//import java.awt.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;

/**
* Smth about class, method
* @param  x
* @param  y
* @return  smth
 */
public class GameMaster {
  public static int currentLevel;
  private ResourceManager resources;
  private InputK keys;
  private Menu menu;
  public static int lives;
  private int time = 0;

  GameMaster (ResourceManager resources, InputK keys, Menu menu) {
    this.resources = resources;
    this.keys = keys;
    this.menu = menu;
    startPlay();
  }
  public void render(Graphics2D g) {
    g.setColor(Color.BLACK);
    g.setFont(new Font("Arial", Font.BOLD, Config.FR_HEIGHT/20));
    g.drawString("Lives: "+lives+" | Level: "+currentLevel+"", Config.HUD_POS_X, Config.HUD_POS_Y+Config.FR_HEIGHT/20);
  }

  public void update() {
    ////////////MUSIC
    if(keys.wasPressed(Config.K_ESC)) {
      menu.setVisible(true, "Pause");
      resources.getMusicPlayer().stop();
    }
    if (menu.isVisible()) return;
    if(!resources.getMusicPlayer().isPlaying()) {
      resources.getMusicPlayer().play(resources.getCurrentWorld().getSound());
      resources.getMusicPlayer().setPlaying(true);
    }
    /////////MUSIC

    ////////LIVES UPDATER
    if (resources.getCurrentWorld().getPlayerLives() < 0) {
      lives--;
      if(lives > 0) resetWorld();
      else gameOver();
    }
    if (resources.getCurrentWorld().getPlayerLives() > 0) {
      lives++;
      resources.getCurrentWorld().setPlayerLives(0);
    }
    ////////LIVES UPDATER

    ////////ENDER
    if(resources.getCurrentWorld().isEnded()) {
      if(resources.getWorldsNum() == currentLevel)winGame();
      else nextLevel();
    }
    resources.getCurrentWorld().update();

  }
  private void startPlay() {
    this.currentLevel = Config.STARTING_LEVEL;
    resources.setCurrentWorld("level"+Config.STARTING_LEVEL);
    this.lives = Config.START_LIVES;
  }
  private void resetWorld() {
    resources.getCurrentWorld().resetCamera();
    resources.getCurrentWorld().getPlayer().setPosition(resources.getCurrentWorld().getSpawnX(), resources.getCurrentWorld().getSpawnY());
    resources.getCurrentWorld().getPlayer().resetPlayer();
    resources.getCurrentWorld().setPlayerLives(0);
    resources.getMusicPlayer().stop();

  }
  public void nextLevel() {
    this.currentLevel++;
    resources.setCurrentWorld("level"+currentLevel);
    resources.getMusicPlayer().stop();
  }
  private void gameOver() {
    menu.setVisible(true, "Game Over");
    resources.gameOver();
    resetWorld();
    startPlay();
    resources.getCurrentWorld().resetCamera();
    resources.getCurrentWorld().setPlayerLives(0);
    resetWorld();
    resources.getMusicPlayer().stop();
  }
  private void winGame() {
    System.out.println("GAME HAS WON");
    menu.setVisible(true, "YOU WON");
    resources.gameOver();
    resetWorld();
    startPlay();
    resources.getCurrentWorld().resetCamera();
    resources.getCurrentWorld().setPlayerLives(0);
    resetWorld();
    resources.getMusicPlayer().stop();
  }
}
