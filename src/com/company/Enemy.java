package com.company;

import java.util.Random;

public class Enemy {
    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public int x = 0;
    public int y = 0;
    public int tempPosX, tempPosY;
    boolean hasCollided = false;
    char c = 'E';
    boolean isAlive = true;
    Random rand = new Random();
    String type = "";
    public Enemy() {
    }

    public Enemy(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
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

    public void update(int tempPosRandX, int tempPosRandY) {
        x += tempPosRandX;
        y += tempPosRandY;
        setX(x);
        setY(y);
    }
}
