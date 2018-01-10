package com.company;

import java.util.Random;

public class SmartEnemy extends Archetype {

    public SmartEnemy(int x, int y) {
        super(x, y);
    }

    public void smartPatternAI(Archetype enemy, Player player) {
        Random random = new Random();
        int randomNum = random.nextInt(2) + 1;
        int x;
        int y;

        if (player.getX() < enemy.getX() && player.getY() < enemy.getY()) {
            if (randomNum == 1) {
                x = enemy.getX() - 1;
                enemy.setX(x);
            } else {
                y = enemy.getY() - 1;
                enemy.setY(y);
            }
        } else if (player.getX() > enemy.getX() && player.getY() > enemy.getY()) {
            if (randomNum == 1) {
                x = enemy.getX() + 1;
                enemy.setX(x);
            } else {
                y = enemy.getY() + 1;
                enemy.setY(y);
            }
        } else if (player.getX() < enemy.getX() && player.getY() > enemy.getY()) {
            if (randomNum == 1) {
                x = enemy.getX() - 1;
                enemy.setX(x);
            } else {
                y = enemy.getY() + 1;
                enemy.setY(y);
            }
        } else if (player.getX() > enemy.getX() && player.getY() < enemy.getY()) {
            if (randomNum == 1) {
                x = enemy.getX() + 1;
                enemy.setX(x);
            } else {
                y = enemy.getY() - 1;
                enemy.setY(y);
            }
        } else if (player.getX() < enemy.getX()) {
            x = enemy.getX() - 1;
            enemy.setX(x);
        } else if (player.getX() > enemy.getX()) {
            x = enemy.getX() + 1;
            enemy.setX(x);
        } else if (player.getY() < enemy.getY()) {
            y = enemy.getY() - 1;
            enemy.setY(y);
        } else if (player.getY() > enemy.getY()) {
            y = enemy.getY() + 1;
            enemy.setY(y);
        }
    }
}
