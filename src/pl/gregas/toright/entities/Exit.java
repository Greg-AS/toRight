/*
 * This file is part of toRight project.
 *
 * Author: GregAS
 */
package pl.gregas.toright.entities;

import pl.gregas.toright.*;
import pl.gregas.toright.entities.*;
import pl.gregas.toright.rendering.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.*;

/**
*
 */
public class Exit extends Entity{

  public Exit(int id, int posX, int posY, int sizeX, int sizeY, String texture, ResourceManager resources) {
    this.id = id;
    this.startX = posX;
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    this.setPosition(posX, posY);
    this.resources = resources;
    this.texture = texture;
  }

  public void update() {

  }
  public void moveByCamera(int cameraPos) {
    this.setPosition(this.startX - cameraPos, this.posY);
  }
}
