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
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Properties;
import java.io.FileInputStream;

/**
* @param  image Image reference to imagefile
* TODO
 */
public class World {
  private double spawnX;
  private double spawnY;
  private int cameraPos;
  private int worldWidth;
  private BufferedImage image;
  private ArrayList<Block> blocks = new ArrayList<Block>();
  private ArrayList<Block> movingBlocks = new ArrayList<Block>();
  private ArrayList<KillBlock> killBlocks = new ArrayList<KillBlock>();
  private Player player;
  private Exit exit;
  private String sound;
  private String background;
  private ResourceManager resources;
  private InputK keys;
  private int addPlayerLives = 0;
  private boolean isEnded = false;

  public World(String name, ResourceManager resources, InputK keys) {
    this.cameraPos = 0;
    this.resources = resources;
    this.keys = keys;
    Properties p = new Properties();
    try {
      this.image = ImageIO.read(new File("../resources/worlds/" + name +"/"+ name + ".png"));
      p.load(new FileInputStream("../resources/worlds/"+name+"/config.properties"));
    } catch (IOException e) {
      System.err.println("Error reading world: " +name+ ".png");
      e.printStackTrace();
    }
    this.sound = p.getProperty("sound");
    this.background = p.getProperty("background");
    int imgW = image.getWidth();
    this.worldWidth = imgW*Config.BLOCK_SIZE_X;
    int imgH = image.getHeight();
    int[] pixels = image.getRGB(0, 0, imgW, imgH, null, 0, imgW);
    for (int y = 0; y < imgH; y++) {
      for (int x = 0; x < imgW; x++) {
        int id = pixels[x + y * imgW];
        switch(id) {
          case 0xFFFF0000: //block
            blocks.add(new Block(x + y * imgW, Config.BLOCK_SIZE_X*x, Config.BLOCK_SIZE_Y*y-Config.BLOCK_SIZE_Y/10, Config.BLOCK_SIZE_X, Config.BLOCK_SIZE_Y , p.getProperty("world"), resources, false));
            break;
          case 0xFF00FF00: //player
            player = new Player((double)Config.PLAYER_SIZE_X*x, (double)Config.PLAYER_SIZE_Y*y, Config.PLAYER_SIZE_X, Config.PLAYER_SIZE_Y, p.getProperty("player"), resources, keys);
            spawnX = (double)Config.PLAYER_SIZE_X*x;
            spawnY = (double)Config.PLAYER_SIZE_Y*y;
            break;
          case 0xFF0000FF: //killblock
            killBlocks.add(new KillBlock(x + y * imgW, Config.BLOCK_SIZE_X*x, Config.BLOCK_SIZE_Y*y, Config.BLOCK_SIZE_X, Config.BLOCK_SIZE_Y , p.getProperty("killBlock"), resources, false, false));
            break;
          case 0xFFFFFF00: //mobs
            killBlocks.add(new KillBlock(x + y * imgW, Config.BLOCK_SIZE_X*x, Config.BLOCK_SIZE_Y*y, Config.PLAYER_SIZE_X, Config.BLOCK_SIZE_Y , p.getProperty("mob"), resources, true, false));
            break;
          case 0xFFFF00FF: //UPs
            killBlocks.add(new KillBlock(x + y * imgW, Config.BLOCK_SIZE_X*x, Config.BLOCK_SIZE_Y*y, Config.BLOCK_SIZE_X, Config.BLOCK_SIZE_Y , p.getProperty("UP"), resources, false, true));
            break;
          case 0xFF00FFFF: //movingBlock
            movingBlocks.add(new Block(x + y * imgW, Config.BLOCK_SIZE_X*x, Config.BLOCK_SIZE_Y*y-Config.BLOCK_SIZE_Y/10, Config.BLOCK_SIZE_X, Config.BLOCK_SIZE_Y , p.getProperty("movingBlock"), resources, true));
            break;
          case 0xFFFFFFFF: //exit
            exit = new Exit(x + y * imgW, Config.BLOCK_SIZE_X*x, Config.BLOCK_SIZE_Y*y, Config.BLOCK_SIZE_X, Config.BLOCK_SIZE_Y, p.getProperty("exit"), resources);
            break;
            default:

        }
      }
    }
  }


  public void render(Graphics2D g) {
    g.drawImage(resources.getTexture(this.background), 0, 0, Config.FR_WIDTH, Config.FR_HEIGHT, null);
    for(int i = 0; i < blocks.size(); i++) {
      blocks.get(i).render(g);
    }
    for(int i = 0; i < movingBlocks.size(); i++) {
      movingBlocks.get(i).render(g);
    }
    for(int i = 0; i < killBlocks.size(); i++) {
      killBlocks.get(i).render(g);
    }
    exit.render(g);
    player.render(g);

  }

  public void update() {
    for(int i = 0; i < blocks.size(); i++) {
      blocks.get(i).update();
    }
    for(int i = 0; i < movingBlocks.size(); i++) {
      movingBlocks.get(i).update();
    }
    for(int i = 0; i < killBlocks.size(); i++) {
      killBlocks.get(i).update();
    }
    exit.update();
    player.update();

  }
  public void moveCamera(double velocity) {
    cameraPos = cameraPos+(int)velocity;
    for(int i = 0; i < blocks.size(); i++) {
      blocks.get(i).moveByCamera(cameraPos);
    }
    for(int i = 0; i < movingBlocks.size(); i++) {
      movingBlocks.get(i).moveByCamera(cameraPos);
    }
    for(int i = 0; i < killBlocks.size(); i++) {
      killBlocks.get(i).moveByCamera(cameraPos);
    }
    exit.moveByCamera(cameraPos);
  }
  public void resetCamera() {
    cameraPos = 0;
    moveCamera(0.0);
  }
  public Player getPlayer() {
    return player;
  }
  public double getSpawnX() {
    return spawnX;
  }
  public double getSpawnY() {
    return spawnY;
  }
  public int getCameraPos() {
    return cameraPos;
  }
  public String getSound() {
    return sound;
  }
  public void removeKillBlock(int i) {
    killBlocks.remove(i);
  }
  public int getPlayerLives() {
    return this.addPlayerLives;
  }
  public void setPlayerLives(int set) {
    this.addPlayerLives = set;
  }
  public boolean isCameraSlideEnded() {
    return this.cameraPos >= this.worldWidth-Config.FR_WIDTH;
  }
  public int getBlockNum() {
    return blocks.size();
  }
  public Block getBlock(int i) {
    return blocks.get(i);
  }
  public int getMovingBlockNum() {
    return movingBlocks.size();
  }
  public Block getMovingBlock(int i) {
    return movingBlocks.get(i);
  }
  public int getKillBlockNum() {
    return killBlocks.size();
  }
  public KillBlock getKillBlock(int i) {
    return killBlocks.get(i);
  }
  public Exit getExit() {
    return exit;
  }
  public boolean isEnded() {
    return isEnded;
  }
  public void setEnded(boolean bool) {
    this.isEnded = bool;
  }
}
