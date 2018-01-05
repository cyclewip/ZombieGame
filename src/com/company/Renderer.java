package com.company;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

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


char[][] map = new char[20][70];

    static Timer timer;
    static int interval = 0;

    Random rand = new Random();


    Player player = new Player(30, 15);
    Enemy enemy = new Enemy(20, 10);

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
//                if (map[i][y] == 'B') {
//                    terminal.moveCursor(y, i);
//                    terminal.putCharacter(c);
//                } else if (map[i][y] == 'E') {
//                    terminal.moveCursor(y, i);
//                    terminal.putCharacter('E');
//                } else if (map[i][y] == 'W') {
//                    terminal.moveCursor(y, i);
//                    terminal.putCharacter('W');
//                } else if (map[i][y] == '-') {
//                    terminal.moveCursor(y, i);
//                    terminal.putCharacter('-');
//                }
            }
        }
    }


    public void updateEnemy() {
//        posX = rand.nextInt(40) + 1;
//        posY = rand.nextInt(20) + 1;
//        enemy.setX(posX);
//        enemy.setY(posY);
        enemy.update();
        terminal.moveCursor(enemy.x, enemy.y);
        terminal.putCharacter('H');
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
    }

    public void clear() {
        terminal.clearScreen();
    }
}

