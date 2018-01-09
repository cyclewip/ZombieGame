package com.company;

public class Player {
    public int x = 0;
    public int y = 0;
    public int tempPosX, tempPosY;
    int hitPoints = 100;

    public Player() {
    }

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getTempPosX() {
        return tempPosX;
    }

    public void setTempPosX(int tempPosX) {
        this.tempPosX = tempPosX;
    }

    public int getTempPosY() {
        return tempPosY;
    }

    public void setTempPosY(int tempPosY) {
        this.tempPosY = tempPosY;
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

    public void update(int tempPosX, int tempPosY){
        x += tempPosX;
        y += tempPosY;
        setX(x);
        setY(y);
    }
}
