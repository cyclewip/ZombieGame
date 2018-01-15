package com.company;

import java.util.Scanner;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Menu {

    List<String> lines = new ArrayList<String>();
    char[][] menu = new char[24][70];
    GameLogic logic = new GameLogic();
    String menuType = "MENU";
    boolean enteredMenu = false;
    boolean okay = true;

    public void Run() throws InterruptedException {
        Scanner scan = new Scanner(System.in);

        logic.rend.terminal.enterPrivateMode();
        Key key;
        int input;
        drawEverything();
        while (true) {
            //Wait for a key to be pressed
            do {
                key = logic.rend.terminal.readInput();

            }
            while (key == null);

//            if(logic.rend.player.isLost()){
//                menuType = "GAMEOVER";
//                okay = false;
//                drawEverything();
//            }
//            if(logic.rend.player.isWon()){
//                menuType = "WINMENU";
//                okay = false;
//                drawEverything();
//            }


            switch (key.getKind()) {
                case F1:
                    if (okay) {
                        menuType = "GAME";
                        logic.gameLoop();
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
                    if(okay){
                        menuType = "GAMEOVER";
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
                lines = Files.readAllLines(Paths.get("src/MainMenu.txt"), StandardCharsets.UTF_8);
                enteredMenu = true;
            }
            if (menuType.contains("HIGHSCORE")) {
                lines = Files.readAllLines(Paths.get("src/HighScoreMenu.txt"), StandardCharsets.UTF_8);
            }
            if (menuType.contains("HELP")) {
                lines = Files.readAllLines(Paths.get("src/HelpMenu.txt"), StandardCharsets.UTF_8);
            }
            if (menuType.contains("GAME")) {
                lines = Files.readAllLines(Paths.get("src/MainMenu.txt"), StandardCharsets.UTF_8);
            }
            if (menuType.contains("GAMEOVER")) {
                lines = Files.readAllLines(Paths.get("src/GameOverMenu.txt"), StandardCharsets.UTF_8);
            }
            if (menuType.contains("WINMENU")) {
                lines = Files.readAllLines(Paths.get("src/WinMenu"), StandardCharsets.UTF_8);
            }

            for (int i = 0; i < menu.length; i++) {
                for (int y = 0; y < 70; y++) {
                    menu[i][y] = lines.get(i).charAt(y);
                }
            }
            String s = lines.get(0);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        for (int i = 0; i < menu.length; i++) {
            for (int y = 0; y < 70; y++) {
                c = menu[i][y];
                logic.rend.terminal.moveCursor(y, i);
                logic.rend.terminal.putCharacter(c);
            }
        }

    }

    public void startGame() {

    }

    public void drawHighScoreMenu() {

    }

    public void drawGameOverMenu() {

    }

    public void quitGame() {

    }

}

//     if (logic.rend.player.getHitPoints() <= 0) {
//             menuType = "GAMEOVER";
//
//             }
//             if (logic.rend.player.getHighScore() >= 5) {
//             menuType = "WINMENU";
//             drawEverything();
//             okay = true;
//
//             }
