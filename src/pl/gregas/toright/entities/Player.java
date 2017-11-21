/*
 * This file is part of toRight project.
 *
 * Author: GregAS
 */
package pl.gregas.toright.entities;

import pl.gregas.toright.rendering.*;
import pl.gregas.toright.*;
import pl.gregas.toright.io.*;
import java.util.Properties;
import java.io.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.*;
import java.awt.image.*;

/**
*
 */
public class Player extends Entity{
  private InputK keys;
  private double posX;
  private double posY;
  private boolean moving = false;
  private boolean isFalling = true;
  private boolean isJumping = false;
  private double currentVelocity = 0.0;
  private Rectangle2D.Double playerR;
  private Rectangle2D.Double playerL;
  private Rectangle2D.Double playerU;
  private Rectangle2D.Double playerD;
  private boolean[] coll;
  private int stickedVelocity = 0;
  private boolean animate = false;
  private int currentFrame = 1;
  private int animateSpeed = (int)Config.VELOCITY/2;
  private int framesNum = Config.ANIMATION_FRAMES;
  private int count=0;

  public Player(double spawnX, double spawnY, int sizeX, int sizeY, String texture, ResourceManager resources, InputK keys) {
    this.coll = new boolean[4];
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    this.posX = spawnX;
    this.posY = spawnY;
    this.keys = keys;
    this.resources = resources;
    this.texture = texture;
  }
  @Override
  public void render(Graphics2D g2) {
    BufferedImage image = resources.getTexture(texture);
    int imgW = image.getWidth(null);
      g2.drawImage(image.getSubimage(currentFrame*imgW/framesNum-imgW/framesNum, 0, imgW/framesNum, imgW/framesNum), (int) posX, (int) posY, sizeX, sizeY, null);
    if(Config.DEBUG==1) {
      g2.draw(this.playerR);
      g2.draw(this.playerL);
      g2.draw(this.playerU);
      g2.draw(this.playerD);
    }
  }
  public void setPosition(double posX, double posY) {
    this.posX = posX;
    this.posY = posY;
  }

  public void update() {
    if(moving) {
      //animate = true;
      if(currentFrame==framesNum) {currentFrame =1;}
      else {
        if (count==animateSpeed){
          currentFrame++;
          count=0;
        }count++;
      }
    }else  {
      currentFrame=1;
      //animate = false;
    }
    setCollision();
    if(!coll[0] && !coll[1] && !coll[2] && !coll[3] && !isJumping) {
      isFalling = true;
    }
      if(stickedVelocity != 0 && this.posX >=1 && !coll[0] && !coll[1]) {
        if(stickedVelocity > 0 && this.posX >= ((Config.FR_WIDTH/2)-(2*Config.PLAYER_SIZE_X))) resources.getCurrentWorld().moveCamera(stickedVelocity);
        else  this.posX = this.posX +stickedVelocity;
      }
      if(keys.isDown(Config.K_LEFT) && keys.wasPressed(Config.K_SPACE) && !isFalling && !isJumping && !coll[1] && this.posX >=1) {
        if(this.posX >= 1) {
           this.moveLeft();
        }
          currentVelocity = Config.VELOCITY;
          isJumping = true;
          isFalling = false;
      }else if(keys.isDown(Config.K_RIGHT) && keys.wasPressed(Config.K_SPACE) && !isFalling && !isJumping && !coll[0] && this.posX <= Config.FR_WIDTH) {
        //do animation
          currentVelocity = Config.VELOCITY;
          isJumping = true;
          isFalling = false;
          moving = true;
          if(this.posX >= ((Config.FR_WIDTH/2)-(2*Config.PLAYER_SIZE_X)) && !resources.getCurrentWorld().isCameraSlideEnded()) {
            resources.getCurrentWorld().moveCamera(Config.VELOCITY);
          }else this.moveRight();
      }else if(keys.isDown(Config.K_RIGHT) && coll[0] == false && this.posX <= Config.FR_WIDTH) {
          moving = true;
          if(this.posX >= ((Config.FR_WIDTH/2)-(2*Config.PLAYER_SIZE_X)) && !resources.getCurrentWorld().isCameraSlideEnded()) {
            resources.getCurrentWorld().moveCamera(Config.VELOCITY);
          } else this.moveRight();
      }else if(keys.isDown(Config.K_LEFT) && coll[1] == false && this.posX >=1) {
          this.moveLeft();
          moving = true;
      }else if(keys.wasPressed(Config.K_SPACE) && isFalling ==false && isJumping == false) {
          currentVelocity = Config.VELOCITY;
          isJumping = true;
          isFalling = false;
      }else {
        moving = false;
      }

      if(isJumping && !isFalling) {
        moving = true;
        currentVelocity = currentVelocity - (Config.VELOCITY/35)/Config.GRAVITY;
        this.posY = this.posY -currentVelocity;
        if (currentVelocity <= 0.005 || coll[2] ) {
          isJumping = false;
          currentVelocity = 0;
          isFalling = true;
        }
      }
      if(isFalling && !isJumping) {
        moving = true;
        currentVelocity = currentVelocity + (Config.VELOCITY/25)*Config.GRAVITY;
        this.posY = this.posY +currentVelocity;
        if (currentVelocity == Config.VELOCITY || coll[3]==true ) {
          currentVelocity = 0.0;
          isFalling = false;
          isJumping = false;
          moving = false;
        }
      }
  }

  private void moveRight() {
      this.posX = this.posX +Config.VELOCITY*8/10;
  }

  private void moveLeft() {
    this.posX = this.posX -Config.VELOCITY*8/10;
  }

  public void resetPlayer() {
    currentVelocity =0.0;
    isFalling = true;
    isJumping = false;
  }

  public boolean isMoving(){
    return moving;
  }

  public void setCollision() {
    playerR = new Rectangle2D.Double((double)(this.posX+Config.PLAYER_SIZE_X),   (double)this.posY,      (double)Config.COLISION*1/3,              (double)Config.PLAYER_SIZE_Y*5/10);
    playerL = new Rectangle2D.Double((double)(this.posX-Config.COLISION),        (double)this.posY,      (double)Config.COLISION*1/3,              (double)Config.PLAYER_SIZE_Y*5/10);
    playerU = new Rectangle2D.Double((double)this.posX,                        (double)this.posY-(double)Config.COLISION,  (double)Config.PLAYER_SIZE_X,         (double)Config.COLISION);
    playerD = new Rectangle2D.Double((double)this.posX+(double)Config.PLAYER_SIZE_X*1/5,                        (double)this.posY+(double)Config.PLAYER_SIZE_Y,         (double)Config.PLAYER_SIZE_X*2/5, (double)Config.COLISION*9/8);
    int blockX;
    int blockY;
    coll[0] = false;
    coll[1] = false;
    coll[2] = false;
    coll[3] = false;
    stickedVelocity = 0;
    for(int i=0; i < resources.getCurrentWorld().getBlockNum(); i++) {
      blockX = resources.getCurrentWorld().getBlock(i).getPosX();
      blockY = resources.getCurrentWorld().getBlock(i).getPosY();
      if(playerR.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)) {
         this.coll[0]=true;
       }
      if(playerL.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)){
        this.coll[1]=true;}
      if(playerU.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)){
         this.coll[2]=true;}
      if(playerD.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)){
        this.coll[3]=true;
        }
      }
      for(int i=0; i < resources.getCurrentWorld().getMovingBlockNum(); i++) {
        blockX = resources.getCurrentWorld().getMovingBlock(i).getPosX();
        blockY = resources.getCurrentWorld().getMovingBlock(i).getPosY();
        if(playerR.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)) {
           this.coll[0]=true;
         }
        if(playerL.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)){
          this.coll[1]=true;}
        if(playerU.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)){
           this.coll[2]=true;}
        if(playerD.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)){
          this.coll[3]=true;
          stickedVelocity = Config.BLOCK_VELOCITY*resources.getCurrentWorld().getMovingBlock(i).getDirection();
          }
        }
      for(int i=0; i < resources.getCurrentWorld().getKillBlockNum(); i++) {
        blockX = resources.getCurrentWorld().getKillBlock(i).getPosX();
        blockY = resources.getCurrentWorld().getKillBlock(i).getPosY();
        if(playerR.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)||
            playerL.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)||
            playerU.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)||
            playerD.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)){
              if (resources.getCurrentWorld().getKillBlock(i).isUP()) {
                resources.getCurrentWorld().setPlayerLives(1);
                resources.getCurrentWorld().removeKillBlock(i);
              } else resources.getCurrentWorld().setPlayerLives(-1);
        }
      }
      blockX = resources.getCurrentWorld().getExit().getPosX();
      blockY = resources.getCurrentWorld().getExit().getPosY();
      if(playerR.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)||
          playerL.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)||
          playerU.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)||
          playerD.intersects((double)blockX, (double)blockY, (double)Config.BLOCK_SIZE_X, (double)Config.BLOCK_SIZE_Y)){
        resources.getCurrentWorld().setEnded(true);
      }
    }
  }
