package com.company;

import java.util.List;
import java.util.Random;

public class StupidEnemy extends Archetype {
    Random random = new Random();
    //    int x = 0;
//    int y = 0;
    String type = "Stupid";
    boolean isAlive = true;

    //    public int tempPosX = 0;
//    public int tempPosY = 0;
    public StupidEnemy(int x, int y, String type) {
        super(x, y, type);
        this.type = type;
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

    public void isPatternOK(Player player, Archetype e, int randNumb) {
        // NEVER USED HERE
    }

    public void pattern2(Archetype e, int newPosX, int newPosY) {
// NEVER USED HERE
    }

    public void pattern(Player player, Archetype e, int randNumb) {

//        int randomNum = random.nextInt(4) + 1;
        int x;
        int y;
        if (randNumb == 1) {
            x = e.getX() - 1;
            e.setX(x);
        } else if (randNumb == 2) {
            x = e.getX() + 1;
            e.setX(x);
        } else if (randNumb == 3) {
            y = e.getY() + 1;
            e.setY(y);
        } else {
            y = e.getY() - 1;
            e.setY(y);
        }

    }
}
