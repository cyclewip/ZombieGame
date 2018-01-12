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
    List<Archetype> allEnemies = new ArrayList<Archetype>();
    List<Powerup> allPowerups = new ArrayList<Powerup>();
    boolean collided = false;
    boolean isAWall = false;
    boolean attack = false;
    public boolean enterPressed = false;
    boolean secondPassed = false;
    int meleeAttackCounter = 0;

    char[][] map = new char[24][70];
    char[][] mapMainMenu = new char[24][70];

    static int interval = 0;
    Random rand = new Random();


    Player player = new Player(11, 10);
    Archetype smartEnemy1 = new SmartEnemy(7, 7, "Smart");
    Archetype smartEnemy2 = new SmartEnemy(7, 9, "Smart");
    Archetype smartEnemy3 = new SmartEnemy(9, 7, "Smart");
    Archetype smartEnemy4 = new SmartEnemy(11, 11, "Smart");
    Archetype stupidEnemy1 = new StupidEnemy(13, 13, "Stupid");


    Powerup healthPowerUp = new HealthPowerUp(10, 10);
//    Powerup scorePowerUp = new ScorePowerUp(10, 17);
//    Powerup damagePowerUp = new DamagePowerUp(10, 19);

    public Renderer() {

    }

    int secondsPassed = 0;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            updateEnemy();
            secondsPassed++;
            System.out.println("Seconds passed" + secondsPassed);
        }
    };

    public void start() {
        terminal.enterPrivateMode();

//        allEnemies.add(smartEnemy1);
//        allEnemies.add(smartEnemy2);
//        allEnemies.add(smartEnemy3);
//        allEnemies.add(smartEnemy4);
        allEnemies.add(stupidEnemy1);


        allPowerups.add(healthPowerUp);
//        allPowerups.add(healthPowerUp);
//        allPowerups.add(scorePowerUp);


        timer.scheduleAtFixedRate(task, 500, 300);
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

    public void updateEnemy() {
        int newPosX = 0;
        int newPosY = 0;
        for (int i = 0; i < allEnemies.size(); i++) {
            if (inBounds(allEnemies.get(i).getX() + newPosX, allEnemies.get(i).getY() + newPosY)) { // KOLLA NÄSTA POSITION!!! HAR EJ GJORTS
                if (allEnemies.get(i).type.contains("Stupid")) {
                    allEnemies.get(i).pattern(player, allEnemies.get(i));
                    map[allEnemies.get(i).getY()][allEnemies.get(i).getX()] = 'E';
                } else if (allEnemies.get(i).type.equals("Smart")) {
                    allEnemies.get(i).pattern(player, allEnemies.get(i));
                }
            }
        }
    }

    public void renderScores() {
        if (player.highScore >= 100) {
            System.exit(0);
        }

        int hp = player.hitPoints;
        int score = player.highScore;
        String s = Integer.toString(hp);
        String s2 = Integer.toString(score);
        char c;

        //// CHECKING FOR HITPOINTS
        if (s.length() > 2) {
            terminal.moveCursor(21, 2);
            c = s.charAt(0);
            terminal.putCharacter(c);
            terminal.moveCursor(22, 2);
            c = s.charAt(1);
            terminal.putCharacter(c);
            terminal.moveCursor(23, 2);
            c = s.charAt(2);
            terminal.putCharacter(c);
        } else if (s.length() > 1) {
            terminal.moveCursor(21, 2);
            c = s.charAt(0);
            terminal.putCharacter(c);
            terminal.moveCursor(22, 1);
            c = s.charAt(1);
            terminal.putCharacter(c);
        } else if (s.length() > 0) {
            terminal.moveCursor(21, 2);
            c = s.charAt(0);
            terminal.putCharacter(c);
        }
        //// CHECKING FOR HIGHSCORE
        if (s2.length() > 2) {
            terminal.moveCursor(32, 2);
            c = s2.charAt(0);
            terminal.putCharacter(c);
            terminal.moveCursor(33, 2);
            c = s2.charAt(1);
            terminal.putCharacter(c);
            terminal.moveCursor(34, 2);
            c = s2.charAt(2);
            terminal.putCharacter(c);
        } else if (s2.length() > 1) {
            terminal.moveCursor(32, 2);
            c = s2.charAt(0);
            terminal.putCharacter(c);
            terminal.moveCursor(33, 2);
            c = s2.charAt(1);
            terminal.putCharacter(c);
        } else if (s2.length() > 0) {
            terminal.moveCursor(32, 2);
            c = s2.charAt(0);
            terminal.putCharacter(c);
        }
    }

    public void updatePlayer(int newPosX, int newPosY) {
        player.tempPosX = newPosX;
        player.tempPosY = newPosY;

        if (inBounds(player.getX() + newPosX, player.getY() + newPosY)) {
            map[player.getY()][player.getX()] = ' ';

            if (collisionDetection()) {
                collectPowerUp();
            }
            player.update(newPosX, newPosY);
        }
    }

    public void renderStuff() {

        terminal.moveCursor(player.getX(), player.getY());
        map[player.getY()][player.getX()] = 'P';
        terminal.putCharacter('P');

        for (int i = 0; i < allEnemies.size(); i++) {
            if (meleeAttack(i) && enterPressed && allEnemies.get(i).isAlive) {    //// AFTER MELEE ATTACK and ENTER IS PRESSED, ENEMY DIES
                player.setHighScore(player.getHighScore() + player.powerUpHighScore);
                renderScores();
                allEnemies.get(i).isAlive = false;
                enterPressed = false;
            }
            if (allEnemies.get(i).isAlive) {
                allEnemies.get(i).setC('E');
                terminal.moveCursor(allEnemies.get(i).getX(), allEnemies.get(i).getY());
                terminal.putCharacter('E');
                map[allEnemies.get(i).getY()][allEnemies.get(i).getX()] = 'E';
            } else {   // WHEN DEAD, REMOVE CHARACTER E
                allEnemies.get(i).setC(' ');
                map[allEnemies.get(i).getY()][allEnemies.get(i).getX()] = allEnemies.get(i).getC();
            }
        }
        for (int i = 0; i < allPowerups.size(); i++) {
            if (!allPowerups.get(i).isPickedUp()) {
                terminal.moveCursor(allPowerups.get(i).getX(), allPowerups.get(i).getY());
                terminal.putCharacter('H');
                map[allPowerups.get(i).getY()][allPowerups.get(i).getX()] = 'H';
            } else {

                terminal.moveCursor(allPowerups.get(i).getX(), allPowerups.get(i).getY());
                terminal.putCharacter(' ');
                map[allPowerups.get(i).getY()][allPowerups.get(i).getX()] = ' ';
            }
        }
    }

    public void collectPowerUp() { ////////////// ÄNDRAT 1/12
        for (int i = 0; i < allPowerups.size(); i++) {
            if (allPowerups.get(i).isPickedUp()) {
                allPowerups.get(i).setPickedUp(false);
                allPowerups.get(i).powerUp(player);
            }
        }
    }

    public boolean inBounds(int x, int y) {
        boolean inBound = false;
        if (x < 68 && y < 23) {
            if (x > 0 && y > 3)
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
//        if (map[player.getX() + player.tempPosX][player.getY() + player.tempPosY] == 'E') {
        collided = false;
//        }
        for (int i = 0; i < allPowerups.size(); i++) {      ////////////// ÄNDRAT 1/12
            if (map[player.getX() + player.getTempPosX()][player.getY() + player.getTempPosY()] == 'S') { // ADD player.tempPos
                allPowerups.get(i).setPickedUp(true);
                collided = true;
                break;
            } else if (map[player.getX() + player.getTempPosX()][player.getY() + player.getTempPosY()] == 'H') {
                allPowerups.get(i).setPickedUp(true);
                collided = true;
                break;
            } else if (map[player.getX() + player.getTempPosX()][player.getY() + player.getTempPosY()] == 'D') {
                allPowerups.get(i).setPickedUp(true);
                collided = true;
                break;
            }
        }
        return collided;
    }

    public boolean meleeAttack(int placeInList) {
        attack = false;
        int pdistanceX = player.getX();
        int pdistanceY = player.getY();
        int edistanceX;
        int edistanceY;
        for (int i = 0; i < allEnemies.size(); i++) {
            edistanceX = allEnemies.get(placeInList).getX();
            edistanceY = allEnemies.get(placeInList).getY();
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

