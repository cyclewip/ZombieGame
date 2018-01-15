package com.company;

public abstract class Archetype {
    public int x = 0;
    public int y = 0;
    public int tempPosX;
    public int tempPosY;
    String type = "";
    boolean isAlive = true;
    boolean inRange = false;
    char c = 'E';

    public Archetype() {
    }
    public Archetype(int x, int y, String type) {
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

    public int getTempPosX() {
        return tempPosX;
    }

    public int getTempPosY() {
        return tempPosY;
    }

    public void setTempPosX(int tempPosX) {
        this.tempPosX = tempPosX;
    }

    public void setTempPosY(int tempPosY) {
        this.tempPosY = tempPosY;
    }

    public boolean isAlive() {
        return isAlive;
    }
//    public abstract void spawner(Archetype e, Renderer rend);
    public abstract void pattern(Player player, Archetype e);

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public void update(int tempPosX, int tempPosY) {
        x += tempPosX;
        y += tempPosY;
        setX(x);
        setY(y);
    }
}
