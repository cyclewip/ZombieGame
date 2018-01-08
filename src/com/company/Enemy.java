package com.company;

import java.util.Random;

public class Enemy {
    public int x = 0;
    public int y = 0;
    int tempPosX = 0;
    int tempPosY = 0;
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
        x += tempPosX;
        y += tempPosY;
        setX(x);
        setY(y);
    }
    public void checkForWall(){
        tempPosX = rand.nextInt(2 + 1) - 1;
        tempPosY =  rand.nextInt(2 + 1) - 1;
    }

    public void followPlayer(){

    }
}
