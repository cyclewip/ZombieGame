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
    ArrayList<Enemy> enemies = new ArrayList<Enemy>(5);
    Enemy[] enemyList = new Enemy[10];

    char[][] map = new char[20][70];

    Random rand = new Random();

    boolean collided = false;
    boolean isAWall = false;
    boolean attack = false;
    boolean deadEnemy = false;


    Player player = new Player(4, 3);
    Enemy enemy = new Enemy(10, 10);



    public Renderer() {

    }

    public void start() {
        terminal.enterPrivateMode();
        Player player = new Player(0, 0);

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

    public void updateEnemy(int newPosX, int newPosY) {
        if (inBounds(enemy.getX() + newPosX, enemy.getY() + newPosY)) {
            enemy.tempPosX = newPosX; // används i collisionDetection, för att se om newPos får användas
            enemy.tempPosY = newPosY;
            if (!collisionDetection()) {
                map[enemy.getX()][enemy.getY()] = ' '; ////// ÄNDRAR FÖREGÅENDE POSITION TILL INGET, SÅ ATT INTE ENEMY STANNAR KVAR
                enemy.update(newPosX, newPosY);
            }
        }


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
            player.tempPosX = newPosX; // används i collisionDetection, för att se om newPos får användas
            player.tempPosY = newPosY;
            if (!collisionDetection()) {
                player.update(newPosX, newPosY);
                map[player.getX()][player.getY()] = ' ';
            } else {
                int t = 5;
            }
        }
    }

    public void renderStuff() {

        terminal.moveCursor(player.getX(), player.getY());
        map[player.getY()][player.getX()] = 'P';
        terminal.putCharacter('P');


        if (meleeAttack()) {    //// AFTER MELEE ATTACK, ENEMY DIES
            enemy.isAlive = false;
        }
        //// IF ENEMY IS ALIVE, KEEP DRAWING
        if (enemy.isAlive) {
            enemy.setC('E');
            terminal.moveCursor(enemy.getX(), enemy.getY());
            map[enemy.getY()][enemy.getX()] = enemy.getC();
            terminal.putCharacter(enemy.getC());
        }
        else{   // WHEN DEAD, REMOVE CHARACTER E
            enemy.setC(' ');
            map[enemy.getY()][enemy.getX()] = enemy.getC();
        }
//        if(meleeAttack() && !deadEnemy){    //// IF ENEMY IS HIT ONCE, SET VALUE TO ' '
//            deadEnemy = true;
//            enemy.setC(' ');
//            terminal.moveCursor(enemy.getX(), enemy.getY());
//            map[enemy.getY()][enemy.getX()] = enemy.getC();
//            terminal.putCharacter(enemy.getC());
//        }


//        else{
//            terminal.moveCursor(enemy.getX(), enemy.getY());
//            map[enemy.getY()][enemy.getX()] = ' ';
//            terminal.putCharacter(' ');
//        }
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
        collided = false;
        if (map[player.getX() + player.tempPosX][player.getY() + player.tempPosY] == 'E') {
            collided = true;
        }
        if (map[enemy.getX() + enemy.tempPosX][enemy.getY() + enemy.tempPosY] == 'P') {
            collided = true;
        }
        return collided;
    }

    public boolean meleeAttack() {

        int pdistanceX = player.getX();
        int pdistanceY = player.getY();
        int edistanceX = enemy.getX();
        int edistanceY = enemy.getY();
        if (pdistanceX - edistanceX == 1) {
            attack = true;
        } else if (pdistanceY - edistanceY == 1) {
            attack = true;
        }
        return attack;
    }
}

