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

    boolean collided = false;
    boolean isAWall = false;
    boolean attack = false;

    char[][] map = new char[20][70];

    static Timer timer;
    static int interval = 0;
    Random rand = new Random();


    Player player = new Player(4, 3);
    Enemy enemy = new Enemy(10, 10);
    Enemy enemy1 = new Enemy(10, 10);
    Enemy enemy2 = new Enemy(20, 10);

    public Renderer() {

    }

    public void start() {
        terminal.enterPrivateMode();
        Player player = new Player(0, 0);
        enemies.add(enemy1);
        enemies.add(enemy2);
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
        for (int i = 0; i < enemies.size(); i++) {
            newPosX = rand.nextInt(2 + 1) - 1;
            newPosY = rand.nextInt(2 + 1) - 1;
            if (inBounds(enemies.get(i).getX() + newPosX, enemies.get(i).getY() + newPosY)) {
                enemies.get(i).tempPosX = newPosX; // används i collisionDetection, för att se om newPos får användas
                enemies.get(i).tempPosY = newPosY;
                if (!collisionDetection()) {
                    enemies.get(i).update(newPosX, newPosY);
                    map[enemies.get(i).getY()][enemies.get(i).getX()] = 'E';
                }

            }
        }
        //if (map[enemy.tempPosY][enemy.tempPosX] != '-') {


        //}

    }

    public void renderScores() {

        int score = player.hitPoints;
        String s = Integer.toString(score);
        char c;

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
    }

    public void updatePlayer(int newPosX, int newPosY) {


        if (inBounds(player.getX() + newPosX, player.getY() + newPosY)) {
//            player.tempPosX = newPosX; // används i collisionDetection, för att se om newPos får användas
//            player.tempPosY = newPosY; FÅR EJ ANVÄNDAS VET EJ VRF

            if(!collisionDetection()){
                player.update(newPosX, newPosY);
                map[player.getY()][player.getX()] = ' ';
            }

        }

    }

    public void renderStuff() {

        terminal.moveCursor(player.getX(), player.getY());
        map[player.getY()][player.getX()] = 'P';
        terminal.putCharacter('P');

        for (int i = 0; i < enemies.size(); i++) {
            if (meleeAttack()) {    //// AFTER MELEE ATTACK, ENEMY DIES
                enemies.get(i).isAlive = false;
            }
            if (enemies.get(i).isAlive) {
                enemies.get(i).setC('E');
                terminal.moveCursor(enemies.get(i).getX(), enemies.get(i).getY());
                terminal.putCharacter('E');
                map[enemies.get(i).getY()][enemies.get(i).getX()] = 'E';
            }
            else {   // WHEN DEAD, REMOVE CHARACTER E
                enemies.get(i).setC(' ');
                map[enemies.get(i).getY()][enemies.get(i).getX()] = enemies.get(i).getC();
            }
        }
        renderScores();
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


    public boolean meleeAttack() {
        int pdistanceX = player.getX();
        int pdistanceY = player.getY();
        int edistanceX;
        int edistanceY;
        for (int i = 0; i < enemies.size(); i++) {
            edistanceX = enemies.get(i).getX();
            edistanceY = enemies.get(i).getY();
            if (pdistanceX - edistanceX == 1) {
                if (pdistanceY - edistanceY == 1) {
                    attack = true;
                    break;
                }
            }
        }
        return attack;
    }
}

