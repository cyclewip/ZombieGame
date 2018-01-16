package com.company;

public class Bullet implements  Weapon{
    public int x = 0;
    public int y = 0;
    int tempPosX = 0;
    int tempPosY = 0;
    String direct = "";
    public char c = '+';
    boolean isalive = true;

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

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public boolean isIsalive() {
        return isalive;
    }

    public void setIsalive(boolean isalive) {
        this.isalive = isalive;
    }

    public Bullet(int x, int y, String direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;

    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
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
}
