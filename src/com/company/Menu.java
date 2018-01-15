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
    String menuType = "";
    boolean enteredMenu = false;
    public void Run() throws InterruptedException {
        Scanner scan = new Scanner(System.in);


//        rend.terminal.enterPrivateMode();
        logic.rend.terminal.enterPrivateMode();
        Key key;
        int input;
        drawMainMenu();
        while (true) {
            //Wait for a key to be pressed

            do {
//                key = rend.terminal.readInput();
                key = logic.rend.terminal.readInput();
            }
            while (key == null);
            switch (key.getKind()) {
                case F1:
                    menuType = "GAME";
                    logic.gameLoop();
                    break;
                case F2:
                    menuType = "HELP";
                    break;
                case F3:
                    menuType = "HIGHSCORE";
                    break;
                case F4:
                    menuType = "MENU";
                    break;
                case Escape:
                    System.exit(0);
            }
            System.out.println(key.getCharacter() + " " + key.getKind());
        }
    }

//        while (true) {
//            do {
//                drawMainMenu();
//
//                key = rend.terminal.readInput();
//                otherKey = rend.terminal.readInput();
//
//
//                switch (otherKey.getKind()) {
//                    case ArrowDown:
//                        logic.gameLoop();
//                        break;
//                    case F2:
//                        // Look at high scores
//                        break;
//                    case F3:
//                        // Get help on how to move
//                        break;
//                    case Escape:
//                        System.exit(0);
//                        break;
//                }
//            } while (key == null);
//
//        }
//    }

    public void drawMainMenu() throws InterruptedException {
        int temp = 0;
        char c;
        try {

            if(!enteredMenu){
                lines = Files.readAllLines(Paths.get("src/MainMenu"), StandardCharsets.UTF_8);
                enteredMenu = true;
            }
            if(menuType == "GAME"){
//                lines = Files.readAllLines(Paths.get("src/MainMenu"), StandardCharsets.UTF_8);
//                enteredMenu = true;
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
