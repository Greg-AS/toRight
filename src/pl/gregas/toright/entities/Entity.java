/*
 * This file is part of toRight project.
 *
 * Author: GregAS
 */
package pl.gregas.toright.entities;

import pl.gregas.toright.*;
import pl.gregas.toright.rendering.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.*;
import java.awt.Graphics2D;
import java.awt.Graphics;

/**
*
 */
public abstract class Entity {
  protected int posX;
  protected int posY;
  protected String texture;
  protected ResourceManager resources;
  protected int sizeX;
  protected int sizeY;
  protected int id;
  protected int startX;
  // private Animation animation;
  public void render(Graphics2D g2) {
    g2.drawImage(resources.getTexture(texture), posX, posY, sizeX, sizeY, null);
  }
  public void setPosition(int posX, int posY) {
    this.posX = posX;
    this.posY = posY;
  }
  public int getPosX() {
    return (int)this.posX;
  }

  public int getPosY() {
    return (int)this.posY;
  }

  public int getID() {
    return this.id;
  }
}
