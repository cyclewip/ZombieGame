package com.company;

import java.util.Random;

public class Enemy {
    public int x = 0;
    public int y = 0;
    int tempPosX = 0;
    int tempPosY = 0;
    int realPosX = 0;
    int realPosY = 0;
    Random rand = new Random();

    public Enemy() {
    }

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void update() {
        x +=  rand.nextInt(2 + 1) - 1;
        y +=  rand.nextInt(2 + 1) - 1;
        setX(x);
        setY(y);
    }
    public void checkForWall(){
        tempPosX = x + rand.nextInt(2 + 1) - 1;
        tempPosY = y + rand.nextInt(2 + 1) - 1;
    }
    public void addToMovement(){
        realPosX = x + rand.nextInt(2 + 1) - 1;
        realPosY = y + rand.nextInt(2 + 1) - 1;
    }
    public void followPlayer(){

    }
}
