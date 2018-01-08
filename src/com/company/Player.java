package com.company;

public class Player {
    public int x = 0;
    public int y = 0;
    int hitPoints = 10;

    public Player() {
    }

    public Player(int x, int y) {
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

    public void update(int newPosX, int newPosY){
        setX(getX() + newPosX);
        setY(getY() + newPosY);
    }
}
