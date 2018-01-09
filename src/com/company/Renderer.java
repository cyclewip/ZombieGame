package com.company;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//import com.googlecode.lanterna.graphics.Styleset;
//import com.googlecode.lanterna.graphics.TextGraphics;
//import com.googlecode.lanterna.TextColor.ANSI;

public class Renderer {
    Terminal terminal = TerminalFacade.createTerminal(System.in,
            System.out, Charset.forName("UTF8"));
    List<String> lines = new ArrayList<String>();
    List<Enemy> enemies = new ArrayList<Enemy>();

    int secondsPassed = 0;

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if(secondsPassed == 1)
                secondsPassed = 0;
            secondsPassed++;
            System.out.println("Seconds passed" + secondsPassed);
            secondPassed = true;
//            System.out.println("Boolean is " + oneSecond);
//            oneSecond = true;
        }
    };

    boolean collided = false;
    boolean isAWall = false;
    boolean attack = false;
    public boolean enterPressed = false;
    boolean secondPassed = false;
    int meleeAttackCounter = 0;

    char[][] map = new char[20][70];


    static int interval = 0;
    Random rand = new Random();


    Player player = new Player(4, 3);
    Enemy enemy1 = new Enemy(10, 10, "Dumb");
    Enemy enemy2 = new Enemy(20, 12, "Follow");

    //    Enemy enemy3 = new Enemy(20, 14);
//    Enemy enemy4 = new Enemy(20, 16);
//    Enemy enemy5 = new Enemy(20, 18);
    public Renderer() {

    }

    public void start() {
        terminal.enterPrivateMode();
        Player player = new Player(0, 0);
        enemies.add(enemy1);
        enemies.add(enemy2);
//        enemies.add(enemy3);
//        enemies.add(enemy4);
//        enemies.add(enemy5);


    }

    public void readMap() {
        try {
            lines = Files.readAllLines(Paths.get("src/map.txt"), StandardCharsets.UTF_8);

            for (int i = 0; i < map.length; i++) {
                for (int y = 0; y < 70; y++) {
                    map[i][y] = lines.get(i).charAt(y);
                }
            }
            String s = lines.get(0);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    public void createMap() {
        int temp = 0;
        char c;
        for (int i = 0; i < map.length; i++) {
            for (int y = 0; y < 70; y++) {
                c = map[i][y];
                terminal.moveCursor(y, i);
                terminal.putCharacter(c);
            }
        }
    }

    public void updateRate() {
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void updateEnemy() {


            int newPosX = 0;
            int newPosY = 0;
            for (int i = 0; i < enemies.size(); i++) {
                newPosX = rand.nextInt(2 + 1) - 1;
                newPosY = rand.nextInt(2 + 1) - 1;
                if (enemies.get(i).type.equals("Dumb")) {
                    if (inBounds(enemies.get(i).getX() + newPosX, enemies.get(i).getY() + newPosY)) {
                        enemies.get(i).tempPosX = newPosX; // används i collisionDetection, för att se om newPos får användas
                        enemies.get(i).tempPosY = newPosY;
//                if (!collisionDetection()) {
                        enemies.get(i).update(newPosX, newPosY);
                        map[enemies.get(i).getY()][enemies.get(i).getX()] = 'E';
//                }
                    }
                }
                if (enemies.get(i).type.equals("Follow")) {
                    standardPatternAI(i);
                }
            }

    }

    public void standardPatternAI(int i) {
        int randomNum = rand.nextInt(2) + 1;
        int x;
        int y;

        if (player.getX() < enemies.get(i).getX() && player.getY() < enemies.get(i).getY()) {
            if (randomNum == 1) {
                x = enemies.get(i).getX() - 1;
                enemies.get(i).setX(x);
            } else {
                y = enemies.get(i).getY() - 1;
                enemies.get(i).setY(y);
            }
        } else if (player.getX() > enemies.get(i).getX() && player.getY() > enemies.get(i).getY()) {
            if (randomNum == 1) {
                x = enemies.get(i).getX() + 1;
                enemies.get(i).setX(x);
            } else {
                y = enemies.get(i).getY() + 1;
                enemies.get(i).setY(y);
            }
        } else if (player.getX() < enemies.get(i).getX() && player.getY() > enemies.get(i).getY()) {
            if (randomNum == 1) {
                x = enemies.get(i).getX() - 1;
                enemies.get(i).setX(x);
            } else {
                y = enemies.get(i).getY() + 1;
                enemies.get(i).setY(y);
            }
        } else if (player.getX() > enemies.get(i).getX() && player.getY() < enemies.get(i).getY()) {
            if (randomNum == 1) {
                x = enemies.get(i).getX() + 1;
                enemies.get(i).setX(x);
            } else {
                y = enemies.get(i).getY() - 1;
                enemies.get(i).setY(y);
            }
        } else if (player.getX() < enemies.get(i).getX()) {
            x = enemies.get(i).getX() - 1;
            enemies.get(i).setX(x);
        } else if (player.getX() > enemies.get(i).getX()) {
            x = enemies.get(i).getX() + 1;
            enemies.get(i).setX(x);
        } else if (player.getY() < enemies.get(i).getY()) {
            y = enemies.get(i).getY() - 1;
            enemies.get(i).setY(y);
        } else if (player.getY() > enemies.get(i).getY()) {
            y = enemies.get(i).getY() + 1;
            enemies.get(i).setY(y);
        }
    }

    public void renderScores() {

        int hp = player.hitPoints;
        int score = player.highScore;
        String s = Integer.toString(hp);
        String s2 = Integer.toString(score);
        char c;

        //// CHECKING FOR HITPOINTS
        if (s.length() > 2) {
            terminal.moveCursor(21, 1);
            c = s.charAt(0);
            terminal.putCharacter(c);
            terminal.moveCursor(22, 1);
            c = s.charAt(1);
            terminal.putCharacter(c);
            terminal.moveCursor(23, 1);
            c = s.charAt(2);
            terminal.putCharacter(c);
        } else if (s.length() > 1) {
            terminal.moveCursor(21, 1);
            c = s.charAt(0);
            terminal.putCharacter(c);
            terminal.moveCursor(22, 1);
            c = s.charAt(1);
            terminal.putCharacter(c);
        } else if (s.length() > 0) {
            terminal.moveCursor(21, 1);
            c = s.charAt(0);
            terminal.putCharacter(c);
        }
        //// CHECKING FOR HIGHSCORE
        if (s2.length() > 2) {
            terminal.moveCursor(32, 1);
            c = s2.charAt(0);
            terminal.putCharacter(c);
            terminal.moveCursor(33, 1);
            c = s2.charAt(1);
            terminal.putCharacter(c);
            terminal.moveCursor(34, 1);
            c = s2.charAt(2);
            terminal.putCharacter(c);
        } else if (s2.length() > 1) {
            terminal.moveCursor(32, 1);
            c = s2.charAt(0);
            terminal.putCharacter(c);
            terminal.moveCursor(33, 1);
            c = s2.charAt(1);
            terminal.putCharacter(c);
        } else if (s2.length() > 0) {
            terminal.moveCursor(32, 1);
            c = s2.charAt(0);
            terminal.putCharacter(c);
        }

    }

    public void updatePlayer(int newPosX, int newPosY) {

        if (inBounds(player.getX() + newPosX, player.getY() + newPosY)) {
//            if(!collisionDetection()){
            player.update(newPosX, newPosY);
            map[player.getY()][player.getX()] = ' ';
//            }
        }
    }

    public void renderStuff() {

        terminal.moveCursor(player.getX(), player.getY());
        map[player.getY()][player.getX()] = 'P';
        terminal.putCharacter('P');

        for (int i = 0; i < enemies.size(); i++) {
            if (meleeAttack(i) && enterPressed && enemies.get(i).isAlive) {    //// AFTER MELEE ATTACK and ENTER IS PRESSED, ENEMY DIES
                player.setHighScore(player.getHighScore() + 5);
                renderScores();
                enemies.get(i).isAlive = false;
                enterPressed = false;
            }
            if (enemies.get(i).isAlive) {
                enemies.get(i).setC('E');
                terminal.moveCursor(enemies.get(i).getX(), enemies.get(i).getY());
                terminal.putCharacter('E');
                map[enemies.get(i).getY()][enemies.get(i).getX()] = 'E';
            } else {   // WHEN DEAD, REMOVE CHARACTER E
                enemies.get(i).setC(' ');
                map[enemies.get(i).getY()][enemies.get(i).getX()] = enemies.get(i).getC();
            }
        }

    }

    public boolean inBounds(int x, int y) {
        boolean inBound = false;
        if (x < 68 && y < 19) {
            if (x > 0 && y > 2)
                inBound = true;
        }
        return inBound;
    }

    public void draw() {

    }

    public void clear() {
        terminal.clearScreen();
    }

    public boolean collisionDetection() {
        if (map[player.tempPosX][player.tempPosY] == 'E') {
            collided = true;
        }
//        for (int i = 0; i < enemies.size(); i++) { FUNKAR EJ
//            if (map[enemies.get(i).getX() + enemies.get(i).tempPosX][enemies.get(i).getY() + enemies.get(i).tempPosY] == 'P') {
//                collided = true;
//                enemies.get(i).hasCollided = true;
//                break;
//            }
//        }
        return collided;

    }

    public boolean meleeAttack(int placeInList) {
        attack = false;
        int pdistanceX = player.getX();
        int pdistanceY = player.getY();
        int edistanceX;
        int edistanceY;
        for (int i = 0; i < enemies.size(); i++) {
            edistanceX = enemies.get(placeInList).getX();
            edistanceY = enemies.get(placeInList).getY();
            if (pdistanceX - edistanceX == 1 || edistanceX - pdistanceX == 1 || pdistanceX - edistanceX == 0 || edistanceX - pdistanceX == 0) {
                if (pdistanceY - edistanceY == 1 || edistanceY - pdistanceY == 1 || pdistanceY - edistanceY == 0 || edistanceY - pdistanceY == 0) {
                    attack = true;

                    break;
                }
            }
        }
        return attack;
    }
}

