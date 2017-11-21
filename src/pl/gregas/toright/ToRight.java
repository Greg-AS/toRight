/*
 * This file is part of toRight project.
 *
 * Author: GregAS
 */
package pl.gregas.toright;

import pl.gregas.toright.Game;
import pl.gregas.toright.io.*;
import pl.gregas.toright.rendering.ResourceManager;
import pl.gregas.toright.rendering.Menu;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
* Starting class in application. Consists only main method.
* @author GregAS.
* @version 1.0
*/
public class ToRight {
  /**
  * Creates visible frame and main objects in application, starts the game. Loads all config constants and variables.
  * @see InputK
  * @see ResourceManager
  * @see GameMaster
  * @see Game
  * @see Config
  */
  public static void main(String[] args) {
    Config.load();
    InputK keys = new InputK();
    ResourceManager resources = new ResourceManager(keys);
    Menu menu = new Menu("To Right", keys, resources);
    GameMaster gameMaster = new GameMaster(resources, keys, menu);
    System.out.println("-->GameMaster created");
    Game game = new Game(gameMaster, resources, keys, menu);
    JFrame frame = new JFrame(game.TITLE);
    frame.add(game);
    frame.setSize(game.WIDTH, game.HEIGHT);
    frame.setResizable(false);
    frame.setFocusable(true);
    frame.setLocationRelativeTo(null);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        game.stop();
        System.err.println("Interrupted game. Exiting");
      }
    });

    frame.setVisible(true);
    game.start();
  }
}
