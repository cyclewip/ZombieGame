package com.company;

import java.util.Random;
import java.util.*;
import java.util.Arrays;
import java.util.List;

public class SmartEnemy extends Archetype {
    Random random = new Random();
    //    int x = 0;
//    int y = 0;
    String type = "Smart";
    boolean isAlive = true;
    int hitPoints = 10;

    //    public int tempPosX = 0;
//    public int tempPosY = 0;
    public SmartEnemy(int x, int y, String type) {
        super(x, y, type);
        this.type = type;
    }

    public void pattern(Player player, Archetype e, int randNumb) {
        int randomNum = random.nextInt(2) + 1;
        int x;
        int y;

        if (player.getX() < e.getX() && player.getY() < e.getY()) {
            if (randomNum == 1) {
                x = e.getX() - 1;
                setX(x);
            } else {
                y = e.getY() - 1;
                setY(y);
            }
        } else if (player.getX() > e.getX() && player.getY() > e.getY()) {
            if (randomNum == 1) {
                x = e.getX() + 1;
                e.setX(x);
            } else {
                y = e.getY() + 1;
                e.setY(y);
            }
        } else if (player.getX() < e.getX() && player.getY() > e.getY()) {
            if (randomNum == 1) {
                x = e.getX() - 1;
                e.setX(x);
            } else {
                y = e.getY() + 1;
                e.setY(y);
            }
        } else if (player.getX() > e.getX() && player.getY() < e.getY()) {
            if (randomNum == 1) {
                x = e.getX() + 1;
                e.setX(x);
            } else {
                y = e.getY() - 1;
                e.setY(y);
            }
        } else if (player.getX() < e.getX()) {
            x = e.getX() - 1;
            e.setX(x);
        } else if (player.getX() > e.getX()) {
            x = e.getX() + 1;
            e.setX(x);
        } else if (player.getY() < e.getY()) {
            y = e.getY() - 1;
            e.setY(y);
        } else if (player.getY() > e.getY()) {
            y = e.getY() + 1;
            e.setY(y);
        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public void update(int tempPosX, int tempPosY) {
        x += tempPosX;
        y += tempPosY;
        setX(x);
        setY(y);
    }
//    public void spawner(Archetype e, Renderer rend){
//        if(rend.enemyTimer == 5){
//            e = new SmartEnemy(random.nextInt(35) + 25,random.nextInt(12) + 6, "Smart");
//        }
//
//    }
}
