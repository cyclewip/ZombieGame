package com.company;

import java.util.Random;

public class tempAI {
// YOLO
    Player player = new Player(20, 15);
    Enemy enemy = new Enemy(30, 15, "Dumb");
    Random random = new Random();

    // Enemy AI examples - By Jonas and Medusa //
    public void moveEnemy() {
    }


    /* *********** AI ***************
           STANDARD PATTERN AI
     ***************************** */
//    public void standardPatternAI() {
//        int randomNum = random.nextInt(2) + 1;
//        int x;
//        int y;
//
//        if (player.getX() < enemy.getX() && player.getY() < enemy.getY()) {
//            if (randomNum == 1) {
//                x = enemy.getX() - 1;
//                enemy.setX(x);
//            } else {
//                y = enemy.getY() - 1;
//                enemy.setY(y);
//            }
//        } else if (player.getX() > enemy.getX() && player.getY() > enemy.getY()) {
//            if (randomNum == 1) {
//                x = enemy.getX() + 1;
//                enemy.setX(x);
//            } else {
//                y = enemy.getY() + 1;
//                enemy.setY(y);
//            }
//        } else if (player.getX() < enemy.getX() && player.getY() > enemy.getY()) {
//            if (randomNum == 1) {
//                x = enemy.getX() - 1;
//                enemy.setX(x);
//            } else {
//                y = enemy.getY() + 1;
//                enemy.setY(y);
//            }
//        } else if (player.getX() > enemy.getX() && player.getY() < enemy.getY()) {
//            if (randomNum == 1) {
//                x = enemy.getX() + 1;
//                enemy.setX(x);
//            } else {
//                y = enemy.getY() - 1;
//                enemy.setY(y);
//            }
//        } else if (player.getX() < enemy.getX()) {
//            x = enemy.getX() - 1;
//            enemy.setX(x);
//        } else if (player.getX() > enemy.getX()) {
//            x = enemy.getX() + 1;
//            enemy.setX(x);
//        } else if (player.getY() < enemy.getY()) {
//            y = enemy.getY() - 1;
//            enemy.setY(y);
//        } else if (player.getY() > enemy.getY()) {
//            y = enemy.getY() + 1;
//            enemy.setY(y);
//        }
//    }

    /* *********** AI ***************
            RANDOM PATTERN AI
     ***************************** */
    public void randomPatternAI() {
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




