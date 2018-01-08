package com.company;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

//import com.googlecode.lanterna.graphics.Styleset;
//import com.googlecode.lanterna.graphics.TextGraphics;
//import com.googlecode.lanterna.TextColor.ANSI;

import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Renderer {
    Terminal terminal = TerminalFacade.createTerminal(System.in,
            System.out, Charset.forName("UTF8"));
    List<String> lines = new ArrayList<String>();


    boolean isAWall = false;
    char[][] map = new char[20][70];

    static Timer timer;
    static int interval = 0;
    Random rand = new Random();


    Player player = new Player(20, 15);
    Enemy enemy = new Enemy(30, 15);

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

    public void updateEnemy() {
//        posX = rand.nextInt(40) + 1;
//        posY = rand.nextInt(20) + 1;
//        enemy.setX(posX);
//        enemy.setY(posY);

//        if (map[enemy.y][enemy.x] == '-') {
//            isAWall = true;
//        }
//        enemy.checkForWall();
//        if (map[enemy.tempPosY][enemy.tempPosX] == '-') {
//            isAWall = true;
//        }
//
//        if (!isAWall) {
//            isAWall = false;
//            enemy.update();
//            terminal.moveCursor(enemy.x, enemy.y);
//            terminal.putCharacter('E');
//        }

        if (map[enemy.tempPosY][enemy.tempPosX] != '-') {

            isAWall = true;
            enemy.checkForWall();
            enemy.update();
        }
        enemy.update();
        terminal.moveCursor(enemy.x, enemy.y);
        terminal.putCharacter('E');
//                if (!isAWall) {
//            isAWall = false;
//            enemy.update();
//            terminal.moveCursor(enemy.x, enemy.y);
//            terminal.putCharacter('E');
//        }

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
//        clear();
//        player.setX(player.getX() + newPosX);
//        player.setY(player.getY() + newPosY);


        player.update(newPosX, newPosY);
        terminal.moveCursor(player.getX(), player.getY());
        terminal.putCharacter('\u263A');
    }

    public void draw() {
        terminal.moveCursor(5, 5);
        terminal.putCharacter('P');
        terminal.moveCursor(10, 5);
        terminal.putCharacter('E');

//        terminal.flush();
    }

    public void clear() {
        terminal.clearScreen();
    }
}

