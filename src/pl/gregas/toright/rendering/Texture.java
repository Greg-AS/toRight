/*
 * This file is part of toRight project.
 *
 * Author: GregAS
 */
package pl.gregas.toright.rendering;

import pl.gregas.toright.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
* Class using to create object which referece to simple texture.
* All textures are preload by ResourceManager Class
* @param  image Image reference to imagefile
* @param  width Width of buffered image
* @param  height height of buffered image
 */
public class Texture {
  private BufferedImage image;
  private int width;
  private int height;

  public Texture(String name) {
    try {
      this.image = ImageIO.read(new File("../resources/textures/" + name + ".png"));
      width = image.getWidth();
      height = image.getHeight();
    } catch (IOException e) {
      System.err.println("Error reading texture: " +name+ ".png");
      e.printStackTrace();
    }
  }

  public Texture(String name, int width, int height) {
    try {
      this.image = ImageIO.read(new File("../resources/textures/" + name + ".png"));
      this.width = width;
      this.height = height;
    } catch (IOException e) {
      System.err.println("Error reading texture: " +name+ ".png");
      e.printStackTrace();
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public BufferedImage getImage() {
    return image;
  }
}
