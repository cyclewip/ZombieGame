package com.company;

import java.util.Random;

public class StupidEnemy extends Archetype {

    public StupidEnemy(int x, int y) {
        super(x, y);
    }

    public void randomPatternAI(Archetype enemy) {
        Random random = new Random();
        int randomNum = random.nextInt(4) + 1;
        int x;
        int y;

        if (randomNum == 1) {
            x = enemy.getX() - 1;
            enemy.setX(x);
        } else if (randomNum == 2) {
            x = enemy.getX() + 1;
            enemy.setX(x);
        } else if (randomNum == 3) {
            y = enemy.getY() - 1;
            enemy.setY(y);
        } else {
            y = enemy.getY() + 1;
            enemy.setY(y);
        }
    }
}
