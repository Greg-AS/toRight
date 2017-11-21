/*
 * This file is part of toRight project.
 *
 * Author: GregAS
 */
package pl.gregas.toright.rendering;

import pl.gregas.toright.*;
import pl.gregas.toright.rendering.*;
import pl.gregas.toright.io.*;
import pl.gregas.toright.entities.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Font;
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
* Smth about class, method
* @param  x
* @param  y
* @return  smth
 */
public class Menu {
  private boolean visible = true;
  private String background = "pink";
  private String news;
  private int currentSelected = 1;
  private InputK keys;
  private ResourceManager resources;

  public Menu(String news, InputK keys, ResourceManager resources) {
    this.news = news;
    this.keys = keys;
    this.resources = resources;
  }

  public void render(Graphics2D g) {
    g.drawImage(resources.getTexture(this.background), 0, 0, Config.FR_WIDTH, Config.FR_HEIGHT, null);
    g.setColor(Color.BLACK);
    g.setFont(new Font("Arial", Font.BOLD, Config.FR_HEIGHT/10));
    g.drawString(news, Config.FR_WIDTH/2, Config.FR_HEIGHT/5);
    if(currentSelected == 1) {
      g.setColor(Color.ORANGE);
      g.setFont(new Font("Arial", Font.BOLD, Config.FR_HEIGHT/20));
      g.drawString("Play", Config.FR_WIDTH/2, 2*Config.FR_HEIGHT/5);
      g.setColor(Color.BLACK);
      g.drawString("Options", Config.FR_WIDTH/2, 3*Config.FR_HEIGHT/5);
      g.drawString("Exit", Config.FR_WIDTH/2, 4*Config.FR_HEIGHT/5);
    } else if(currentSelected == 2) {
      g.setColor(Color.ORANGE);
      g.setFont(new Font("Arial", Font.BOLD, Config.FR_HEIGHT/20));
      g.drawString("Options", Config.FR_WIDTH/2, 3*Config.FR_HEIGHT/5);
      g.setColor(Color.BLACK);
      g.drawString("Play", Config.FR_WIDTH/2, 2*Config.FR_HEIGHT/5);
      g.drawString("Exit", Config.FR_WIDTH/2, 4*Config.FR_HEIGHT/5);
    } else if(currentSelected == 3) {
      g.setColor(Color.ORANGE);
      g.setFont(new Font("Arial", Font.BOLD, Config.FR_HEIGHT/20));
      g.drawString("Exit", Config.FR_WIDTH/2, 4*Config.FR_HEIGHT/5);
      g.setColor(Color.BLACK);
      g.drawString("Play", Config.FR_WIDTH/2, 2*Config.FR_HEIGHT/5);
      g.drawString("Options", Config.FR_WIDTH/2, 3*Config.FR_HEIGHT/5);
    }
  }

  public void update() {

    if(keys.wasPressed(Config.K_UP)) {
      if(currentSelected != 3) this.currentSelected = this.currentSelected+1;
      else this.currentSelected = 1;
    }
    if(keys.wasPressed(Config.K_DOWN)) {
      if(currentSelected != 1) this.currentSelected = this.currentSelected-1;
      else this.currentSelected = 3;
    }
    if(keys.wasPressed(Config.K_SPACE)) {
      if (currentSelected == 1) {
        this.visible = false;
      }
      if (currentSelected == 2) {
        System.out.println("Options dont work yet");
      }
      if (currentSelected == 3) {
        Game.stop();
      }
    }
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean set, String news) {
    this.visible = set;
    this.news = news;
  }
}
