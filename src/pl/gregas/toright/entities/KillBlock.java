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
import java.awt.*;

/**
*
 */
public class KillBlock extends Entity{
  private boolean isMob;
  private boolean isUP;
  private boolean[] coll = new boolean[3];
  private int direction = 1;
  private Rectangle2D.Double playerR;
  private Rectangle2D.Double playerL;
  private Rectangle2D.Double playerD;
  private int moved;

  public KillBlock(int id, int posX, int posY, int sizeX, int sizeY, String texture, ResourceManager resources, boolean isMob, boolean isUP) {
    this.id = id;
    this.isMob = isMob;
    this.isUP = isUP;
    this.startX = posX;
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    this.setPosition(posX, posY);
    this.resources = resources;
    this.texture = texture;
  }
  public void update() {

    if(isMob) {
      setCollision();
      if (coll[2]) {
        if (!coll[0] && coll[1]) direction =1;
        if (coll[0] && !coll[1]) direction =-1;
      }
      if(!coll[2]) direction = direction *(-1);
      if (coll[0] && coll[1] && coll[2]) return;
      this.posX = this.posX + direction*Config.MOB_VELOCITY;
      moved = moved + direction*Config.MOB_VELOCITY;
    }
  }
  public void moveByCamera(int cameraPos) {
    this.setPosition(this.startX - cameraPos +moved, this.posY);
  }
  public boolean isUP() {
    return isUP;
  }

  public void setCollision() {
    playerR = new Rectangle2D.Double((double)(this.posX+Config.BLOCK_SIZE_X),   (double)this.posY+10,      (double)Config.COLISION,              (double)Config.BLOCK_SIZE_Y*7/10);
    playerL = new Rectangle2D.Double((double)(this.posX-Config.COLISION),        (double)this.posY+10,      (double)Config.COLISION,              (double)Config.BLOCK_SIZE_Y*7/10);
    playerD = new Rectangle2D.Double((double)this.posX+(double)Config.BLOCK_SIZE_X*2/5,                     (double)this.posY+(double)Config.BLOCK_SIZE_Y,         (double)Config.BLOCK_SIZE_X/5, (double)Config.COLISION*2);
    int blockX;
    int blockY;
    coll[0] = false;
    coll[1] = false;
    coll[2] = false;
    for(int i=0; i < resources.getCurrentWorld().getBlockNum(); i++) {
      blockX = resources.getCurrentWorld().getBlock(i).getPosX();
      blockY = resources.getCurrentWorld().getBlock(i).getPosY();
      if(playerR.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)) {
         this.coll[0]=true;}
      if(playerD.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)){
          this.coll[2]=true;}
      if(playerL.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)){
        this.coll[1]=true;}
      }
    }
}
