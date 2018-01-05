package com.company;

import java.util.Random;

public class Enemy {
    public int x = 0;
    public int y = 0;
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
        x = rand.nextInt(40) + 1;
        y = rand.nextInt(20) + 1;
        setX(x);
        setY(y);
    }
}