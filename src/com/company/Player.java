package com.company;

import java.security.Key;

public class Player {
    public int x = 0;
    public int y = 0;

    public int getPowerUpHighScore() {
        return powerUpHighScore;
    }

    public void setPowerUpHighScore(int powerUpHighScore) {
        this.powerUpHighScore = powerUpHighScore;
    }

    public int getPowerUpDamage() {
        return powerUpDamage;
    }

    public void setPowerUpDamage(int powerUpDamage) {
        this.powerUpDamage = powerUpDamage;
    }

    public int tempPosX, tempPosY;
    int hitPoints = 100;
    int highScore = 0;
    int powerUpHighScore = 5;
    int powerUpDamage = 10;
    Key key;

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getHighScore() {

        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

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
        this.tempPosX = tempPosX;
        this.tempPosY = tempPosY;
        x += tempPosX;
        y += tempPosY;
        setX(x);
        setY(y);
    }
}
