package com.company;

import com.googlecode.lanterna.input.Key;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameLogic {
    Renderer rend = new Renderer();
    boolean wonOrLost = false;
    boolean anotherGame = false;
    public void gameLoop() throws InterruptedException {
        Random rand = new Random();

        if(!anotherGame) {
            rend.scheduleTime();
        }
            rend.terminal.setCursorVisible(false);
            rend.start();


        while (true) {
            //Wait for a key to be pressed
            Key key;
            do {
                Thread.sleep(50);

                if (rend.player.isWon() || rend.player.isLost()) {
                    wonOrLost = true;
                }
                if (!wonOrLost) {
                    rend.readMap();
                    rend.createMap();
                    rend.renderScores();
                    rend.draw();
                    rend.renderStuff();
                }
                if (rend.player.isWon() && okay) {
                    menuType = "WINMENU";
                    anotherGame = true;
                    okay = false;
                    run();
                } else if (rend.player.isLost() && okay) {
                    menuType = "GAMEOVER";
                    anotherGame = true;
                    okay = false;
                    run();
                }
                key = rend.terminal.readInput();
            }
            while (key == null);

            switch (key.getKind()) {
                case ArrowDown:
                    rend.updatePlayer(0, 1);
                    rend.bulletDir = "BOT";
                    rend.bulletValues(0, 2, "BOT");
                    break;
                case ArrowUp:
                    rend.updatePlayer(0, -1);
                    rend.bulletDir = "TOP";
                    rend.bulletValues(0, -2, "TOP");
                    break;
                case ArrowLeft:
                    rend.updatePlayer(-1, 0);
                    rend.bulletDir = "LEFT";
                    rend.bulletValues(-2, 0, "LEFT");
                    break;
                case ArrowRight:
                    rend.updatePlayer(1, 0);
                    rend.bulletDir = "RIGHT";
                    rend.bulletValues(2, 0, "RIGHT");
                    break;
                case Enter:
                    rend.enterPressed = true;
                    break;
                case F1:
                    rend.enterF1 = true;
                    rend.bullet();
                    break;
                case Escape:
                    System.exit(0);

            }
            System.out.println(key.getCharacter() + " " + key.getKind());
        }
    }
    /////////// FROM MENU //////////////
    List<String> linesMenu = new ArrayList<String>();
    char[][] menu = new char[24][70];
    //    GameLogic logic = new GameLogic();
    static String menuType = "MENU";
    static boolean enteredMenu = false;
    boolean okay = true;
    /////////// FROM MENU //////////////

    ///////// FROM MENU ////////////////

    public void run() throws InterruptedException {
        Scanner scan = new Scanner(System.in);

        if(!anotherGame){
            rend.terminal.enterPrivateMode();
        }

        Key key;
        int input;
        drawEverything();

        while (true) {
            //Wait for a key to be pressed
            do {
                key = rend.terminal.readInput();
            }
            while (key == null);

            switch (key.getKind()) {
                case F1:
                    if (okay) {
                        menuType = "GAME";
                        rend.resetGame();
                        wonOrLost = false;
                        gameLoop();
                    } else {
                        menuType = "MENU";
                        enteredMenu = false;
                        okay = true;
                    }
                    drawEverything();
                    break;
                case F2:
                    if (okay) {
                        menuType = "HIGHSCORE";
                        okay = false;
                        drawEverything();
                    }
                    break;
                case F3:
                    if (okay) {
                        menuType = "HELP";
                        okay = false;
                        drawEverything();
                    }
                    break;
                case F4:
                    if (okay) {
                        menuType = "ENDCREDITS";
                        drawEverything();
                        Thread.sleep(5000);
                        System.exit(0);
                    }
                    break;
            }
            System.out.println(key.getCharacter() + " " + key.getKind());
        }
    }

    public void drawEverything() throws InterruptedException {
        int temp = 0;
        char c;
        try {
            if (!enteredMenu && menuType.contains("MENU")) {
                linesMenu = Files.readAllLines(Paths.get("src/MainMenu.txt"), StandardCharsets.UTF_8);
                enteredMenu = true;
            }
            if (menuType.contains("HIGHSCORE")) {
                linesMenu = Files.readAllLines(Paths.get("src/HighScoreMenu.txt"), StandardCharsets.UTF_8);
            }
            if (menuType.contains("HELP")) {
                linesMenu = Files.readAllLines(Paths.get("src/HelpMenu.txt"), StandardCharsets.UTF_8);
            }
            if (menuType.contains("GAME")) {
                linesMenu = Files.readAllLines(Paths.get("src/MainMenu.txt"), StandardCharsets.UTF_8);
            }
            if (menuType.contains("GAMEOVER")) {
                linesMenu = Files.readAllLines(Paths.get("src/GameOverMenu.txt"), StandardCharsets.UTF_8);
            }
            if (menuType.contains("WINMENU")) {
                linesMenu = Files.readAllLines(Paths.get("src/WinMenu.txt"), StandardCharsets.UTF_8);
            }
            if (menuType.contains("ENDCREDITS")) {
                linesMenu = Files.readAllLines(Paths.get("src/EndCredits.txt"), StandardCharsets.UTF_8);
            }
            for (int i = 0; i < menu.length; i++) {
                for (int y = 0; y < 70; y++) {
                    menu[i][y] = linesMenu.get(i).charAt(y);
                }
            }
            String s = linesMenu.get(0);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        for (int i = 0; i < menu.length; i++) {
            for (int y = 0; y < 70; y++) {
                int p = 0;
                c = menu[i][y];
                rend.terminal.moveCursor(y, i);
                rend.terminal.putCharacter(c);
            }
        }
    }
}
