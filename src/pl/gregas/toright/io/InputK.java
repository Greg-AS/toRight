/*
 * This file is part of toRight project.
 *
 * Author: GregAS
 */
package pl.gregas.toright.io;

import pl.gregas.toright.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
* Smth about class, method
* @param  x
* @param  y
* @return  smth
 */
public class InputK extends KeyAdapter {
  private boolean[] keys = new boolean[256];
  private boolean[] releasedKeys = new boolean[256];

  public InputK() {
    System.out.println("KeyInputManager is ready");
  }
  @Override
  public void keyPressed(KeyEvent e) {
    int k = e.getKeyCode();
    keys[k] = true;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int k = e.getKeyCode();
    keys[k] = false;
    releasedKeys[k] = true;
  }

  public void update() {
    for (int i = 0; i < 256; i++)
      releasedKeys[i] = keys[i];
    }

  public boolean isDown(int keyCode) {
    return keys[keyCode];
  }

  public boolean wasPressed(int keyCode) {
    return isDown(keyCode) && !releasedKeys[keyCode];
  }
}
