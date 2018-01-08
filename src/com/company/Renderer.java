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

    boolean collided = false;
    boolean isAWall = false;

    char[][] map = new char[20][70];

    static Timer timer;
    static int interval = 0;
    Random rand = new Random();


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

        if(!collisionDetection() && inBounds(enemy.getX()+ newPosX, enemy.getY() + newPosY)){

            enemy.update(newPosX, newPosY);

            map[enemy.getY()][enemy.getX()] = 'E';
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


        if(!collisionDetection() && inBounds(player.getX()+ newPosX, player.getY() + newPosY)){

            player.update(newPosX, newPosY);
            map[player.getY()][player.getX()] = 'P';
        }

    }
    public void renderStuff(){
        terminal.moveCursor(player.getX(), player.getY());
        terminal.putCharacter('P');

        terminal.moveCursor(enemy.getX(), enemy.getY());
        terminal.putCharacter('E');

        renderScores();
    }

    public boolean inBounds(int x, int y){
        boolean inBound = false;
        if(x < 68 && y < 19 ){
            if(x > 0 && y > 2)
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
        return collided;
    }

    public void meleeAttack(){

    }
}

