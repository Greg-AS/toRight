/*
 * This file is part of toRight project.
 *
 * Author: GregAS
 */
package pl.gregas.toright;

import pl.gregas.toright.*;
import pl.gregas.toright.rendering.*;
import pl.gregas.toright.io.*;
import pl.gregas.toright.entities.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.*;
import java.awt.EventQueue;
import javax.swing.*;
import java.awt.image.BufferStrategy;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
* Class which creates object, consist main loop which can be stop and start by particular methods
* @author GregAS.
* @version 1.0
 */
public class Game extends Canvas {
  public String TITLE;
  public int WIDTH;
  public int HEIGHT;
  private GameMaster gameMaster;
  private ResourceManager resources;
  private InputK keys;
  private Menu menu;
  private static boolean started = false;
  /**
  * Creates an object with particular parameters.
  * @param gamemaster the object type GameMaster
  * @param resources the object type ResourceManager
  * @param keys the object type InputK
  * @param menu the object type Menu
  */
  public Game(GameMaster gamemaster, ResourceManager resources, InputK keys, Menu menu) {
    this.menu = menu;
    this.gameMaster = gamemaster;
    this.resources  = resources;
    this.keys = keys;
    this.TITLE = Config.TITLE;
    this.WIDTH = Config.FR_WIDTH;
    this.HEIGHT = Config.FR_HEIGHT;
    System.out.println("-->Game created");
  }
  /**
  * Stop the main loop of game. Exit of program.
  */
  public static void stop() {
    if(started == false) return;
    started = false;
    System.exit(0);
  }
  /**
  * Refresh states of all objects which will be render
  * @see Menu#isVisible
  * @see Menu#update
  * @see GameMaster#update
  * @see InputK#update
  */
  private void update() {
    addKeyListener(keys);
    if(menu.isVisible()) {
      menu.update();
    } else {
      gameMaster.update();
    }
    keys.update();
  }
  /**
  * Render objects which was refreshed
  * @see Game#update
  * @see Menu#isVisible
  * @see Menu#render
  * @see World#render
  * @see GameMaster#render
  */
  private void render() {
    BufferStrategy bs = getBufferStrategy();
    if (bs == null) {
      createBufferStrategy(3);
      return;
    }
    Graphics g = bs.getDrawGraphics();
    Graphics2D g2 = (Graphics2D) g;
    // g2.setColor(Color.BLACK);
    // g2.fillRect(0, 0, WIDTH, HEIGHT);

    /////
    if(menu.isVisible()) {
      menu.render(g2);
    } else {
      resources.getCurrentWorld().render(g2);
      gameMaster.render(g2);
    }
    ////
    g2.dispose();
    bs.show();
  }
  /**
  * Starts the program. Creates main loop which works only if @param started is true.
  * Calls @see Game#update and @see Game#render periodicaly, based on target fps which takes from @see Config.
  */
  public void start() {
    started = true;
    boolean rend = false;
    double nanoSecondsPerUpdate = 1000000000.0 / Config.FPS;
    long lastTime = System.nanoTime();
    long timer = System.currentTimeMillis();
    double unprocessed = 0.0;
    int fps = 0;

    while(started) {
      requestFocus();

      long currentNanoTime = System.nanoTime();
      unprocessed += (currentNanoTime - lastTime) / nanoSecondsPerUpdate;
      lastTime = currentNanoTime;

      if (unprocessed >= 1.0) {
        update();
        unprocessed--;
        rend = true;
      } else rend = false;

      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      if(rend) {
        render();
        fps += 1;
      }

      if (System.currentTimeMillis() - 1000 > timer) {
        timer += 1000;
        //System.out.printf("FPS: %d\n", fps);
        fps = 0;
      }
    }
  }

}
