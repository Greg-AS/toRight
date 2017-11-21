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
import java.awt.geom.*;
import java.awt.Graphics2D;

/**
*
 */
public class Block extends Entity{
  private boolean isMoving;
  private boolean[] coll = new boolean[2];
  private int direction = 1;
  private Rectangle2D.Double playerR;
  private Rectangle2D.Double playerL;
  private int moved;

  public Block(int id, int posX, int posY, int sizeX, int sizeY, String texture, ResourceManager resources, boolean isMoving) {
    this.id = id;
    this.startX = posX;
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    this.setPosition(posX, posY);
    this.resources = resources;
    this.texture = texture;
    this.isMoving = isMoving;
  }


  public void update() {
    if(isMoving) {
      setCollision();
      if (!coll[0] && coll[1]) direction =1;
      if (coll[0] && !coll[1]) direction =-1;
      if (coll[0] && coll[1]) return;
      this.posX = this.posX + direction*Config.BLOCK_VELOCITY;
      moved = moved + direction*Config.BLOCK_VELOCITY;
    }
  }
  public void moveByCamera(int cameraPos) {
    this.setPosition(this.startX - cameraPos + moved, this.posY);
  }

  public void setCollision() {
    playerR = new Rectangle2D.Double((double)(this.posX+Config.BLOCK_SIZE_X),   (double)this.posY+10,      (double)Config.COLISION,              (double)Config.BLOCK_SIZE_Y*7/10);
    playerL = new Rectangle2D.Double((double)(this.posX-Config.COLISION),        (double)this.posY+10,      (double)Config.COLISION,              (double)Config.BLOCK_SIZE_Y*7/10);
    int blockX;
    int blockY;
    coll[0] = false;
    coll[1] = false;
    for(int i=0; i < resources.getCurrentWorld().getBlockNum(); i++) {
      blockX = resources.getCurrentWorld().getBlock(i).getPosX();
      blockY = resources.getCurrentWorld().getBlock(i).getPosY();
      if(playerR.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)) {
         this.coll[0]=true;
       }
      if(playerL.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)){
        this.coll[1]=true;}
      }
    }
  public int getDirection() {
    return direction;
  }
}
